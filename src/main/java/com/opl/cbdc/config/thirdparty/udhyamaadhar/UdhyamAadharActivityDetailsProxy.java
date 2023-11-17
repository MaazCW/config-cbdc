package com.opl.cbdc.config.thirdparty.udhyamaadhar;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UdhyamAadharActivityDetailsProxy {
    public int applicationId;
    public String activity;
    public String twoDigitActivity;
    public String fourDigitActivity;
    public String fiveDigitActivity;
}
