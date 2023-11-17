package com.opl.cbdc.config.utils.namematch.enums;

public enum NameMatchBaseId {

    PAN(1, "PAN","Name mentioned in PAN "),
    ENTERED_BY_APPLICANT (2, "Entered by applicant","Name entered by applicant"),
    AADHAR(3,"Aadhar","Name mentioned in Aadhar"),
    CBDT(4,"CBDT","Name fetched from CBDT"),
    BANK_STATEMENT(5,"Bank Statement","Name from Bank Statements"),
    BUREAU(6,"Bureau","Name fetched from Bureau"),
    UDYAM(7,"Udyam","Name mentioned in Udyam"),
    GST_ENTERED_BY_BORROWER_ON_SALES_DETAILS_PAGE(8,"GST/Entered by borrower on sales details page","Name fetched from GST / Entered by borrower on Sales detail page"),
    STP(9,"STP","STP");

    private Integer id;
    private String value;
    private String displayName;    

    private NameMatchBaseId(Integer id, String value,String displayName) {
        this.id = id;
        this.value = value;
        this.displayName = displayName;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    

    public static NameMatchBaseId fromId(Integer v) {
        for (NameMatchBaseId c : NameMatchBaseId.values()) {
            if (c.id.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v != null ? v.toString() : null);
    }
}
