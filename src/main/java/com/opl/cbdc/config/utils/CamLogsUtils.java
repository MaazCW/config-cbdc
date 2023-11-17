package com.opl.cbdc.config.utils;

import com.opl.cbdc.config.domain.*;
import com.opl.cbdc.config.repository.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@Slf4j
public class CamLogsUtils {

    @Autowired
    private CamAuditRepo camAuditRepo;

    public void save(CamAuditProxy camAuditProxy) {
        try{
        	CamAudit camAudit = new CamAudit();
            BeanUtils.copyProperties(camAuditProxy, camAudit);
            camAudit.setIsActive(Boolean.TRUE);
            camAudit.setCreatedDate(new Date());
            camAuditRepo.save(camAudit);
        }catch(Exception e){
            log.error("Exception while saving audit logs related downloading CAM or ApplicationForm in cam audit table ", e);
        }
    }
}
