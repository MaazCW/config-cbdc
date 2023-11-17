package com.opl.cbdc.config.thirdparty.nsdl;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class NSDLPanDetailsProxy {

    private String lastName;
    private String firstName;
    private String nameOnCard;
    private String lastUpdateDate;
    private String middleName;
    private String panStatusDesc;
    private String pan;
    private String title;
    private String isAadhaarLinked;
}
