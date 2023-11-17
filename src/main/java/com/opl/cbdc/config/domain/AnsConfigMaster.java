package com.opl.cbdc.config.domain;

import lombok.*;

import javax.persistence.*;
import java.io.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ans_config_master", catalog = "ans_config", schema = "ans_config")
public class AnsConfigMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "value")
	private String value;

	@Column(name = "is_active")
	private Boolean isActive;
	
}
