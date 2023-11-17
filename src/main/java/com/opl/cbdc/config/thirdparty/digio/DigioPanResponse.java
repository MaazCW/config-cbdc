package com.opl.cbdc.config.thirdparty.digio;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(Include. NON_NULL)
public class DigioPanResponse {
	private String full_name;
	private String category;
	private String pan;
	private String status;
	private Boolean is_pan_dob_valid;
	private Boolean name_matched;
	private String error_message;
	private String details;
	private String code;
	private String message;
	private Boolean flag;
	

}
