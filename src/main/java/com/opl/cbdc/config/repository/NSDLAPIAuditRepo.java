package com.opl.cbdc.config.repository;

import com.opl.cbdc.config.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

public interface NSDLAPIAuditRepo extends JpaRepository<NSDLAPIAudit, Long> {

    @Query(value = "select * from ans_config.nsdl_api_audit where UPPER(pan) = UPPER(HEX(AES_ENCRYPT(:pan, 'C@p!ta@W0rld#AES'))) and reference_id IS NOT NULL and is_active = true ORDER BY id DESC limit 1", nativeQuery = true)
    public NSDLAPIAudit getByPanDetails(@Param("pan") String pan);

}
