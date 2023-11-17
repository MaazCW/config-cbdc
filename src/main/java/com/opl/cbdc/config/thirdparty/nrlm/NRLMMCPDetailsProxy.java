package com.opl.cbdc.config.thirdparty.nrlm;


import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class NRLMMCPDetailsProxy {

    public String purpose;

    @JsonProperty("proposed_loan_amount")
    public Double proposedLoanAmount;

    @JsonProperty("mis_member_id")
    public Long misMemberId;

    public Integer priority;
}
