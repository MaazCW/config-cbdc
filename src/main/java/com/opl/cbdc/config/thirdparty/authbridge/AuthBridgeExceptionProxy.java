package com.opl.cbdc.config.thirdparty.authbridge;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthBridgeExceptionProxy {
//	{"status":1011,"msg":"Invalid request. Please check Token and\/or Data."}
	private Integer status;
	private String msg;
}
