package com.opl.cbdc.config.repository;

import com.opl.cbdc.config.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

public interface UdhyamAPIAuditRepo extends JpaRepository<UdhyamAPIAudit, Long> {

    @Query(value = "select * from ans_config.udhyam_api_audit where UPPER(udhyam_no) = UPPER(HEX(AES_ENCRYPT(:udhyamNo, 'C@p!ta@W0rld#AES'))) and UPPER(mobile_no) = UPPER(HEX(AES_ENCRYPT(:mobileNo, 'C@p!ta@W0rld#AES'))) and reference_id IS NOT NULL and is_active = true ORDER BY id DESC limit 1", nativeQuery = true)
    public UdhyamAPIAudit getByUdhyamNoAndMobileNo(@Param("udhyamNo") String udhyamNo,@Param("mobileNo") String mobileNo);

}
