package com.opl.cbdc.config.domain.namematch;

import lombok.*;

import javax.persistence.*;
import java.io.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "name_match_preset_master", catalog = "name_match_api", schema = "name_match_api")
public class NameMatchPresetMaster implements Serializable {
    private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "key")
	private String key;
	
	@Column(name = "value")
	private String value;	

}
