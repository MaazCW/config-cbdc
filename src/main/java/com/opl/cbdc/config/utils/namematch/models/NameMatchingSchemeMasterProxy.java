package com.opl.cbdc.config.utils.namematch.models;

import com.opl.cbdc.config.domain.namematch.*;
import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameMatchingSchemeMasterProxy {

    private Long id;
    
    private Long orgId;

    private Integer schemeId;

    private String schemeName;

    private Double percentage;

    private NameMatchAlgoMaster algorithmId;
    
    private Double karzaPercentage;

    private Date modifiedDate;

    private Long modifiedBy;
    
    private NameMatchPresetMaster  preset;
    
    private NameMatchInputTypeMaster matchInputMaster;
    
    private NameMatchApiMaster failureApiId; 
    
    private NameMatchApiMaster apiId;
}
