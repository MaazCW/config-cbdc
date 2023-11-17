
package com.opl.cbdc.config.thirdparty.roundrobin;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(Include.NON_NULL)
public class ApiList implements Serializable{

	private static final long serialVersionUID = -9127262477139709596L;
	
	@JsonProperty("apilist")
	private List<Api> apilist = new ArrayList<>();

}
