package com.opl.cbdc.config.thirdparty.nrlm;

import com.opl.cbdc.utils.common.*;
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
public class NRLMAPI {

    @Autowired
    private AnsProperties ansProperties;

    @Autowired
    private NRLMAuditRepo pmSvanidhiAPIAuditRepo;

//    @Autowired
//    private ReqResLogsRepo reqResLogsRepo;
    
	@Autowired
	private ReadFileToString readFileToString;

    private final static String USER_NAME = "user_name";
    private final static String USER_PASS = "user_password";
    private final static String SUCCESS_LOGIN = "Successfully Login";
    private final static String NO_DATA_FOUND = "No data found";
    private final static String USER_NAME_PASS_NOT_MATCHED = "UserName and Pass not matched !!";
    private final static String SERVICE_NOT_AVAILABLE = "NRLM service in under maintainace !!";
    private final static String RESPONSE_NULL_OR_EMPTY = "Response is null or empty !!";
    private final static String EXCEPTION_WHILE_CALL_LOGIN_API_NRLM = "Exception while Call Login API for NRLM";
    private final static String EXCEPTION_WHILE_CALL_APPLICATION_LIST_API_NRLM = "Exception while Call Application List for NRLM";
    private final static String EXCEPTION_WHILE_CALL_APPLICATION_DETAILS_API_NRLM = "Exception while Call Application Details for NRLM";

    public NRLMLoginRes login(String userName, String pass) {
        NRLMAPIAudit nrlmAPIAudit = new NRLMAPIAudit();
        nrlmAPIAudit.setType(NRLMAPIAudit.TypeMaster.LOGIN_API);
        nrlmAPIAudit.setCreatedDate(new Date());
        nrlmAPIAudit.setIsActive(Boolean.TRUE);
        nrlmAPIAudit.setIsSuccess(Boolean.FALSE);
//        ReqResLogs reqResLogs = new ReqResLogs();
        try {
            String url = ansProperties.getValue(AnsProperties.NRLM_LOGIN_API);
            nrlmAPIAudit.setUrl(url);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> map = new HashMap<>();
            map.put(USER_NAME, userName);
            map.put(USER_PASS, pass);
//            reqResLogs.setRequest(map.toString());
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, headers);
            String loginRes = "";
            if(ansProperties.getValue(LoadTestingEnum.TESTING_MODE_ON_OFF.getKey()).equals(LoadTestingEnum.TESTING_MODE.getKey())) {
            	loginRes = LoadTestingEnum.NRLM_LOGIN_API.getCode();
            }else {
            	loginRes = restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
            }
            
            
//            reqResLogs.setResponse(loginRes);

            Map<String, Object> mapMainObj = MultipleJSONObjectHelper.getObjectFromString(loginRes, Map.class);
            if(mapMainObj != null && !OPLUtils.isObjectNullOrEmpty(mapMainObj.get("login"))) {
            	boolean flag = false;
            	if(mapMainObj.get("login") != null) {
                    System.out.println(mapMainObj.get("login"));
            		Map<String, Object> mapObj = MultipleJSONObjectHelper.getObjectFromObject(mapMainObj.get("login"), Map.class);
            		if(mapObj.get("status") != null) {
                        System.out.println(mapMainObj.get("status"));
            			flag = Boolean.valueOf(mapObj.get("status").toString());
            		}
            	}
                NRLMLoginRes res = new NRLMLoginRes();
                res.setFlag(flag);
                if(flag) {
                    nrlmAPIAudit.setIsSuccess(Boolean.TRUE);
                    nrlmAPIAudit.setMessage(SUCCESS_LOGIN);
                    res.setStatus(HttpStatus.OK.value());
                    return res;
                } else {
                    nrlmAPIAudit.setMessage(USER_NAME_PASS_NOT_MATCHED);
                }
            } else {
                nrlmAPIAudit.setMessage(RESPONSE_NULL_OR_EMPTY);
            }
        } catch (Exception e) {
            nrlmAPIAudit.setMessage(e.getMessage());
            log.error(EXCEPTION_WHILE_CALL_LOGIN_API_NRLM, e);
        } finally {
//            reqResLogs = reqResLogsRepo.save(reqResLogs);
//            nrlmAPIAudit.setReqResLogs(reqResLogs);
            if(OPLUtils.isObjectNullOrEmpty(nrlmAPIAudit.getMessage())) {
                nrlmAPIAudit.setMessage(SERVICE_NOT_AVAILABLE);
            }
            nrlmAPIAudit.setModifiedDate(new Date());
            pmSvanidhiAPIAuditRepo.save(nrlmAPIAudit);
        }
        return new NRLMLoginRes(Boolean.FALSE, HttpStatus.SERVICE_UNAVAILABLE.value());
    }

    public NRLMApplicationListRes getApplicationList(String userName) {
        log.info("Enter in NRLM fetch Application List for " + userName);
        NRLMAPIAudit nrlmAPIAudit = new NRLMAPIAudit();
        nrlmAPIAudit.setType(NRLMAPIAudit.TypeMaster.APPLICATION_LIST);
        nrlmAPIAudit.setCreatedDate(new Date());
        nrlmAPIAudit.setIsActive(Boolean.TRUE);
        nrlmAPIAudit.setIsSuccess(Boolean.FALSE);
//        Code commented for handle load in production
//        ReqResLogs reqResLogs = new ReqResLogs();
        try {
            String url = ansProperties.getValue(AnsProperties.NRLM_FETCH_APPLICATION_LIST) + userName;
            nrlmAPIAudit.setUrl(url);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
//            reqResLogs.setRequest(userName);
            HttpEntity<?> entity = new HttpEntity<>(null, headers);
            String loginRes = "";
            if(ansProperties.getValue(LoadTestingEnum.TESTING_MODE_ON_OFF.getKey()).equals(LoadTestingEnum.TESTING_MODE.getKey())) {
            	loginRes = LoadTestingEnum.NRLM_FETCH_APPLICATION_LIST.getCode();
            }else {
            	loginRes = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();	
            }
            
//            reqResLogs.setResponse(loginRes);
            NRLMApplicationListRes res = MultipleJSONObjectHelper.getObjectFromString(loginRes, NRLMApplicationListRes.class);
            if(res != null && res.getApplicationList() != null && res.getApplicationList().size() > 0) {
                nrlmAPIAudit.setIsSuccess(Boolean.TRUE);
                nrlmAPIAudit.setMessage(SUCCESS_LOGIN);
                log.info("Exit in NRLM fetch successfully Application List for " + userName);
                return res;
            } else {
                log.info("Exit in NRLM fetch Application List No Data Found for " + userName);
                nrlmAPIAudit.setMessage(RESPONSE_NULL_OR_EMPTY);
            }
        } catch (Exception e) {
            nrlmAPIAudit.setMessage(e.getMessage());
            log.error(EXCEPTION_WHILE_CALL_APPLICATION_LIST_API_NRLM, e);
        } finally {
//            reqResLogs = reqResLogsRepo.save(reqResLogs);
//            nrlmAPIAudit.setReqResLogs(reqResLogs);
            nrlmAPIAudit.setModifiedDate(new Date());
            pmSvanidhiAPIAuditRepo.save(nrlmAPIAudit);
        }
        return null;
    }

    public NRLMApplicationDetailsProxy getApplicationDetails(Long nrlmApplicationId) {
        log.info("Enter in NRLM fetch Application Details for " + nrlmApplicationId);
        NRLMAPIAudit nrlmAPIAudit = new NRLMAPIAudit();
        nrlmAPIAudit.setType(NRLMAPIAudit.TypeMaster.APPLICATION_DETAILS);
        nrlmAPIAudit.setCreatedDate(new Date());
        nrlmAPIAudit.setIsActive(Boolean.TRUE);
        nrlmAPIAudit.setIsSuccess(Boolean.FALSE);

//        Code commented for handle load in production
//        ReqResLogs reqResLogs = new ReqResLogs();
        try {
            String url = ansProperties.getValue(AnsProperties.NRLM_FETCH_APPLICATION_DETAILS) + nrlmApplicationId;
            nrlmAPIAudit.setUrl(url);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
//            reqResLogs.setRequest(nrlmApplicationId.toString());
            HttpEntity<?> entity = new HttpEntity<>(null, headers);
            String loginRes = "";
            if(ansProperties.getValue(LoadTestingEnum.TESTING_MODE_ON_OFF.getKey()).equals(LoadTestingEnum.TESTING_MODE.getKey())) {
            	loginRes = readFileToString.readUsingApacheCommonsIO(LoadTestingEnum.NRLM_FETCH_APPLICATION_DETAILS.getCode());
            }else {
            	loginRes = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();	
            }
            
//            reqResLogs.setResponse(loginRes);
            @SuppressWarnings("unchecked")
			List<NRLMApplicationDetailsProxy> resList = MultipleJSONObjectHelper.getListOfObjects(loginRes, null, NRLMApplicationDetailsProxy.class);
            if(resList == null || resList.size() == 0) {
                nrlmAPIAudit.setIsSuccess(Boolean.TRUE);
                nrlmAPIAudit.setMessage(NO_DATA_FOUND);
                log.info("Enter in NRLM fetch Application Details No Data Found for " + nrlmApplicationId);
                return null;
            }
            nrlmAPIAudit.setIsSuccess(Boolean.TRUE);
            nrlmAPIAudit.setMessage(SUCCESS_LOGIN);
            log.info("Enter in NRLM fetch successfully Application Details No for " + nrlmApplicationId);
            return resList.get(0);
        } catch (Exception e) {
            nrlmAPIAudit.setMessage(e.getMessage());
            log.error(EXCEPTION_WHILE_CALL_APPLICATION_DETAILS_API_NRLM, e);
        } finally {
//            reqResLogs = reqResLogsRepo.save(reqResLogs);
//            nrlmAPIAudit.setReqResLogs(reqResLogs);
            nrlmAPIAudit.setModifiedDate(new Date());
            pmSvanidhiAPIAuditRepo.save(nrlmAPIAudit);
        }
        return null;
    }


}
