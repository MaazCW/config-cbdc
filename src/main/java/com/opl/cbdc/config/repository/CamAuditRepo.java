package com.opl.cbdc.config.repository;

import com.opl.cbdc.config.domain.*;
import org.springframework.data.jpa.repository.*;

public interface CamAuditRepo extends JpaRepository<CamAudit, Long> {
}
