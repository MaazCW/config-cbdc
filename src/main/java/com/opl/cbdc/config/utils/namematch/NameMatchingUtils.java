package com.opl.cbdc.config.utils.namematch;

import com.opl.cbdc.utils.common.*;
import com.opl.cbdc.utils.config.*;
import com.opl.cbdc.utils.config.UrlsAns.*;
import com.opl.cbdc.config.domain.namematch.*;
import com.opl.cbdc.config.repository.namematch.*;
import com.opl.cbdc.config.utils.*;
import com.opl.cbdc.config.utils.namematch.enums.*;
import com.opl.cbdc.config.utils.namematch.models.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;

@Component
@Slf4j
public class NameMatchingUtils {

    @Autowired
    private NameMatchingMasterRepo nameMatchingMasterRepo;

//    @Autowired
//    private void setApplicationContext(NameMatchingMasterRepo nameMatchingMasterRepo) {
//        this.nameMatchingMasterRepo = nameMatchingMasterRepo;
//    }

    @Autowired
    private NameMatchingSchemeMasterRepo nameMatchingSchemeMasterRepo;

    @Autowired
    private  AnsProperties ansProperties;

    @Autowired
    private static GatewayEncryptionUtils gatewayEncryptionUtils;
    
    @Autowired
    private NameMatchApiMasterRepo nameMatchApiMasterRepo; 
    
	public static final String REQUEST_HEADER_AUTHENTICATE = "req_auth";
	public static final String REQUEST_HEADER_AUTHENTICATE_VALUE = "true";
	private static final String PERCENTAGE = "percentage";
	private static final String REFERENCE_ID = "referenceId";
    private static final String API_TYPE = "apiType";
    private static final String BANK_SET_PERCENTAGE = "bankSetPercentage";
    
		

    @Async
    public void checkNameMatch(NameMatchRequest nameMatchProxy) {
        try {
            callNameMatch(nameMatchProxy);
        } catch (Exception e) {
            log.error("Exception while Calling name matche ============> ", e);
        }
    }

    public CommonResponse callNameMatch(NameMatchRequest nameMatchProxy) {
        if (OPLUtils.isObjectNullOrEmpty(nameMatchProxy) || (nameMatchProxy.getApplicationId() == null && nameMatchProxy.getCoApplicantId() == null)
                || nameMatchProxy.getSchemeId() == null || nameMatchProxy.getInputTypeId() == null) {
            return new CommonResponse("Request parameter should not be null or empty.", HttpStatus.BAD_REQUEST.value(), Boolean.FALSE);
//            return null;
        }
        if(OPLUtils.isObjectNullOrEmpty(nameMatchProxy.getOrgId())) {
            nameMatchProxy.setOrgId(Long.parseLong("-1"));
        }
        log.info("Enter in Check Name at applicationId --------------------------> {}", nameMatchProxy.getApplicationId());
        try {
            // THIS LIST ARE CONSIDER FOR BASE_ID
            List<Integer> inputTypeIds = new ArrayList<>();
            inputTypeIds.add(NameMatchBaseId.AADHAR.getId());
            inputTypeIds.add(NameMatchBaseId.PAN.getId());
            inputTypeIds.add(NameMatchBaseId.CBDT.getId());
            //CHECK THAT DATA IS EXISTS ON APP/CO-APP ID AND INPUT_TYPE_ID
            NameMatchingMaster nameMatchingMaster = null;
            // IN CASE OF NRLM SCHEME INSTEAD OF APPLICATION_ID WE ARE USEING MEMBER_ID
            if (nameMatchProxy.getCoApplicantId() != null) {
                nameMatchingMaster = nameMatchingMasterRepo.getByCoApplicationId(nameMatchProxy.getApplicationId(), nameMatchProxy.getCoApplicantId(), inputTypeIds);
            } else {
                nameMatchingMaster = nameMatchingMasterRepo.getByApplicationId(nameMatchProxy.getApplicationId(), inputTypeIds);
            }
            //Check nameMatchingMaster object has data if yes then get baseId from it
            if (nameMatchingMaster != null && nameMatchProxy.getInputName() != null) {
                NameMatchingMaster matchingMaster = null;
                if (nameMatchProxy.getCoApplicantId() != null) {
                    matchingMaster = nameMatchingMasterRepo.findByApplicationIdAndCoApplicantIdAndInputTypeIdAndBaseIdOrBaseIdIsNullAndIsActive(nameMatchProxy.getApplicationId(), nameMatchProxy.getCoApplicantId(), nameMatchProxy.getInputTypeId(), nameMatchingMaster.getInputTypeId(), Boolean.TRUE);
                } else {
//                    matchingMaster = nameMatchingMasterRepo.findByApplicationIdAndInputTypeIdAndBaseIdAndIsActive(nameMatchProxy.getApplicationId(), nameMatchProxy.getInputTypeId(), nameMatchingMaster.getInputTypeId(), Boolean.TRUE);
                    matchingMaster = nameMatchingMasterRepo.findByApplicationIdAndInputTypeIdAndBaseIdOrBaseIdIsNullAndIsActive(nameMatchProxy.getApplicationId(), nameMatchProxy.getInputTypeId(), nameMatchingMaster.getInputTypeId(), Boolean.TRUE);
                }
                if (OPLUtils.isObjectNullOrEmpty(matchingMaster)) {
                    matchingMaster = new NameMatchingMaster();
                } else if (matchingMaster.getBaseId() == null && matchingMaster.getInputTypeId() != null && matchingMaster.getInputTypeId().equals(nameMatchProxy.getInputTypeId())) {
                    matchingMaster.setInputName(nameMatchProxy.getInputName());
                    matchingMaster = nameMatchingMasterRepo.save(matchingMaster);
                    log.info("Data already exists at given inputYypeId and with baseId NULL");
                    return new CommonResponse("Data updated successfully.", matchingMaster, HttpStatus.OK.value(), Boolean.TRUE);
                }
                matchingMaster.setInputName(nameMatchProxy.getInputName());
                matchingMaster.setApplicationId(nameMatchProxy.getApplicationId());
                matchingMaster.setCoApplicantId(nameMatchProxy.getCoApplicantId());
                matchingMaster.setInputTypeId(nameMatchProxy.getInputTypeId());
                matchingMaster.setCreatedDate(new Date());
                matchingMaster.setBaseId(nameMatchingMaster.getInputTypeId());
                matchingMaster.setIsActive(Boolean.TRUE);
                log.info("Calling Name match Api for applicationId " + nameMatchProxy.getApplicationId() + "And baseId " + nameMatchProxy.getBaseId() + " And co-AppId " + (nameMatchProxy.getCoApplicantId() != null ? nameMatchProxy.getCoApplicantId() : null));

                // GET ALGO AND PERCENTAGE
//                NameMatchingSchemeMaster nameMatchingSchemeMaster = nameMatchingSchemeMasterRepo.findBySchemeIdAndOrgId(nameMatchProxy.getSchemeId(), nameMatchProxy.getOrgId());  
                NameMatchingSchemeMaster nameMatchingSchemeMaster = nameMatchingSchemeMasterRepo.findBySchemeIdAndOrgIdAndMatchInputMasterInputTypeIdAndMatchInputMasterIsForKcc(nameMatchProxy.getSchemeId(), nameMatchProxy.getOrgId(),nameMatchProxy.getInputTypeId(), Boolean.FALSE);  
                  
                //CALL NAME MATCH API
// 				Double percentageFromApi = callNameMatchingApi(nameMatchProxy.getInputName(), nameMatchingMaster.getInputName(), algoName);
//              Double percentageFromApi = nameMatchingApi(nameMatchProxy.getInputName(), nameMatchingMaster.getInputName(), nameMatchingSchemeMaster);
                Map<String, String> map = nameMatchingApi(nameMatchProxy.getInputName(), nameMatchingMaster.getInputName(), nameMatchingSchemeMaster);
                log.info("RECIEVED RESPONSE callNameMatchingApi-----------------------------> {}", map);
                if (!OPLUtils.isObjectNullOrEmpty(map) && !OPLUtils.isObjectNullOrEmpty(map.get(PERCENTAGE))) {
                    log.info("ENTER IN IF CONDITION ----------------------------->");
                    // GET PERCENTAGE FROM NAME_MATCHING_SCHEME_MASTER AND COMPARE IT WITH NAME MATCH API PERCENTAGE
                    Double percentageFromApi = Double.parseDouble(map.get(PERCENTAGE));
                    Double percentage = nameMatchingSchemeMaster.getPercentage();
                    if (percentageFromApi < percentage) {
                        matchingMaster.setIsMatch(Boolean.FALSE);
                    } else {
                        matchingMaster.setIsMatch(Boolean.TRUE);
                    }
                    matchingMaster.setPercentage(percentageFromApi);
                    matchingMaster.setRefId(map.get(REFERENCE_ID));
                    matchingMaster.setApiType(map.get(API_TYPE));
                    if(!OPLUtils.isObjectNullOrEmpty(map.get("bankSetPercentage"))){
                    	matchingMaster.setBankSetPercentage(Double.valueOf(map.get("bankSetPercentage")));
                   } 
                    
                } else {
                    if(!OPLUtils.isObjectNullOrEmpty(map)) {
                        matchingMaster.setRefId(map.get(REFERENCE_ID));
                        matchingMaster.setApiType(map.get(API_TYPE));
                    }
                    matchingMaster.setMessage("Something went wrong while calling NameMatchApi.");
                }
                log.info("BEFORE SAVEING ----------------------------->");
                matchingMaster = nameMatchingMasterRepo.save(matchingMaster);
                log.info("COMPLETED & RETURN MATCHING SERVICE INFO  ----------------------------->");
                return new CommonResponse("Data saved successfully.", matchingMaster, HttpStatus.OK.value(), Boolean.TRUE);
//                return null;
            }
            log.info("START CHECK COAPPLICANT AND APPLICANT CONDITIOn");
            nameMatchingMaster = null;
            if (nameMatchProxy.getCoApplicantId() != null) {
                nameMatchingMaster = nameMatchingMasterRepo.findByCoApplicantIdAndInputTypeIdAndIsActive(nameMatchProxy.getCoApplicantId(), nameMatchProxy.getInputTypeId(), Boolean.TRUE);
            } else {
                nameMatchingMaster = nameMatchingMasterRepo.findByApplicationIdAndInputTypeIdAndIsActive(nameMatchProxy.getApplicationId(), nameMatchProxy.getInputTypeId(), Boolean.TRUE);
            }
            log.info("START CHECK COAPPLICANT AND APPLICANT CONDITIOn  ---- SUCCESSFULLY FETCHED DATA");
            if (nameMatchingMaster == null) {
                nameMatchingMaster = new NameMatchingMaster();
            }
            nameMatchingMaster.setInputName(nameMatchProxy.getInputName());
            nameMatchingMaster.setApplicationId(nameMatchProxy.getApplicationId());
            nameMatchingMaster.setCoApplicantId(nameMatchProxy.getCoApplicantId());
            nameMatchingMaster.setInputTypeId(nameMatchProxy.getInputTypeId());
            nameMatchingMaster.setCreatedDate(new Date());
            String msg = "Data for BaseId or inputName in request is null or empty at applicationId " + nameMatchProxy.getApplicationId() + " And inputTypeId " + nameMatchProxy.getInputTypeId();
            nameMatchingMaster.setMessage(nameMatchProxy.getMessage() != null ? nameMatchProxy.getMessage() : msg);
            nameMatchingMaster.setIsActive(Boolean.TRUE);
            nameMatchingMasterRepo.save(nameMatchingMaster);
            log.info("START CHECK COAPPLICANT AND APPLICANT CONDITIOn  ---- SUCCESSFULLY SAVED DATA");
        } catch (Exception e) {
            log.error("Exception while chechMatching", e);
            return new CommonResponse("The application has encountered some error, please try after sometimes.", HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE);
        }
        log.info("NAME MATCHING END ---------------------------------------------->");
        return new CommonResponse("Data inserted.", HttpStatus.OK.value(), Boolean.TRUE);
    }

    public List<NameMatchingMasterProxy> getListByApplicationId(Long applicationId, Long coApplicantId) {
        if (applicationId == null && coApplicantId == null) {
            log.info("ApplicationId should not be null or empty.");
            return Collections.emptyList();
        }
        log.info("Enter in getListByApplicationId method at applicationId" + applicationId);
        List<NameMatchingMaster> nameMatchingMasterList = new ArrayList<>();
        if (coApplicantId != null) {
            nameMatchingMasterList = nameMatchingMasterRepo.findByCoApplicantIdAndIsActive(coApplicantId, Boolean.TRUE);
        } else {
            nameMatchingMasterList = nameMatchingMasterRepo.findByApplicationIdAndIsActive(applicationId, Boolean.TRUE);
        }
        if (OPLUtils.isListNullOrEmpty(nameMatchingMasterList)) {
            log.info("No data found at applicationId" + applicationId + " And co-AppId " + (coApplicantId != null ? coApplicantId : null));
            return Collections.emptyList();
        }
        List<NameMatchingMasterProxy> matchingMasterProxyList = new ArrayList<>(nameMatchingMasterList.size());
        NameMatchingMasterProxy nameMatchingMasterProxy = null;
        for (NameMatchingMaster nameMatchingMaster : nameMatchingMasterList) {
            nameMatchingMasterProxy = new NameMatchingMasterProxy();
            BeanUtils.copyProperties(nameMatchingMaster, nameMatchingMasterProxy);
            nameMatchingMasterProxy.setInputTypeName(NameMatchBaseId.fromId(nameMatchingMaster.getInputTypeId()).getValue());
            nameMatchingMasterProxy.setDisplayName(NameMatchBaseId.fromId(nameMatchingMaster.getInputTypeId()).getDisplayName());
            matchingMasterProxyList.add(nameMatchingMasterProxy);
        }
        return matchingMasterProxyList;
    }

    
    @Deprecated
    public Double callNameMatchingApi(String inputName, String matchWith, String algoName) {
        log.info("Enter in Name Matching Algorithm --------------> callNameMatchingApi---------- ");
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            String ansPropertiesValue = ansProperties.getValue(AnsProperties.NAME_MATCH_ALGO);

            Map<String, Object> ansProperMap = MultipleJSONObjectHelper.getMapFromString(ansPropertiesValue);
            String url = ansProperMap.get("url").toString();

            headers.add("clientId", ansProperMap.get("clientId").toString());
            headers.add("secretId", ansProperMap.get("secretId").toString());
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> map = new HashMap<>();
            map.put("inputName", inputName);
            map.put("matchWith", matchWith);
            if (!OPLUtils.isObjectNullOrEmpty(algoName)) {
                map.put("algoName", algoName);
            }

//            ENCRYPT DATA
            log.info("BEFORE gatewayEncryptionUtils.encryptContent---------------------->{}" , map);
            String body = gatewayEncryptionUtils.encryptContent(MultipleJSONObjectHelper.getStringfromObject(map));
//            log.info("AFTER gatewayEncryptionUtils.encryptContent---------------------->");
            HttpEntity<?> entity = new HttpEntity<>(body, headers);

//            CALLING REST TEMPLATE FOR MATCHING
//            log.info("BEFORE gatewayEncryptionUtils.decryptContent --------------------->");
            NameMatchResponse nameMatchResponse = gatewayEncryptionUtils.decryptContent(restTemplate.exchange(url, HttpMethod.POST, entity, GatewayRequest.class).getBody());
            log.info("AFTER gatewayEncryptionUtils.decryptContent ---------------------> {}" , nameMatchResponse);
            if (!OPLUtils.isObjectNullOrEmpty(nameMatchResponse) && !OPLUtils.isObjectNullOrEmpty(nameMatchResponse.getData())
                    && !OPLUtils.isObjectNullOrEmpty(nameMatchResponse.getStatus()) && nameMatchResponse.getStatus() == 1000) {
                log.info("END callNameMatchingApi  ------------------- " + nameMatchResponse.getData());
                return Double.parseDouble(nameMatchResponse.getData());
            } else {
                log.info(nameMatchResponse.getMessage());
                log.info("@@@@@@@@@@@@@@@@@ Request is -----------> {}" , map);
            }

        } catch (Exception e) {
            log.error("Exception while calling Name matching Api", e);
        }
        return null;
    }
    
    public Map<String, String> nameMatchingApi(String inputName, String matchWith, NameMatchingSchemeMaster nameMatchingSchemeMaster) {
        log.info("Enter in nameMatchingApi() -----------> ");
        try {
        	String REST_URL =  UrlsAns.getLocalIpAddress(UrlType.NAME_MATCH);
//        	String REST_URL = "http://localhost:1125/namematch";
        	
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set(OPLUtils.REQUEST_HEADER_AUTHENTICATE, OPLUtils.REQUEST_HEADER_AUTHENTICATE_VALUE);
    		headers.add("Accept", "application/json");
            headers.setContentType(MediaType.APPLICATION_JSON);

            NameMatchRequestProxy nameMatchRequestProxy = new NameMatchRequestProxy();
            nameMatchRequestProxy.setName1(inputName);
            nameMatchRequestProxy.setName2(matchWith);
            if(!OPLUtils.isObjectNullOrEmpty(nameMatchingSchemeMaster.getAlgorithmId())) {
            	nameMatchRequestProxy.setAlgoName(nameMatchingSchemeMaster.getAlgorithmId().getValue());	
            }        	
            if(!OPLUtils.isObjectNullOrEmpty(nameMatchingSchemeMaster.getPreset())) {
            	nameMatchRequestProxy.setPreset(nameMatchingSchemeMaster.getPreset().getValue());
            }
            if(OPLUtils.isObjectNullOrEmpty(nameMatchingSchemeMaster.getApiId())) {
            	log.warn("Name match API master id is null or empty--------------------------------> ");
            	return null;
            }
            Map<String, String> map = new HashMap<>();
            NameMatchApiMaster nameMatchApiMaster = nameMatchingSchemeMaster.getApiId();
            String url = REST_URL.concat(nameMatchApiMaster.getEndPoint());
            HttpEntity<?> entity = new HttpEntity<>(nameMatchRequestProxy, headers);
            NameMatchResponse nameMatchResponse = restTemplate.exchange(url, HttpMethod.POST, entity, NameMatchResponse.class).getBody();
            if(OPLUtils.isObjectNullOrEmpty(nameMatchResponse) || (!OPLUtils.isObjectNullOrEmpty(nameMatchResponse) && OPLUtils.isObjectNullOrEmpty(nameMatchResponse.getData()))) {
            	if(!OPLUtils.isObjectNullOrEmpty(nameMatchingSchemeMaster.getFailureApiId())) {
            		nameMatchApiMaster = nameMatchingSchemeMaster.getFailureApiId();
            		log.info("CALLING FAILURE API ------> {}", nameMatchApiMaster.getValue());
            		url = REST_URL.concat(nameMatchApiMaster.getEndPoint());
            		nameMatchResponse = restTemplate.exchange(url, HttpMethod.POST, entity, NameMatchResponse.class).getBody();
            		map.put(PERCENTAGE, OPLUtils.isObjectNullOrEmpty(nameMatchResponse) ? null : (OPLUtils.isObjectNullOrEmpty(nameMatchResponse.getData()) ? null : nameMatchResponse.getData().toString()));
            		map.put(REFERENCE_ID, nameMatchResponse.getReferenceId());
                    map.put(API_TYPE, nameMatchApiMaster.getId().toString());
                    
                    if(nameMatchingSchemeMaster.getApiId().getId() == NameMatchApiEnum.KARZA.getId()) {
                    	map.put(BANK_SET_PERCENTAGE, String.valueOf(nameMatchingSchemeMaster.getKarzaPercentage()));
                    }else {
                    	map.put(BANK_SET_PERCENTAGE, String.valueOf(nameMatchingSchemeMaster.getPercentage()));
                    }
            		return map;
            	}
            }
    		map.put(PERCENTAGE, OPLUtils.isObjectNullOrEmpty(nameMatchResponse) ? null : (OPLUtils.isObjectNullOrEmpty(nameMatchResponse.getData()) ? null : nameMatchResponse.getData().toString()));
    		map.put(REFERENCE_ID, nameMatchResponse.getReferenceId());
            map.put(API_TYPE, nameMatchApiMaster.getId().toString());
            if(nameMatchingSchemeMaster.getApiId().getId() == NameMatchApiEnum.KARZA.getId()) {
            	map.put(BANK_SET_PERCENTAGE, String.valueOf(nameMatchingSchemeMaster.getKarzaPercentage()));
            }else {
            	map.put(BANK_SET_PERCENTAGE, String.valueOf(nameMatchingSchemeMaster.getPercentage()));
            }
    		return map;
        } catch (Exception e) {
            log.error("Exception while calling Name matching Api -----------> ", e);
        }
        return null;
    }
    
}
	