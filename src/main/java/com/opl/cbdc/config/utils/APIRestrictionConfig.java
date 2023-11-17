package com.opl.cbdc.config.utils;

import com.opl.cbdc.utils.common.*;
import com.opl.cbdc.config.domain.*;
import com.opl.cbdc.config.repository.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@Slf4j
public class APIRestrictionConfig {

    // private static final Logger logger = LoggerFactory.getLogger(APIRestrictionConfig.class);

    public static final Long INIT_ATTEMPT = 1l;
    public static  final String SUCCESS ="SUCCESS";
    public static  final String END_MESSEGE="number of attempt only.";
    public static  final String GENERAL_MESSEGE="You have reaches maximum number of attempt";

    @Autowired
    private APIRestrictConfigRepo apiRestrictConfigRepo;
    @Autowired
    private APIRestrictAuditRepo apiRestrictAuditRepo;


    public String checkAPIRestriction(String code,Long value) {
        APIRestrictAudit apiRestrictAudit = new APIRestrictAudit();
        apiRestrictAudit.setValue(value);
        apiRestrictAudit.setCreatedDate(new Date());
        apiRestrictAudit.setIsActive(Boolean.FALSE);
        try {
            APIRestrictConfig apiRestrictConfig = apiRestrictConfigRepo.findByApiCode(code);
            if (OPLUtils.isObjectNullOrEmpty(apiRestrictConfig)) {
                apiRestrictAudit.setMessages("Wrong Code Found " + code);
                return null;
            }
            apiRestrictAudit.setRestrictId(apiRestrictConfig);
            Long count;
            if(!OPLUtils.isObjectNullOrEmpty(apiRestrictConfig.getMinutes())) {
                count = apiRestrictAuditRepo.getCountByRestrictIdAndValueAndMinutes(apiRestrictConfig.getId(), value, apiRestrictConfig.getMinutes());
            } else {
                count = apiRestrictAuditRepo.getCountByRestrictIdAndValue(apiRestrictConfig.getId(), value);
            }
           log.info("count"+count);

            if(count >= apiRestrictConfig.getMaxAttempt()) {

                apiRestrictAudit.setMessages(GENERAL_MESSEGE);
                return apiRestrictConfig.getMessages();
            } else {
                apiRestrictAudit.setMessages(SUCCESS);
                apiRestrictAudit.setIsActive(Boolean.TRUE);
            }
        } catch (Exception e) {
            log.error("GETTING EXCEPTION WHILE CHECK API RESTRICTION.", e);
            apiRestrictAudit.setMessages(e.getMessage());
        } finally {
            apiRestrictAuditRepo.save(apiRestrictAudit);
        }
        return apiRestrictAudit.getMessages();
    }

    public String checkAPIRestriction(String code,String email) {
        APIRestrictAudit apiRestrictAudit = new APIRestrictAudit();
        apiRestrictAudit.setEmail(email);
        apiRestrictAudit.setCreatedDate(new Date());
        apiRestrictAudit.setIsActive(Boolean.FALSE);
        try {
            APIRestrictConfig apiRestrictConfig = apiRestrictConfigRepo.findByApiCode(code);
            if (OPLUtils.isObjectNullOrEmpty(apiRestrictConfig)) {
                apiRestrictAudit.setMessages("Wrong Code Found " + code);
                return null;
            }
            apiRestrictAudit.setRestrictId(apiRestrictConfig);
//            Long count = apiRestrictAuditRepo.getCountByRestrictIdAndEmailAndMinutes(apiRestrictConfig.getId(), email, apiRestrictConfig.getMinutes());
            Long count;
            if(!OPLUtils.isObjectNullOrEmpty(apiRestrictConfig.getMinutes())) {
                count = apiRestrictAuditRepo.getCountByRestrictIdAndEmailAndMinutes(apiRestrictConfig.getId(), email, apiRestrictConfig.getMinutes());
            } else {
                count = apiRestrictAuditRepo.getCountByRestrictIdAndEmail(apiRestrictConfig.getId(), email);
            }
           log.info("count"+count);

            if(count >= apiRestrictConfig.getMaxAttempt()) {

                apiRestrictAudit.setMessages(GENERAL_MESSEGE);
                return apiRestrictConfig.getMessages();
            } else {
                apiRestrictAudit.setMessages(SUCCESS);
                apiRestrictAudit.setIsActive(Boolean.TRUE);
            }
        } catch (Exception e) {
            log.error("GETTING EXCEPTION WHILE CHECK API RESTRICTION.", e);
            apiRestrictAudit.setMessages(e.getMessage());
        } finally {
            apiRestrictAuditRepo.save(apiRestrictAudit);
        }
        return apiRestrictAudit.getMessages();
    }

}
