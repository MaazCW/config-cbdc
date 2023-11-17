package com.opl.cbdc.config.thirdparty.udhyamaadhar;

import com.opl.cbdc.utils.common.*;
import com.opl.cbdc.utils.config.*;
import com.opl.cbdc.utils.config.UrlsAns.*;
import com.opl.cbdc.config.domain.*;
import com.opl.cbdc.config.repository.*;
import com.opl.cbdc.config.utils.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;

@Component
@Slf4j
public class UdhyamAadharAPI {

	@Autowired
	private AnsProperties ansProperties;

	@Autowired
	private UdhyamAPIAuditRepo udhyamAPIAuditRepo;

	public UdhyamAadharResponse getData(UdhyamAadharRequest udhyamAadharRequest) {
		try {
			if(OPLUtils.isObjectNullOrEmpty(udhyamAadharRequest.getUdhyamNo()) || OPLUtils.isObjectNullOrEmpty(udhyamAadharRequest.getMobileNo())) {
				log.warn("Udhyam No -> " + udhyamAadharRequest.getUdhyamNo()  + " or Mobile No :- " + udhyamAadharRequest.getMobileNo() + " Is Null Or Empty !!!!!!!!!!!!");
				return null;
			}
			UdhyamAPIAudit udhyamAPIAudit = udhyamAPIAuditRepo.getByUdhyamNoAndMobileNo(udhyamAadharRequest.getUdhyamNo(), udhyamAadharRequest.getMobileNo());
			if(udhyamAPIAudit != null) {
				return getExistingData(udhyamAPIAudit.getReferenceId());
			}
			return getFreshData(udhyamAadharRequest);
		} catch (Exception e) {
//			log.error("Exception while Get UdhyamAadhr API For " + udhyamAadharRequest.toString(), e);
			log.error("Exception while Get UdhyamAadhr API ", e);
		}
		return null;
	}

	private UdhyamAadharResponse getFreshData(UdhyamAadharRequest udhyamAadharRequest) {
//		log.info("Into getFreshData with udhyamAadharRequest==>{}",udhyamAadharRequest);
		log.info("Into getFreshData with udhyamAadharRequest==> " + udhyamAadharRequest.getUdhyamNo());
		UdhyamAPIAudit udhyamAPIAudit = new UdhyamAPIAudit();
		udhyamAPIAudit.setUdhyamNo(udhyamAadharRequest.getUdhyamNo());
		udhyamAPIAudit.setMobileNo(udhyamAadharRequest.getMobileNo());
		udhyamAPIAudit.setCreatedDate(new Date());
		udhyamAPIAudit.setIsActive(Boolean.TRUE);
		try {
//			String url = ansProperties.getValue(AnsProperties.UDHYAM_API_URL);
			String url = UrlsAns.getLocalIpAddress(UrlType.UDHYAM).concat("/get-details");
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<UdhyamAadharRequest> entity = new HttpEntity<UdhyamAadharRequest>(udhyamAadharRequest, headers);
			UdhyamAadharResponse res = restTemplate.exchange(url, HttpMethod.POST, entity, UdhyamAadharResponse.class)
					.getBody();
			if (res != null) {
				if(res.getStatus() == 1001) {
					udhyamAPIAudit.setReferenceId(res.getReferenceId());
					udhyamAPIAudit.setMessage(res.getMessage());
					res.setFlag(Boolean.TRUE);
				} else {
					udhyamAPIAudit.setIsActive(Boolean.FALSE);
					udhyamAPIAudit.setMessage(res.getMessage());
					res.setFlag(Boolean.FALSE);
				}
//				log.info("getFreshData res==>{}",res);
				log.info("At udyam " + udhyamAadharRequest.getUdhyamNo() + " Data found==> " + res.getFlag());
				return res;
			}
			udhyamAPIAudit.setIsActive(Boolean.FALSE);
			udhyamAPIAudit.setMessage("No Data Found");
//			log.info("UdhyamAadhar No data found for " + udhyamAadharRequest.getUdhyamNo() + " ===> Response Is Null ====>");
			log.info("At udyam " + udhyamAadharRequest.getUdhyamNo() + " Data found==> " + res.getFlag());
		} catch (Exception e) {
			udhyamAPIAudit.setIsActive(Boolean.FALSE);
			udhyamAPIAudit.setMessage(e.getMessage());
			log.error("Exception while Get UdhyamAadhr API For ", e);
		} finally {
			udhyamAPIAudit.setModifiedDate(new Date());
			udhyamAPIAuditRepo.save(udhyamAPIAudit);
		}
		return null;
	}

	private UdhyamAadharResponse getExistingData(String refId) {
		log.info("Into getExistingData with RefId==>{}",refId);
		UdhyamAPIAudit udhyamAPIAudit = new UdhyamAPIAudit();
		udhyamAPIAudit.setCreatedDate(new Date());
		udhyamAPIAudit.setIsActive(Boolean.FALSE);
		udhyamAPIAudit.setReferenceId(refId);
		try {
//			String url = ansProperties.getValue(AnsProperties.UDHYAM_GET_BY_REFERENCE_API_URL) + "/" + refId;
			String url =  UrlsAns.getLocalIpAddress(UrlType.UDHYAM).concat("/get-details-by-reference") + "/" + refId;
			RestTemplate restTemplate = new RestTemplate();
			UdhyamAadharResponse res = restTemplate.getForEntity(url, UdhyamAadharResponse.class).getBody();
			if (res != null) {
				if(res.getStatus() == 1001) {
					udhyamAPIAudit.setMessage(res.getMessage());
					udhyamAPIAudit.setIsActive(Boolean.TRUE);
					res.setFlag(Boolean.TRUE);
				} else {
					udhyamAPIAudit.setMessage(res.getMessage());
					res.setFlag(Boolean.FALSE);
				}
				log.info("getExistingData res==>{}",res.getStatus());
				return res;
			}
			udhyamAPIAudit.setMessage("No Data Found");
			log.info("Existing UdhyamAadhar No data found for " + refId + " ===> Response Is null ====>");
		} catch (Exception e) {
			udhyamAPIAudit.setMessage(e.getMessage());
			log.error("Exception while Get Existing UdhyamAadhr API For " + refId, e);
		} finally {
			udhyamAPIAudit.setModifiedDate(new Date());
			udhyamAPIAuditRepo.save(udhyamAPIAudit);
		}
		return null;
	}
}
