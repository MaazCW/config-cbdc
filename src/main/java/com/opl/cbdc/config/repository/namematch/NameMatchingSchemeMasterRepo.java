package com.opl.cbdc.config.repository.namematch;


import com.opl.cbdc.config.domain.namematch.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

public interface NameMatchingSchemeMasterRepo extends JpaRepository<NameMatchingSchemeMaster, Long> {

    @Query(value="select percentage from NameMatchingSchemeMaster where schemeId = :schemeId")
    Double getPercentagBySchemeId(@Param("schemeId") Integer schemeId);

    NameMatchingSchemeMaster findBySchemeId(Integer schemeId);

    NameMatchingSchemeMaster findBySchemeIdAndOrgId(Integer schemeId, Long orgId);
    
    NameMatchingSchemeMaster findBySchemeIdAndOrgIdAndMatchInputMasterInputTypeId(Integer schemeId, Long orgId,Integer inputTypeId);

    NameMatchingSchemeMaster findBySchemeIdAndOrgIdAndMatchInputMasterInputTypeIdAndMatchInputMasterIsForKcc(Integer schemeId, Long orgId,Integer inputTypeId, Boolean isForKcc);

//    @Query(value="update NameMatchingSchemeMaster set percentage = :percentage , modifiedBy = :modifiedBy , modifiedDate = NOW() where schemeId = :schemeId")
//    void updatePercentagBySchemeId(@Param("schemeId") Integer schemeId,@Param("percentage") Double percentage,@Param("modifiedBy") Long modifiedBy);
}
