package com.opl.cbdc.config.thirdparty.authbridge;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthBridgeCredentialProxy {
	private String username;
	private String password;
}
