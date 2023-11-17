package com.opl.cbdc.config.domain.namematch;

import lombok.*;

import javax.persistence.*;
import java.io.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "name_match_input_type_master", catalog = "name_match_api", schema = "name_match_api")
public class NameMatchInputTypeMaster implements Serializable{
	
	  private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long id;

	    @Column(name = "input_type_id")
	    private Integer inputTypeId;

	    @Column(name = "name")
	    private String name;

	    @Column(name = "display_name")
	    private String displayName;

	    @Column(name = "is_for_kcc")
	    private Boolean isForKcc;
	    
	    @Column(name = "disable")
	    private Boolean disable;


}
