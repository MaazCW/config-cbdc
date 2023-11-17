package com.opl.cbdc.config.utils.namematch.enums;

public enum NameMatchApiEnum {
	KARZA(1l, "KARZA"),
	
	INHOUSE(2l, "INHOUSE"),
	
	BOTH(3l, "Both");
	
	private final Long id;
	private final String value;
	

	NameMatchApiEnum(Long id, String value) {
		this.id = id;
		this.value = value;
		
	}

	public Long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}



//	public static ApiEnum getById(Long id) {
//		switch (id.intValue()) {
//		case 1:
//			return GET_FARMER_DATA_BY_FID;
//		case 2:
//			return GET_FARMER_DATA_BY_HASH_OF_AADHAAR;
//		case 3:
//			return CHECK_BHOOMI_STATUS;
//		default:
//			return null;
//		}
//	}
	
	public static NameMatchApiEnum getById(Long v) {
		for (NameMatchApiEnum c : NameMatchApiEnum.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);

	}

	public static NameMatchApiEnum[] getAll() {
		return NameMatchApiEnum.values();

	}

}
