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
@Table(name = "name_matching_scheme_master", catalog = "name_match_api", schema = "name_match_api")
public class NameMatchingSchemeMaster implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "scheme_id")
    private Integer schemeId;

    @Column(name = "percentage")
    private Double percentage;

    @ManyToOne
    @JoinColumn(name = "algorithm_id")
    private NameMatchAlgoMaster algorithmId;

    @Column(name = "org_id")
    private Long orgId;
    
    @Column(name = "karza_percentage")
    private Double karzaPercentage;
    
    @ManyToOne
    @JoinColumn(name= "preset")
    private NameMatchPresetMaster  preset;   
    
    @ManyToOne
    @JoinColumn(name= "failure_api_id")
    private NameMatchApiMaster failureApiId;   
    
    @ManyToOne
    @JoinColumn(name= "api_id")
    private NameMatchApiMaster apiId;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "modified_by")
    private Long modifiedBy;
    
    @ManyToOne
    @JoinColumn(name = "input_type_id")
    private NameMatchInputTypeMaster matchInputMaster;
 
}
