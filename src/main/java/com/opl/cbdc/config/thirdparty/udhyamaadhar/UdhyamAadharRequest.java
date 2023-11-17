package com.opl.cbdc.config.thirdparty.udhyamaadhar;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UdhyamAadharRequest {

    private String udhyamNo;
    private String mobileNo;

}
