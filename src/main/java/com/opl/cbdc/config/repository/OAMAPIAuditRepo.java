package com.opl.cbdc.config.repository;

import com.opl.cbdc.config.domain.*;
import org.springframework.data.jpa.repository.*;

public interface OAMAPIAuditRepo extends JpaRepository<OAMAPIAudit, Long> {

}
