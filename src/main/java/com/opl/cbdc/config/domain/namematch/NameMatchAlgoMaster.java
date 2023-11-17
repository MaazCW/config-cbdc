package com.opl.cbdc.config.domain.namematch;

import lombok.*;

import javax.persistence.*;
import java.io.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "name_match_algo_master", catalog = "name_match_api", schema = "name_match_api")
public class NameMatchAlgoMaster implements Serializable{
	
	 private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long id;

	    @Column(name = "value")
	    private String value;

	    @Column(name = "display_name")
	    private String displayName;
	    

}
