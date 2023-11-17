package com.opl.cbdc.config.thirdparty.digio;

import lombok.*;

import java.util.*;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DigioPanRequest {

	private String pan;
	private String name;
	private Date dob;
	private Long applicationId;
	private Long coAppId;
}
