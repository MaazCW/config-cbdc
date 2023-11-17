package com.opl.cbdc.config.repository;

import com.opl.cbdc.config.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

public interface APIRestrictAuditRepo  extends JpaRepository<APIRestrictAudit, Long> {
    @Query(value = "SELECT COUNT(id) FROM `ans_config`.`api_restrict_audit` WHERE `restrict_id` = :restrictId AND `value` =:value AND TIMESTAMPDIFF(MINUTE, created_date, NOW()) < :minutes AND `is_active` = TRUE", nativeQuery = true)
    public Long getCountByRestrictIdAndValueAndMinutes(@Param("restrictId") Long restrictId, @Param("value") Long value, @Param("minutes") Integer minutes);
    
    @Query(value = "SELECT COUNT(id) FROM `ans_config`.`api_restrict_audit` WHERE `restrict_id` = :restrictId AND `value` =:value AND `is_active` = TRUE", nativeQuery = true)
    public Long getCountByRestrictIdAndValue(@Param("restrictId") Long restrictId, @Param("value") Long value);

    @Query(value = "SELECT COUNT(id) FROM `ans_config`.`api_restrict_audit` WHERE `restrict_id` = :restrictId AND `email` =:email AND TIMESTAMPDIFF(MINUTE, created_date, NOW()) < :minutes AND `is_active` = TRUE", nativeQuery = true)
    public Long getCountByRestrictIdAndEmailAndMinutes(@Param("restrictId") Long restrictId, @Param("email") String email, @Param("minutes") Integer minutes);

    @Query(value = "SELECT COUNT(id) FROM `ans_config`.`api_restrict_audit` WHERE `restrict_id` = :restrictId AND `email` =:email AND `is_active` = TRUE", nativeQuery = true)
    public Long getCountByRestrictIdAndEmail(@Param("restrictId") Long restrictId, @Param("email") String email);
}
