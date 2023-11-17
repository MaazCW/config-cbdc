package com.opl.cbdc.config.domain;

import lombok.*;

import javax.persistence.*;
import java.io.*;
import java.util.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oam_api_audit", catalog = "ans_config", schema = "ans_config")
public class OAMAPIAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "api_name")
	private String apiName;
	
	@ManyToOne
    @JoinColumn(name = "oam_api_master_id")
    private OAMAPIMaster oamapiMaster;

	@Column(name = "reference_id")
	private String referenceId;

	@Column(name = "oam_reference_id")
	private String oamReferenceId;

	@Column(name = "status")
	private Integer status;

	@Column(name = "oam_status")
	private Integer oamStatus;

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

	public OAMAPIAudit(String apiName, String referenceId, Date createdDate) {
		super();
		this.apiName = apiName;
		this.referenceId = referenceId;
		this.createdDate = createdDate;
		this.isActive = true;
	}

}
