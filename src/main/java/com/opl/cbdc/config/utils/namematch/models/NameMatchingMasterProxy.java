package com.opl.cbdc.config.utils.namematch.models;

import lombok.*;

import java.io.*;
import java.util.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class NameMatchingMasterProxy implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long applicationId;
    
    private Long coApplicantId;

    private Date createdDate;

    private String message;

    private Boolean isActive;

    private Boolean isMatch;

    private String inputName;

    private Integer inputTypeId;
    
    private String inputTypeName;

    private Integer baseId;

    private Double percentage;
    
    private String displayName;
}
