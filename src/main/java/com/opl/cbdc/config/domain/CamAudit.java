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
@Table(name = "cam_report_audit_log", catalog = "ans_config", schema = "ans_config") 
public class CamAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "application_id")
	private Long applicationId;

	@Column(name = "proposal_id")
	private Long proposalId;
	
	@Column(name = "scheme_id")
	private Long schemeId;

	@Column(name = "user_message")
	private String userMessage;

	@Column(name = "sys_message")
	private String sysMessage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "is_active")
	private Boolean isActive;
	
}