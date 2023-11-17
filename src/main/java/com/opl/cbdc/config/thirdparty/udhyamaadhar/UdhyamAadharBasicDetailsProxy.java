package com.opl.cbdc.config.thirdparty.udhyamaadhar;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UdhyamAadharBasicDetailsProxy {

    public int applicationId;
    public String udyogAadharNo;
    public String ownerName;
    public String enterpriseName;
    public String organisationType;
    public String socialCategory;
    public String gender;
    public String ph;
    public String address;
    public String stateName;
    public String lgStCode;
    public String districtName;
    public String lgDtCode;
    public String pinCode;
    public String majorActivity;
    public String enterpriseType;
    public String incorporationDate;
    public String whetherProdCommenced;
    public String commmenceDate;
    public String totalEmp;
    public String appliedDate;
    public Object error;
    public Object errorCode;

}
