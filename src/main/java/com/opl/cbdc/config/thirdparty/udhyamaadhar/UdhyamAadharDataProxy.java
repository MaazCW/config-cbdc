package com.opl.cbdc.config.thirdparty.udhyamaadhar;

import lombok.*;

import java.util.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UdhyamAadharDataProxy {

    public UdhyamAadharBasicDetailsProxy basicDetails;
    public List<UdhyamAadharActivityDetailsProxy> activities;
    public List<UdhyamAadharPlantDetailsProxy> plants;
}
