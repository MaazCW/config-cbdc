package com.opl.cbdc.config.thirdparty.nrlm;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NRLMLoginRes {

    private Boolean flag;

    private String login;

    private Integer status;

    public NRLMLoginRes(Boolean flag) {
        this.flag = flag;
    }

    public NRLMLoginRes(Boolean flag, Integer status) {
        this.flag = flag;
        this.status = status;
    }

}
