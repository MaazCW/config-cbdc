package com.opl.cbdc.config.utils.namematch.models;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class NameMatchResponse {
    private String data;
    private String message;
    private String referenceId;
    private String serverRequestId;
    private Integer status;
    
    public NameMatchResponse(String data, String message, Integer status, String referenceId) {
    	this.data = data;
    	this.message = message;
    	this.status = status;
    	this.referenceId = referenceId;
    }
}
