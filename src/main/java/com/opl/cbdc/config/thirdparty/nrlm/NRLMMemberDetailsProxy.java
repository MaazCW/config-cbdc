package com.opl.cbdc.config.thirdparty.nrlm;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class NRLMMemberDetailsProxy {

    public String gender;

    @JsonProperty("kyc_document")
    public String kycDocument;

    public String disability;

    @JsonProperty("doc_image_data")
    public String docImageData;

    @JsonProperty("secc_no")
    public String seccNo;

    @JsonProperty("member_name")
    public String memberName;

    @JsonProperty("mis_shg_code")
    public String misShgCode;

    public String religion;

    @JsonProperty("pic_image_data")
    public String picImageData;

    @JsonProperty("adhar_valid")
    public String adharValid;

    public String dob;

    @JsonProperty("aadhar_seeded_sb_ac")
    public String aadharSeededSbAc;

    @JsonProperty("husband_father_name")
    public String husbandFatherName;

    public String designation;

    @JsonProperty("mis_member_id")
    public Long misMemberId;

    @JsonProperty("mobile_number")
    public String mobileNumber;

    @JsonProperty("social_category")
    public String socialCategory;
}
