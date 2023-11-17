package com.opl.cbdc.config.domain.namematch;

import lombok.*;

import javax.persistence.*;
import java.io.*;
import java.util.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "name_matching_master", catalog = "name_match_api", schema = "name_match_api")
public class NameMatchingMaster implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "co_applicant_id")
    private Long coApplicantId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "message")
    private String message;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_match")
    private Boolean isMatch;

    @Column(name = "input_name")
    private String inputName;

    @Column(name = "input_type_id")
    private Integer inputTypeId;

    @Column(name = "base_id")
    private Integer baseId;

    @Column(name = "percentage")
    private Double percentage;
    
    @Column(name = "refId")
    private String refId;

    @Column(name = "api_type")
    private String apiType;
    
    @Column(name = "bank_set_percentage")
    private Double bankSetPercentage;
}
