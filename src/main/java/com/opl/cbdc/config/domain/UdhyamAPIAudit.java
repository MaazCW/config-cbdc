package com.opl.cbdc.config.domain;

import com.opl.cbdc.utils.common.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;
import java.util.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "udhyam_api_audit", catalog = "ans_config", schema = "ans_config")
public class UdhyamAPIAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "udhyam_no")
    @Convert(converter = EncryptionUtils.class)
    private String udhyamNo;

    @Column(name = "mobile_no")
    @Convert(converter = EncryptionUtils.class)
    private String mobileNo;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "message")
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDate;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date modifiedDate;

}
