package com.opl.cbdc.config.utils.oam;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.*;
import lombok.*;

import java.io.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(content = Include.NON_NULL)
public class GatewayResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	public Object data;
	public String message;
	public String serverRequestId;
	public Integer status;
	public String path;

}
