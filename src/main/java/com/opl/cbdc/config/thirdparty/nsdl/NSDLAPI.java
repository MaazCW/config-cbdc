package com.opl.cbdc.config.thirdparty.nsdl;

import com.opl.cbdc.utils.config.*;
import com.opl.cbdc.utils.config.UrlsAns.*;
import com.opl.cbdc.config.domain.*;
import com.opl.cbdc.config.repository.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;

@Component
@Slf4j
public class NSDLAPI {

    @Autowired
    private NSDLAPIAuditRepo nsdlapiAuditRepo;

//    @Autowired
//    private AnsProperties ansProperties;

    public NSDLResponse getData(String pan) {
        try {
            NSDLAPIAudit nsdlAPIAudit = nsdlapiAuditRepo.getByPanDetails(pan);
            if(nsdlAPIAudit != null) {
                return getExistingData(nsdlAPIAudit.getReferenceId());
            }
            return getFreshData(pan);
        } catch (Exception e) {
            log.error("Exception while Get NSDL PAN API For " + pan, e);
        }
        return null;

    }

    private NSDLResponse getExistingData(String refId) {
        NSDLAPIAudit nsdlAPIAudit = new NSDLAPIAudit();
        nsdlAPIAudit.setCreatedDate(new Date());
        nsdlAPIAudit.setReferenceId(refId);
        nsdlAPIAudit.setIsActive(Boolean.FALSE);
        try {
//            String url = ansProperties.getValue(AnsProperties.NSDL_PAN_GET_BY_REFERENCE_API_URL) + "/" + refId;
            String url=UrlsAns.getLocalIpAddress(UrlType.NSDL_PAN).concat("/get-by-referenceId")+ "/" + refId;

            RestTemplate restTemplate = new RestTemplate();
            NSDLResponse res = restTemplate.getForEntity(url, NSDLResponse.class).getBody();
            if (res != null) {
                if(res.getStatus() == 1001) {
                    nsdlAPIAudit.setMessage(res.getMessage());
                    nsdlAPIAudit.setIsActive(Boolean.TRUE);
                    res.setFlag(Boolean.TRUE);
                } else {
                    nsdlAPIAudit.setMessage(res.getMessage());
                    res.setFlag(Boolean.FALSE);
                }
                return res;
            }
            nsdlAPIAudit.setMessage("No Data Found");
            log.info("Existing NSDL PAN No data found for " + refId + " ===> Response Is null ====>");
        } catch (Exception e) {
            nsdlAPIAudit.setMessage(e.getMessage());
            log.error("Exception while Get Existing NSDL PAN API For " + refId, e);
        } finally {
            nsdlAPIAudit.setModifiedDate(new Date());
            nsdlapiAuditRepo.save(nsdlAPIAudit);
        }
        return null;
    }

    private NSDLResponse getFreshData(String pan) {
        NSDLAPIAudit nsdlAPIAudit = new NSDLAPIAudit();
        nsdlAPIAudit.setPan(pan);
        nsdlAPIAudit.setCreatedDate(new Date());
        nsdlAPIAudit.setIsActive(Boolean.FALSE);
        try {
//            String url = ansProperties.getValue(AnsProperties.NSDL_PAN_API_URL);
        	String url=UrlsAns.getLocalIpAddress(UrlType.NSDL_PAN).concat("/verify");
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> map = new HashMap<>();
            map.put("pan", pan);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, headers);
            NSDLResponse res = restTemplate.exchange(url, HttpMethod.POST, entity, NSDLResponse.class).getBody();
            if (res != null) {
                if(res.getStatus() == 1001) {
                    nsdlAPIAudit.setReferenceId(res.getReferenceId());
                    nsdlAPIAudit.setMessage(res.getMessage());
                    nsdlAPIAudit.setIsActive(Boolean.TRUE);
                    res.setFlag(Boolean.TRUE);
                } else {
                    nsdlAPIAudit.setMessage(res.getMessage());
                    res.setFlag(Boolean.FALSE);
                }
                return res;
            }
            nsdlAPIAudit.setMessage("No Data Found");
            log.info("NSDL PAN No data found for " + pan + " ===> Response Is Null ====>");
        } catch (Exception e) {
            nsdlAPIAudit.setMessage(e.getMessage());
            log.error("Exception while Get NSDL PAN API For " + pan, e);
        } finally {
            nsdlAPIAudit.setModifiedDate(new Date());
            nsdlapiAuditRepo.save(nsdlAPIAudit);
        }
        return null;
    }
}
