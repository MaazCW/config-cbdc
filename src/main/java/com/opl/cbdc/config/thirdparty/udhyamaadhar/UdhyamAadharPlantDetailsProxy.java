package com.opl.cbdc.config.thirdparty.udhyamaadhar;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UdhyamAadharPlantDetailsProxy {

    public int applicationId;
    public String unitName;
    public String uamNo;
    public String address;
    public String pin;
    public String stateName;
    public String districtName;
    public String lgDtCode;
}
