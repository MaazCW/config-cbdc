
package com.opl.cbdc.config.thirdparty.roundrobin;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.io.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(Include.NON_NULL)
public class Api implements Serializable {

	private static final long serialVersionUID = -556136673787186691L;

	@JsonProperty("apiUrl")
	private String apiUrl;
	@JsonProperty("method")
	private String method;
	@JsonProperty("apiName")
	private String apiName;
	@JsonProperty("apiAction")
	private String apiAction;
	@JsonProperty("apiOrderNo")
	private Integer apiOrderNo;
	@JsonProperty("domainName")
	private String domainName;
	@JsonProperty("encryption")
	private String encryption;
	@JsonProperty("contentType")
	private String contentType;
	@JsonProperty("urlParameters")
	private String urlParameters;
}
