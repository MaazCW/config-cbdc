package com.opl.cbdc.config.repository;

import com.opl.cbdc.config.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface AnsConfigMasterRepo extends JpaRepository<AnsConfigMaster, Long> {

    public AnsConfigMaster findByCodeAndIsActive(String code, Boolean isActive);

    @Query(value = "from AnsConfigMaster where code IN (:codeList) and isActive = true")
    public List<AnsConfigMaster> findByMultipleCodeAndIsActive(@Param("codeList") List<String> codeList);

}
