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
@Table(name = "digio_api_audit", catalog = "ans_config", schema = "ans_config")
public class DIGIOAPIAudit implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -3799789607634282276L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pan")
    @Convert(converter = EncryptionUtils.class)
    private String pan;

    @Column(name = "request")
    @Convert(converter = EncryptionUtils.class)
    private String request;
    
    @Column(name = "response")
    @Convert(converter = EncryptionUtils.class)
    private String response;
    
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

    @Column(name = "application_id")
    private Long applicationId;
    
    @Column(name = "co_app_id")
    private Long coAppId;
}
