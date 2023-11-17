package com.opl.cbdc.config.utils.namematch.enums;

public enum NameMatchPresetEnum {

	GENERAL(1l, "GENERAL" ,"g"),
	
	LENIENT(2l, "LENIENT", "l"),
	
	STRICT(3l, "STRICT","s");
	
	private final Long id;
	private final String key;
	private final String value;
	

	NameMatchPresetEnum(Long id,String key, String value) {
		this.id = id;
		this.key=key;
		this.value = value;
		
	}

	public Long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}
	public String getKey() {
		return key;
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
	
	public static NameMatchPresetEnum getById(Long v) {
		for (NameMatchPresetEnum c : NameMatchPresetEnum.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);

	}

	public static NameMatchPresetEnum[] getAll() {
		return NameMatchPresetEnum.values();

	}

	
}







	
	
	


