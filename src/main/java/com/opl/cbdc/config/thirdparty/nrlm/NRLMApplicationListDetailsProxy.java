package com.opl.cbdc.config.thirdparty.nrlm;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class NRLMApplicationListDetailsProxy {

    @JsonProperty("shg_name")
    private String shgName;

    @JsonProperty("loan_application_id")
    private Long loanApplicationId;

    @JsonProperty("mis_shg_code")
    private String misShgCode;

    @JsonProperty("loan_application_no")
    private String loanApplicationNo;

}