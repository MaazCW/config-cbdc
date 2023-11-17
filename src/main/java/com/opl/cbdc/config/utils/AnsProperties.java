package com.opl.cbdc.config.utils;


import com.opl.cbdc.config.domain.*;
import com.opl.cbdc.config.repository.*;
import com.opl.cbdc.utils.common.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * @author harshit.suhagiya
 */
@Component
@Slf4j
public class AnsProperties {

    @Autowired
    private AnsConfigMasterRepo ansConfigMasterRepo;

    @Autowired
    private MultipleBankConfigRepo multipleBankConfigRepo;

    public static String UDHYAM_API_URL = "UDHYAM_API_URL";
    public static String PM_SVANIDHI_REQUEST_MOBILE_OTP_API_URL = "PM_SVANIDHI_REQUEST_MOBILE_OTP_API_URL";
    public static String PM_SVANIDHI_VERIFY_MOBILE_OTP_API_URL = "PM_SVANIDHI_VERIFY_MOBILE_OTP_API_URL";
    public static String UDHYAM_GET_BY_REFERENCE_API_URL = "UDHYAM_GET_BY_REFERENCE_API_URL";
    public static String NSDL_PAN_API_URL = "NSDL_PAN_API_URL";
    public static String NSDL_PAN_GET_BY_REFERENCE_API_URL = "NSDL_PAN_GET_BY_REFERENCE_API_URL";

    public static String NRLM_LOGIN_API = "NRLM_LOGIN_API";
    public static String NRLM_FETCH_APPLICATION_LIST = "NRLM_FETCH_APPLICATION_LIST";
    public static String NRLM_FETCH_APPLICATION_DETAILS = "NRLM_FETCH_APPLICATION_DETAILS";
    public static String NRLM_COMPLETED_APPLICATION_DETAILS = "NRLM_COMPLETED_APPLICATION_DETAILS";
    public static String NRLM_SIGNUP_DEFAULT_PASS = "NRLM_SIGNUP_DEFAULT_PASS";
    public static String MULTIPLE_BANK_TOTAL_BANK_SECECTION_COUNT = "MULTIPLE_BANK_TOTAL_BANK_SECECTION_COUNT";

    public static String AADHAR_DUBLICATION_IS_ON_OFF = "AADHAR_DUBLICATION_IS_ON_OFF";
    public static String TESTING_MODE_ON_OFF = "TESTING_MODE";
    public static String QA_AADHAR_TESTING_MODE_ON_OFF = "QA_TESTING_MODE";
    
    public static String BUREAU_SKIP_EDUCATION = "BUREAU_SKIP_EDUCATION";
    public static String BUREAU_SKIP_HOME_LOAN = "BUREAU_SKIP_HOME_LOAN";
    public static String BUREAU_SKIP_AGRI_LOAN = "BUREAU_SKIP_AGRI_LOAN";
    public static String BUREAU_SKIP_LIVELIHOOD_LOAN = "BUREAU_SKIP_LIVELIHOOD_LOAN";
    public static String BUREAU_SKIP_BUTTON_AGRI_KCC_LOAN = "BUREAU_SKIP_BUTTON_AGRI_KCC_LOAN";

    
    public static String BUREAU_SKIP_BUTTON_EDUCATION_LOAN = "BUREAU_SKIP_BUTTON_EDUCATION_LOAN";
    public static String BUREAU_SKIP_BUTTON_HOME_LOAN = "BUREAU_SKIP_BUTTON_HOME_LOAN";
    public static String BUREAU_SKIP_BUTTON_BUSINESS_LOAN = "BUREAU_SKIP_BUTTON_BUSINESS_LOAN"; 
    public static String BUREAU_SKIP_BUTTON_AGRI_LOAN = "BUREAU_SKIP_BUTTON_AGRI_LOAN";
    public static String BUREAU_SKIP_BUTTON_LIVELIHOOD_LOAN = "BUREAU_SKIP_BUTTON_LIVELIHOOD_LOAN";
    
    
    public static String THIRD_PARTY_NCGTC = "THIRD_PARTY_NCGTC";
    public static String NCGTC_TESTING_URL = "NCGTC_TESTING_URL";
    public static String GRIEVANCES_MAIL = "GRIEVANCES_MAIL";
    public static String GRIEVANCES_SEND_MAIL = "GRIEVANCES_SEND_MAIL";
    
    public static String CIBIL_BUREAU_MAIL = "CIBIL_BUREAU_MAIL";
    public static String CIBIL_BUREAU_MAIL_STATUS = "CIBIL_BUREAU_MAIL_STATUS";
    public static String CIBIL_BUREAU_PASSWORD_EXPIRED_MAIL = "CIBIL_BUREAU_PASSWORD_EXPIRED_MAIL";
    public static String CIBIL_UPDATE_PASSWORD_MAIL = "CIBIL_UPDATE_PASSWORD_MAIL";
    public static String NEW_PASSWORD_UPDATED = "NEW_PASSWORD_UPDATED";
    public static String NAME_MATCH_ALGO = "NAME_MATCH_ALGO";
    public static String NAME_MATCH_ALGO_PUBLIC_KEY = "NAME_MATCH_ALGO_PUBLIC_KEY";
    public static String NSDL_INSTITUTION_URL = "NSDL_INSTITUTION_URL";
    public static String NSDL_INSTITUTION_USER_NAME = "NSDL_INSTITUTION_USER_NAME";
    public static String NSDL_INSTITUTION_PASSWORD = "NSDL_INSTITUTION_PASSWORD";
    public static String IS_CHAT_BOOT_ACTIVE = "IS_CHAT_BOT_ACTIVE";

    public static final String KCC_LOS_URL = "KCC_LOS_URL";
    public static final String KCC_LOS_JANSAMARTH_PRIVATE_KEY = "KCC_LOS_JANSAMARTH_PRIVATE_KEY";
    public static final String KCC_LOS_EIS_PUBLIC_KEY = "KCC_LOS_EIS_PUBLIC_KEY";
    
    public static String IS_THIRD_PARTY_KCC = "IS_THIRD_PARTY_KCC";
    public static String IS_THIRD_PARTY_KCC_GEO_SPEC = "IS_THIRD_PARTY_KCC_GEO_SPEC";
    public static String IS_THIRD_PARTY_KCC_MANTLE_LAB = "IS_THIRD_PARTY_KCC_MANTLE_LAB";
    public static String KCC_SBI_GO_NO_GO_API_FLAG = "KCC_SBI_GO_NO_GO_API_FLAG";
    public static final String KCC_BOB_DE_DUPE_CALL = "KCC_BOB_DE_DUPE_CALL";
    public static final String KCC_PNB_DE_DUPE_CALL = "KCC_PNB_DE_DUPE_CALL";
    public static final String KCC_TIMEOUT_FOR_GEO_SPEC = "KCC_TIMEOUT_FOR_GEO_SPEC";


	public static String PRE_SCREEN_APP_UPDATE_STAGE_API = "PRE_SCREEN_APP_UPDATE_STAGE_API";
	public static final String EMAI_SEND_WITH_ZIP = "EMAI_SEND_WITH_ZIP";
    /**
     * Key For Only Load Testing
     */
//    public static String LOAD_TESTING_CIBIL_COMMERCIAL  = "LOAD_TESTING_CIBIL_COMMERCIAL";
//    public static String LOAD_TESTING_CIBIL_INDIVIDUAL  = "LOAD_TESTING_CIBIL_INDIVIDUAL";
//    public static String LOAD_TESTING_EXPERIAN_COMMERCIAL  = "LOAD_TESTING_EXPERIAN_COMMERCIAL";
//    public static String LOAD_TESTING_EXPERIAN_INDIVIDUAL  = "LOAD_TESTING_EXPERIAN_INDIVIDUAL";
//    public static String LOAD_TESTING_THIRD_PARTY_CGTMSE  = "LOAD_TESTING_THIRD_PARTY_CGTMSE";
//    public static String LOAD_TESTING_PMSVANIDHI_OTP  = "LOAD_TESTING_PMSVANIDHI_OTP";
//    public static String LOAD_TESTING_PMSVANIDHI_VERIFY_OTP  = "LOAD_TESTING_PMSVANIDHI_VERIFY_OTP";
//    public static String LOAD_TESTING_PENNYDROP_ACCOUNT_VERIFICATION  = "LOAD_TESTING_PENNYDROP_ACCOUNT_VERIFICATION";
//    public static String LOAD_TESTING_AADHAAR_OCR_FRONT  = "LOAD_TESTING_AADHAAR_OCR_FRONT";
//    public static String LOAD_TESTING_AADHAAR_OCR_BACK  = "LOAD_TESTING_AADHAAR_OCR_BACK";
//    public static String LOAD_TESTING_PAN_KARZA  = "LOAD_TESTING_PAN_KARZA";
//    public static String LOAD_TESTING_AADHAAR  = "LOAD_TESTING_AADHAAR";
//    public static String LOAD_TESTING_NSDL_PAN  = "LOAD_TESTING_NSDL_PAN";
//    public static String LOAD_TESTING_UDHYAM  = "LOAD_TESTING_UDHYAM";
//    public static String LOAD_TESTING_NRLM_LIST  = "LOAD_TESTING_NRLM_LIST";
//    public static String LOAD_TESTING_NRLM_DETAILS  = "LOAD_TESTING_NRLM_DETAILS";

    public String getValue(String code) {
        AnsConfigMaster ansConfigMaster = ansConfigMasterRepo.findByCodeAndIsActive(code, Boolean.TRUE);
        return ansConfigMaster != null ? ansConfigMaster.getValue() : null;
    }

    public List<AnsConfigMaster> getValues(String... code) {
        return ansConfigMasterRepo.findByMultipleCodeAndIsActive(Arrays.asList(code));
    }
    
    public Map<String,String> getValues(List<String> code) {
    	List<AnsConfigMaster> value =  ansConfigMasterRepo.findByMultipleCodeAndIsActive(code);
    	Map<String,String> values=null;
    	if(!OPLUtils.isObjectListNull(value)) {
    		values=new HashMap<>();
    		for(AnsConfigMaster val:value) {
    			values.put(val.getCode(), val.getValue());
    		}
		}
        return values;
    }

    public Integer getMultiBankDiffDayByOrgAndSchemeId(long orgId, int schemeId, boolean isOffline) {
        try {
            MultipleBankConfig multipleBankConfig = multipleBankConfigRepo.findByBankIdAndSchemeIdAndIsActive(orgId, schemeId, Boolean.TRUE);
            if(multipleBankConfig != null) {
                return isOffline ? multipleBankConfig.getOfflineDays() : multipleBankConfig.getOnlineDays();
            }
            log.warn("No data found while get multi bank diff days by org " + orgId + " and schemeid " + schemeId);
        } catch (Exception e) {
            log.error("Exception while get multi bank diff days by org " + orgId + " and schemeid " + schemeId, e);
        }
        return null;
    }


}
