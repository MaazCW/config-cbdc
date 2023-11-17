package com.opl.cbdc.config.utils.namematch.models;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class NameMatchRequest {

    private Long applicationId;
    private Long coApplicantId;
    private Integer schemeId;
    private String inputName;
    private String message;
    private Integer inputTypeId;
    private Integer baseId;
    private Double percentage;
    private Long orgId;

    public NameMatchRequest(Long applicationId, Integer schemeId, String inputName, Integer inputTypeId, Integer baseId) {
        this.applicationId = applicationId;
        this.schemeId = schemeId;
        this.inputName = inputName;
        this.inputTypeId = inputTypeId;
        this.baseId = baseId;
    }

    public NameMatchRequest(Long applicationId, Integer schemeId, Integer inputTypeId) {
        this.applicationId = applicationId;
        this.schemeId = schemeId;
        this.inputTypeId = inputTypeId;
    }

    public NameMatchRequest(Long applicationId, Integer schemeId, Integer inputTypeId,String message) {
        this.applicationId = applicationId;
        this.schemeId = schemeId;
        this.inputTypeId = inputTypeId;
        this.message = message;
    }

    public NameMatchRequest(Long applicationId, Long coApplicantId, Integer schemeId, Integer inputTypeId,String message) {
        this.applicationId = applicationId;
        this.coApplicantId = coApplicantId;
        this.schemeId = schemeId;
        this.inputTypeId = inputTypeId;
        this.message = message;
    }

    public NameMatchRequest(Long applicationId, Integer schemeId, String inputName, Integer inputTypeId) {
        this.applicationId = applicationId;
        this.schemeId = schemeId;
        this.inputName = inputName;
        this.inputTypeId = inputTypeId;
    }

    public NameMatchRequest(Long applicationId, Long coApplicantId, Integer schemeId, String inputName, Integer inputTypeId) {
        this.applicationId = applicationId;
        this.coApplicantId = coApplicantId;
        this.schemeId = schemeId;
        this.inputName = inputName;
        this.inputTypeId = inputTypeId;
    }

    public NameMatchRequest(Long applicationId,Long coApplicantId, Integer schemeId, String inputName, Integer inputTypeId, Integer baseId) {
        this.applicationId = applicationId;
        this.coApplicantId = coApplicantId;
        this.schemeId = schemeId;
        this.inputName = inputName;
        this.inputTypeId = inputTypeId;
        this.baseId = baseId;
    }

    public NameMatchRequest(Integer schemeId, String inputName, Integer inputTypeId, Integer baseId,Long coApplicantId) {
        this.schemeId = schemeId;
        this.inputName = inputName;
        this.inputTypeId = inputTypeId;
        this.baseId = baseId;
        this.coApplicantId = coApplicantId;
    }
}
