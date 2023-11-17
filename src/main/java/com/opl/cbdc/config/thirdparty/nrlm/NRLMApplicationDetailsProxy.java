package com.opl.cbdc.config.thirdparty.nrlm;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class NRLMApplicationDetailsProxy {

    @JsonProperty("resourcefeeservicesharge")
    public Double resourceFeeServiceSharge;

    @JsonProperty("lgd_village_code")
    public String lgdVillageCode;

    @JsonProperty("savingsmembers")
    public Double savingsMembers;

    @JsonProperty("mis_branch_code")
    public String misBranchCode;

    public Double penalties;

    @JsonProperty("loanoutstandmemberother")
    public Double loanOutStandMemberOther;

    @JsonProperty("monthly_saving_per_member")
    public String monthlySavingPerMember;

    @JsonProperty("intpaidbank")
    public Double intPaidBank;

    @JsonProperty("mis_village_code")
    public String misVillageCode;

    @JsonProperty("intsubvenrec")
    public Double intSubVenRec;

    @JsonProperty("state_name")
    public String stateName;

    @JsonProperty("is_group_activity")
    public String isGroupActivity;

    public List<NRLMMemberDetailsProxy> members;

    @JsonProperty("shg_name")
    public String shgName;

    @JsonProperty("intpaidvoclf")
    public Double intPaidVoclf;

    @JsonProperty("grantsawardsrec")
    public Double grantSawardsrec;

    public List<NRLMMCPDetailsProxy> mcp;

    public Double tada;

    @JsonProperty("loanoutstandvoclf")
    public Double loanUutstandVoclf;

    @JsonProperty("lgd_grampanchayat_code")
    public String lgdGrampanchayatCode;

    @JsonProperty("grantsrf")
    public Double grantSrf;

    @JsonProperty("mcp_no")
    public String mcpNo;

    @JsonProperty("lgd_block_code")
    public String lgdBlockCode;

    @JsonProperty("stationothexp")
    public Double stationOthexp;

    @JsonProperty("lgd_district_code")
    public String lgdDistrictCode;

    @JsonProperty("ifsc_code")
    public String ifscCode;

    @JsonProperty("branch_code")
    public String branchCode;

    @JsonProperty("savings_account_no")
    public String savingsAccountNo;

    @JsonProperty("loanoutstandmemberbank")
    public Double loanOutstandMemberBank;

    @JsonProperty("interestrecbank")
    public Double interestRecBank;

    @JsonProperty("otherexp")
    public Double otherExp;

    @JsonProperty("otherincome")
    public Double otherIncome;

    @JsonProperty("grampanchayat_name")
    public String grampanchayatName;

    @JsonProperty("depositwithbank")
    public Double depositWithBank;

    @JsonProperty("mcp_date")
    public String mcpDate;

    @JsonProperty("shg_type")
    public String shgType;

    @JsonProperty("total_mcp_amount_proposed")
    public Double totalMcpAmountProposed;

    @JsonProperty("application_type")
    public String applicationType;

    @JsonProperty("interestincome")
    public Double interestIncome;

    @JsonProperty("loanoutstandbank")
    public Double loanOutstandBank;

    @JsonProperty("othgrants")
    public Double othGrants;

    @JsonProperty("shg_meeting_frequency")
    public String shgMeetingFrequency;

    @JsonProperty("paymenttobookkeeper")
    public Double paymentToBookKeeper;

    @JsonProperty("submitted_to")
    public String submittedTo;

    @JsonProperty("intpaidoth")
    public Double intPaidOth;

    @JsonProperty("district_name")
    public String districtName;

    @JsonProperty("block_name")
    public String blockName;

    @JsonProperty("financial_statement_date")
    public String financialStatementDate;

    @JsonProperty("generation_date")
    public String generationDate;

    @JsonProperty("bank_branch_name")
    public String bankBranchName;

    @JsonProperty("bank_name")
    public String bankName;

    @JsonProperty("cashhand")
    public Double cashHand;

    @JsonProperty("shg_formation_date")
    public String shgFormationDate;

    @JsonProperty("loan_application_no")
    public String loanApplicationNo;

    @JsonProperty("grantsstartupcost")
    public Double grantsStartUpCost;

    @JsonProperty("lgd_state_code")
    public String lgdStateCode;

    @JsonProperty("honorcomcadre")
    public Double honorComcadre;

    @JsonProperty("savingsdepositwithvoclf")
    public Double savingsDepositWithVoclf;

    @JsonProperty("shg_promoted_by")
    public String shgPromotedBy;

    @JsonProperty("loanoutstandother")
    public Double loanOutstandOther;

    @JsonProperty("loanoutstandmembervoclf")
    public Double loanOutstandMemberVoclf;

    @JsonProperty("mis_shg_code")
    public String misShgCode;

    @JsonProperty("ifsc_in_files")
    public String ifscInFiles;

    @JsonProperty("bankcharges")
    public Double bankCharges;

    @JsonProperty("membershipfee")
    public Double membershipFee;

    @JsonProperty("loan_application_id")
    public Long loanApplicationId;

    @JsonProperty("village_name")
    public String villageName;

    @JsonProperty("insurancepremium")
    public Double insurancePremium;

    @JsonProperty("loginid")
    public String loginid;

}
