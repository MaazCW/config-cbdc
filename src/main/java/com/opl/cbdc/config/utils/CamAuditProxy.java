package com.opl.cbdc.config.utils;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CamAuditProxy {

	private Long applicationId;

	private Long proposalId;
	
	private Long schemeId;

	private String userMessage;

	private String sysMessage;

}
