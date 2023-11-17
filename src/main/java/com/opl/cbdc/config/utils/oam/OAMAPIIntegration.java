package com.opl.cbdc.config.utils.oam;

import com.opl.cbdc.utils.common.*;
import com.opl.cbdc.utils.model.*;
import com.opl.cbdc.config.domain.*;
import com.opl.cbdc.config.repository.*;
import com.opl.cbdc.config.utils.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@Slf4j
public class OAMAPIIntegration {

	private static final String V1_0 = "V1.0";
	private static final String CLIENT_ID = "clientId";
	private static final String SECRET_ID = "secretId";

	@Autowired
	private OAMAPIMasterRepo oamapiMasterRepo;

	@Autowired
	private RestTemplateUtils restTemplateUtils;

	@Autowired
	private OAMAPIAuditRepo oamapiAuditRepo;

	public <T extends OAMAPIResponse> T post(Object request, Class<T> response, OAMAPICodeMaster codeMaster) throws Exception {

		OAMAPIAudit oamapiAudit = new OAMAPIAudit(codeMaster.getValue(), generateUUID(), new Date());
		try {

			// FETCH OAM API DETAILS USING API CODE FROM DATABASE (ans-config) 
			OAMAPIMaster oamAPIMaster = oamapiMasterRepo.findByApiCodeAndIsActive(codeMaster.getValue(), true);
			if (oamAPIMaster == null) {
				return response.getDeclaredConstructor(String.class, Integer.class, String.class).newInstance(oamapiAudit.getReferenceId(), HttpStatus.BAD_REQUEST.value(), "Its seems we have not found API details by API code.");
			}
			oamapiAudit.setOamapiMaster(oamAPIMaster);
			String url = oamAPIMaster.getDomainUrl().concat(oamAPIMaster.getApiUrl());
			log.info(codeMaster.getValue() + " --  URL ::-" + url);

			// REQUEST HEADER
			Map<String, String> headers = new HashMap<String, String>();
			headers.put(CLIENT_ID, oamAPIMaster.getClientId());
			headers.put(SECRET_ID, oamAPIMaster.getSecretId());

			// ------------ REQUEST ENCRYPTION ------------------------------
			String encryptedString = encrypt(request, oamAPIMaster.getPrivateKey(), oamapiAudit);
			if (OPLUtils.isObjectNullOrEmpty(encryptedString)) {
				return response.getDeclaredConstructor(String.class, Integer.class, String.class).newInstance(oamapiAudit.getReferenceId(), HttpStatus.BAD_REQUEST.value(), "Encountered some error while Encrypt payload");
			}

			// CALLING OAM API
			String body = "";
			try {
				body = restTemplateUtils.post(url, encryptedString, String.class, headers);
			} catch (Exception e) {
				oamapiAudit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				oamapiAudit.setMessage(e.getMessage());
				return response.getDeclaredConstructor(String.class, Integer.class, String.class).newInstance(oamapiAudit.getReferenceId(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Encountered some error while calling OAM API");
			}

			if (OPLUtils.isObjectNullOrEmpty(body)) {
				oamapiAudit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				oamapiAudit.setMessage("Response body is null or empty !!");
//				return response.cast(new OAMAPIResponse(oamapiAudit.getReferenceId(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Encountered some error while calling OAM API"));
				return response.getDeclaredConstructor(String.class, Integer.class, String.class).newInstance(oamapiAudit.getReferenceId(), HttpStatus.BAD_REQUEST.value(), "Encountered some error while calling OAM API");
			}

			// ------------ START REQUEST DECRYPTION ------------------------------
			T decrypt = decrypt(body, oamAPIMaster.getPublicKey(), response, oamapiAudit);
			if (OPLUtils.isObjectNullOrEmpty(decrypt)) {
//				return response.cast(new OAMAPIResponse(oamapiAudit.getReferenceId(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Encountered some error while Decrypt payload"));
				return response.getDeclaredConstructor(String.class, Integer.class, String.class).newInstance(oamapiAudit.getReferenceId(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Encountered some error while Decrypt payload");
			}
			decrypt.setReferenceId(oamapiAudit.getReferenceId());
			decrypt.setStatus(HttpStatus.OK.value());
			oamapiAudit.setStatus(HttpStatus.OK.value());
			return decrypt;
		} catch (Exception e2) {
			log.error("Error In API - ", e2);
			oamapiAudit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			oamapiAudit.setMessage(e2.getMessage());

			return response.getDeclaredConstructor(String.class, Integer.class, String.class).newInstance(oamapiAudit.getReferenceId(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Encountered some error while calling OAM API");
		} finally {
			oamapiAuditRepo.save(oamapiAudit);
		}
	}

	private String encrypt(Object request, String privateKey, OAMAPIAudit oamapiAudit) {
		try {
			// CONVERT REQEST OBJECT TO JSON STRING
			String data = MultipleJSONObjectHelper.getStringfromObject(request);
			if (OPLUtils.isObjectNullOrEmpty(data)) {
				oamapiAudit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				oamapiAudit.setMessage("Error while converting request object to string");
				return null;
			}

			// GENERATE SECRET KEY
			String secretKey = OAMAESGCMEncryptionUtils.geenerateSecretKey();

			// CONVERT SECRET KEY INTO BASE 64
			String base64StringFromSecretKey = OAMAESGCMEncryptionUtils.getBase64StringFromSecretKey(secretKey.getBytes());
			if (OPLUtils.isObjectNullOrEmpty(base64StringFromSecretKey)) {
				oamapiAudit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				oamapiAudit.setMessage("Error while converting request secret key into base64");
				return null;
			}

			// TIMESTAMP
			String timestamp = OAMAESGCMEncryptionUtils.getPayloadTimestampFormat().format(new java.util.Date());
			// IV LOGIC
			String iv = timestamp.substring(0, 16);
			String encryptPrivateKey = OAMAESGCMEncryptionUtils.encryptSecretKeyByPrivateKey(base64StringFromSecretKey, privateKey);
			String encryptData = OAMAESGCMEncryptionUtils.encrypt(data, secretKey, iv);
			if (OPLUtils.isObjectNullOrEmpty(encryptData)) {
				oamapiAudit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				oamapiAudit.setMessage("Error while encrypt request payload");
				return null;
			}
			return OAMAESGCMEncryptionUtils.getStringfromObject(new GatewayRequest(new GatewayMetaData(encryptPrivateKey, V1_0, timestamp, UUID.randomUUID().toString()), encryptData));
		} catch (Exception e) {
			log.error("Exception while Encrypt request --->", e);
			oamapiAudit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			oamapiAudit.setMessage(e.getMessage());
			return null;
		}
	}

	private <T extends OAMAPIResponse> T decrypt(String res, String publicKey, Class<T> response, OAMAPIAudit oamapiAudit) {
		try {
			GatewayRequest objectFromString = OAMAESGCMEncryptionUtils.getObjectFromString(res, GatewayRequest.class);
			if (OPLUtils.isObjectNullOrEmpty(objectFromString)) {
				oamapiAudit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				oamapiAudit.setMessage("Error while converting response string to object");
				return null;
			}

			String decSecretKey = OAMAESGCMEncryptionUtils.decryptSecretKeyByPublicKey(objectFromString.getMetadata().getSecretId(), publicKey);
			String decrypt = OAMAESGCMEncryptionUtils.decrypt(objectFromString.getPayload().toString(), decSecretKey, objectFromString.getMetadata().getTimestamp().toString().substring(0, 16));
			if (OPLUtils.isObjectNullOrEmpty(decrypt)) {
				oamapiAudit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				oamapiAudit.setMessage("Error while decrypt payload");
				return null;
			}
			GatewayResponse gatewayResponse = MultipleJSONObjectHelper.getObjectFromString(decrypt, GatewayResponse.class);
			if (OPLUtils.isObjectNullOrEmpty(gatewayResponse)) {
				oamapiAudit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				oamapiAudit.setMessage("Error while convert descryt payload string to object");
				return null;
			}
			T responseOAM = MultipleJSONObjectHelper.getObjectFromObject(gatewayResponse.getData(), response);
			oamapiAudit.setMessage(gatewayResponse.getMessage());
			oamapiAudit.setOamStatus(gatewayResponse.getStatus());
			oamapiAudit.setOamReferenceId(gatewayResponse.getServerRequestId());
			return responseOAM;
		} catch (Exception e) {
			log.error("Exception while Decrypt request --->", e);
			oamapiAudit.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			oamapiAudit.setMessage(e.getMessage());
			return null;
		}
	}

	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
