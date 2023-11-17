package com.opl.cbdc.config.utils;

import com.opl.cbdc.utils.common.*;
import com.opl.cbdc.config.thirdparty.authbridge.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.util.*;

@Component
public class AuthBridgeUtils {

	@Autowired
	private AnsProperties ansProperties;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthBridgeUtils.class);

	public static final String AUTHBRIDGE_HEADER_USERNAME = "username";
	public static final String AUTHBRIDGE_HEADER_CONTENT_TYPE = "Content-Type";
	public static final String AUTHBRIDGE_HEADER_CONTENT_TYPE_JSON = "application/json";

	public static final String TRANS_ID = "transID";
	public static final String DOC_TYPE = "docType";
	public static final String FRONT_IMAGE = "front_image";
	public static final String BACK_IMAGE = "back_image";
	public static final String ACC_NO = "beneAccNo";
	public static final String IFSC = "ifsc";
	public static final String DOC_NUMBER = "docNumber";
	public static final String MOBILE_NO = "mobileno";
	public static final String UDYAM_NUMBER = "udyamNumber";
	public static final Integer OCR_DOC_TYPE_AADHAAR = 1;
	public static final Integer OCR_DOC_TYPE_VOTER = 6;
	public static final Integer DOC_TYPE_UDHYAM = 435;
	public static final String DOC_TYPE_BANK_VERIFY = "92";

	private static String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
	private static int CIPHER_KEY_LEN = 16; // 128 bits
//	 static Random random = new Random();
	static SecureRandom random =new  SecureRandom();

	public AuthBridgeCredentialProxy getAuthBridgeCredential() {
		AuthBridgeCredentialProxy proxy = null;
		String value = ansProperties.getValue("AUTHBRIDGE_CREDENTIAL");
		try {
			proxy = MultipleJSONObjectHelper.getObject(value, AuthBridgeCredentialProxy.class);
		} catch (Exception e) {
			LOGGER.error("Exception in getAuthBridgeCredentials() : ", e);
		}
		return proxy;
	}

	public static String createRequest(String text, String password) {
		LOGGER.info("INSIDE createRequest()======>");
		String request = null;
		try {
			String iv = getIV();
			String encrypt = encrypt(password, iv, text);
			Map<String, Object> map = new HashMap<>();
			map.put("requestData", encrypt);
			request = MultipleJSONObjectHelper.getStringfromObject(map);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return request;
	}

	public static String decryptResponse(String text, String password) {
		LOGGER.info("INSIDE decryptResponse()======>");
		String request = null;
		try {
			return decrypt(password, text);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return request;
	}

	/**
	 * Encrypt data using AES Cipher (CBC) with 128 bit key
	 *
	 * @param key  - key to use should be 16 bytes long (128 bits)
	 * @param iv   - initialization vector
	 * @param data - data to encrypt
	 * @return encryptedData data in base64 encoding with iv attached at end after a
	 *         :
	 */
	public static String encrypt(String key, String iv, String data) {
		try {
			byte[] sha = generateSha512Hash(key.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
// converting byte[] type SHA into Hexadecimal representation string
			if (sha != null) {
				for (int i = 0; i < sha.length; i++) {
					sb.append(Integer.toString((sha[i] & 0xff) + 0x100, 16).substring(1));
				}
			}

			String shaKey = sb.substring(0, 16);
			IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
			SecretKeySpec secretKey = new SecretKeySpec(fixKey(shaKey).getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance(AuthBridgeUtils.CIPHER_NAME);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
			byte[] encryptedData = cipher.doFinal((data.getBytes()));
			String encryptedDataInBase64 = Base64.getEncoder().encodeToString(encryptedData);
			String ivInBase64 = Base64.getEncoder().encodeToString(iv.getBytes("UTF-8"));
			return encryptedDataInBase64 + ":" + ivInBase64;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static String fixKey(String key) {
		if (key.length() < AuthBridgeUtils.CIPHER_KEY_LEN) {
			int numPad = AuthBridgeUtils.CIPHER_KEY_LEN - key.length();
			for (int i = 0; i < numPad; i++) {
				key += "0"; // 0 pad to len 16 bytes
			}
			return key;
		}
		if (key.length() > AuthBridgeUtils.CIPHER_KEY_LEN) {
			return key.substring(0, CIPHER_KEY_LEN); // truncate to 16 bytes
		}
		return key;
	}

	/**
	 * Decrypt data using AES Cipher (CBC) with 128 bit key
	 *
	 * @param key  - key to use should be 16 bytes long (128 bits)
	 * @param data - encrypted data with iv at the end separate by :
	 * @return decrypted data string
	 */
	public static String decrypt(String key, String data) {
		try {
			byte sha[] = generateSha512Hash(key.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			if (sha != null) {
				for (int i = 0; i < sha.length; i++) {
					sb.append(Integer.toString((sha[i] & 0xff) + 0x100, 16).substring(1));
				}
			}

			String shaKey = sb.substring(0, 16);
			String[] parts = data.split(":");
			IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[1]));
			SecretKeySpec secretKey = new SecretKeySpec(shaKey.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance(AuthBridgeUtils.CIPHER_NAME);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			byte[] decodedEncryptedData = Base64.getDecoder().decode(parts[0]);
			byte[] original = cipher.doFinal(decodedEncryptedData);
			return new String(original);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/*
	 * This method return IV random values of 16 bit (Init Vector values) which must
	 * be 16 bit long and random each time encrypting the data.
	 */
	protected static String getIV() {
		String ivCHARS = "1234567890";	
		StringBuilder iv = new StringBuilder();
//		Random rnd = new Random();
		while (iv.length() < 16) { // length of the random string.			
//			float val=  new Random().nextFloat();			
			float val=  random.nextFloat();
			int index = (int) (val * ivCHARS.length());
			iv.append(ivCHARS.charAt(index));
		}
		return iv.toString();
	}

	/**
	 * Generate SHA512 Hashes of given data
	 *
	 * @param data - data to use should be in bytes type array form
	 * @return decrypted SHA512 hashes in form of byte type array
	 */
	public static byte[] generateSha512Hash(byte[] data) {
		String algorithm = "SHA-512";
		byte[] hash = null;
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(algorithm);
			digest.reset();
			hash = digest.digest(data);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return hash;
	}

	public static String encodeFileToBase64Binary(MultipartFile multipartFile) throws Exception {
		String encodedfile = null;
		try {
			encodedfile = Base64.getEncoder().encodeToString(multipartFile.getBytes());
		} catch (Exception e) {
			LOGGER.error("Exception in encodeFileToBase64Binary() : ", e);
		}

		return encodedfile;
	}

	public static String getUserErrorMsg(String authBridgeExceptionMsg) {
		AuthBridgeExceptionProxy exceptionProxy = null;
		String msg = null;
		try {
			exceptionProxy = MultipleJSONObjectHelper.getObject(authBridgeExceptionMsg, AuthBridgeExceptionProxy.class);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		if (!OPLUtils.isObjectNullOrEmpty(exceptionProxy)
				&& !OPLUtils.isObjectNullOrEmpty(exceptionProxy.getStatus())) {
			msg = getUserErrorMsgByStatusCode(exceptionProxy.getStatus());
		}
		return msg;
	}

	public static String getUserErrorMsgByStatusCode(Integer code) {
		String msg = null;
		switch (code) {
		case 1012:
			msg = "The provided Voter ID is invalid, please provide valid Voter ID.";
			break;
		case 1172:
			msg = "The provided PAN is not correct, please provide correct PAN.";
			break;
		case 1069:
			msg = "The provided GSTIN is not correct, please provide correct GSTIN.";
			break;
		case 1082:
			msg = "The provided Aadhaar is invalid, please provide valid Aadhaar.";
			break;
		case 1171:
			msg = "The Enter PAN is not correct, please provide correct PAN.";
			break;
		case 1047:
			msg = "There seems to be technical error. Please try again in sometime.";
			break;
		case 1021:
			msg = "Please provide valid 15 digits Service Tax Number.";
			break;
		case 1138:
			msg = "PAN Number should not be blank.";
			break;
		case 1014:
			msg = "Document number is required. Please fill the same.";
			break;

		default:
			msg = "There seems to be technical error. Please try again in sometime.";
			break;
		}

		return msg;
	}

}
