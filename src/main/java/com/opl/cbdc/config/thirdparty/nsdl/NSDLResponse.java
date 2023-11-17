package com.opl.cbdc.config.thirdparty.nsdl;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class NSDLResponse {

    private Integer status;
    private String message;
    private Boolean flag;
    private NSDLPanDetailsProxy data;
    private String referenceId;

}
