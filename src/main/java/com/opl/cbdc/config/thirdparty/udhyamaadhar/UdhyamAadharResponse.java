package com.opl.cbdc.config.thirdparty.udhyamaadhar;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UdhyamAadharResponse {

	private Integer status;
	private String message;
	private Boolean flag;
	private UdhyamAadharDataProxy data;
	private String referenceId;

	public UdhyamAadharResponse(Integer status, String message, Boolean flag) {
		super();
		this.status = status;
		this.message = message;
		this.flag = flag;
	}

}
