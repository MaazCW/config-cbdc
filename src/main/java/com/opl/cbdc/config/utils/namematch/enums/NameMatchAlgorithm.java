package com.opl.cbdc.config.utils.namematch.enums;

public enum NameMatchAlgorithm {

    HAMMING(1, "hamming","Hamming"),
    MRA (2, "mra","MRA"),
    LEVENSHTEIN(3,"levenshtein","Levenshtein");

    private Integer id;
    private String value;
    private String displayName;

    private NameMatchAlgorithm(Integer id, String value,String displayName) {
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


    public static NameMatchAlgorithm fromId(Integer v) {
        for (NameMatchAlgorithm c : NameMatchAlgorithm.values()) {
            if (c.id.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v != null ? v.toString() : null);
    }
}
