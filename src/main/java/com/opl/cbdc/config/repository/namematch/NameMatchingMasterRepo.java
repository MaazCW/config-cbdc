package com.opl.cbdc.config.repository.namematch;

import com.opl.cbdc.config.domain.namematch.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface NameMatchingMasterRepo extends JpaRepository<NameMatchingMaster, Long> {

    public NameMatchingMaster findByApplicationIdAndIsActiveAndBaseIdIsNull(Long applicationId, Boolean isActive);

    public NameMatchingMaster findByCoApplicantIdAndIsActiveAndBaseIdIsNull(Long coApplicantId, Boolean isActive);

    public NameMatchingMaster findByApplicationIdAndInputTypeIdAndBaseIdAndIsActive(Long applicationId, Integer inputTypeId,Integer baseId, Boolean isActive);

    @Query(value="select nmm from NameMatchingMaster nmm where nmm.applicationId = :applicationId AND nmm.inputTypeId = :inputTypeId AND (nmm.baseId IS NULL OR nmm.baseId = :baseId) AND nmm.isActive = :isActive AND nmm.coApplicantId IS NULL")
    public NameMatchingMaster findByApplicationIdAndInputTypeIdAndBaseIdOrBaseIdIsNullAndIsActive(@Param("applicationId") Long applicationId, @Param("inputTypeId") Integer inputTypeId, @Param("baseId") Integer baseId, @Param("isActive") Boolean isActive);

    @Query(value="select nmm from NameMatchingMaster nmm where nmm.applicationId = :applicationId AND coApplicantId = :coApplicantId AND nmm.inputTypeId = :inputTypeId AND (nmm.baseId IS NULL OR nmm.baseId = :baseId) AND nmm.isActive = :isActive AND nmm.coApplicantId IS NULL")
    public NameMatchingMaster findByApplicationIdAndCoApplicantIdAndInputTypeIdAndBaseIdOrBaseIdIsNullAndIsActive(@Param("applicationId") Long applicationId,@Param("coApplicantId")  Long coApplicantId, @Param("inputTypeId") Integer inputTypeId, @Param("baseId") Integer baseId, @Param("isActive") Boolean isActive);

    public NameMatchingMaster findByApplicationIdAndCoApplicantIdAndInputTypeIdAndBaseIdAndIsActive(Long applicationId, Long coApplicantId, Integer inputTypeId, Integer baseId, Boolean isActive);

    public List<NameMatchingMaster> findByApplicationIdAndIsActive(Long applicationId, Boolean isActive);

    public List<NameMatchingMaster> findByCoApplicantIdAndIsActive(Long coApplicantId, Boolean isActive);

    public NameMatchingMaster findByApplicationIdAndIsActiveAndBaseIdIsNullAndInputNameIsNotNull(Long applicationId, Boolean isActive);

    public NameMatchingMaster findByCoApplicantIdAndIsActiveAndBaseIdIsNullAndInputNameIsNotNull(Long coApplicantId, Boolean isActive);

    @Query(value="select nmm from NameMatchingMaster nmm where nmm.applicationId = :applicationId and nmm.coApplicantId = :coApplicantId and nmm.isActive = true and nmm.baseId IS NULL and nmm.inputName IS NOT NULL AND nmm.inputTypeId IN (:inputTypeIds)")
    public NameMatchingMaster getByCoApplicationId(@Param("applicationId") Long applicationId, @Param("coApplicantId") Long coApplicantId, @Param("inputTypeIds") List<Integer> inputTypeIds);

    @Query(value="select nmm from NameMatchingMaster nmm where nmm.applicationId = :applicationId and nmm.isActive = true and nmm.baseId IS NULL and nmm.inputName IS NOT NULL AND nmm.inputTypeId IN (:inputTypeIds) and nmm.coApplicantId IS NULL")
    public NameMatchingMaster getByApplicationId(@Param("applicationId") Long applicationId,@Param("inputTypeIds") List<Integer> inputTypeIds);

    public NameMatchingMaster findByApplicationIdAndInputTypeIdAndIsActive(Long applicationId, Integer inputTypeId, Boolean isActive);

    public NameMatchingMaster findByCoApplicantIdAndInputTypeIdAndIsActive(Long coApplicantId, Integer inputTypeId, Boolean isActive);
}
