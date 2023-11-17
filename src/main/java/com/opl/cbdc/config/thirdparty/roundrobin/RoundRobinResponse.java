package com.opl.cbdc.config.thirdparty.roundrobin;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.io.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(Include.NON_NULL)
public class RoundRobinResponse implements Serializable {
	private static final long serialVersionUID = -7179836985476636496L;

	@JsonProperty("MODULE_NAME")
	private String moduleName;

	@JsonProperty("VENDER_API_LIST")
	private String venderAPIListJSON;

	@JsonProperty("LAST_CALL_TIME")
	private Long lastCallTime;

	@JsonProperty("VENDER_NAME")
	private String venderName;
	
	private ApiList venderAPIList;
}
