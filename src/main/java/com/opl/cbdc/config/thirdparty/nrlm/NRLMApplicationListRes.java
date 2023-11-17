package com.opl.cbdc.config.thirdparty.nrlm;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NRLMApplicationListRes {

    @JsonProperty("Pending Loan Applications")
    private List<NRLMApplicationListDetailsProxy> applicationList;

}
