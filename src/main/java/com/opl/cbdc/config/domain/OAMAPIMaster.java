package com.opl.cbdc.config.domain;

import lombok.*;

import javax.persistence.*;
import java.io.*;
import java.util.*;

/**
 * @author sandip.bhetariya
 *
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oam_api_master", catalog = "ans_config", schema = "ans_config")
public class OAMAPIMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "api_code")
	private String apiCode;

	@Column(name = "description")
	private String description;

	@Column(name = "domain_url")
	private String domainUrl;
	
	@Column(name = "api_url")
	private String apiUrl;
	
	@Column(name = "public_key")
	private String publicKey;
	
	@Column(name = "private_key")
	private String privateKey;

	@Column(name = "client_id")
	private String clientId;

	@Column(name = "secret_id")
	private String secretId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "password")
	private String password;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "is_active")
	private Boolean isActive;

}
