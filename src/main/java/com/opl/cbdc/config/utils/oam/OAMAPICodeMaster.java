package com.opl.cbdc.config.utils.oam;

public enum OAMAPICodeMaster {

	KCC_GET_FARMER_DATA_BY_FID(1l, "KCC_GET_FARMER_DATA_BY_FID"),
	KCC_GET_FARMER_DATA_BY_HASH_OF_AADHAAR(2l, "KCC_GET_FARMER_DATA_BY_HASH_OF_AADHAAR"),
	KCC_CHECK_BHOOMI_STATUS(3l, "KCC_CHECK_BHOOMI_STATUS"),
	KCC_GET_MANTLE_LABS_DATA(4l, "KCC_GET_MANTLE_LABS_DATA"),
	KCC_GET_GEO_SPEC_DATA(5l, "KCC_GET_GEO_SPEC_DATA"),
	KCC_PUSH_DATA_GEO_SPEC_DATA(6l, "KCC_PUSH_DATA_GEO_SPEC_DATA"),
	GET_CROP_SURVEY_DATA(7l, "GET_CROP_SURVEY_DATA"),
	GET_CROP_SURVEY_DATA_BY_YEAR(8l, "GET_CROP_SURVEY_DATA_BY_YEAR"),
	KCC_GET_MANTLE_LABS_DATA_WITH_SAT_IMG(9l, "KCC_GET_MANTLE_LABS_DATA_WITH_SAT_IMG"),
	KCC_NEW_REQUEST_SKYMET_KCC(10l, "KCC_NEW_REQUEST_SKYMET_KCC"),
	KCC_REPORT_PULL_API_SKYMET_KCC(11l, "KCC_REPORT_PULL_API_SKYMET_KCC"),
	SAVE_TRANSACTION_WITHOUT_CHARGE(23l, "SAVE_TRANSACTION_WITHOUT_CHARGE"),
	OAM_UPDATE_SATELLITE_DATA(24l, "OAM_UPDATE_SATELLITE_DATA"),
	KCC_GET_GEO_PDF(25l, "KCC_GET_GEO_PDF"),
	KCC_GET_GEO_SPEC_DATA_VERSION_2(27l, "KCC_GET_GEO_SPEC_DATA_VERSION_2");

	private Long id;
	private String value;

	private OAMAPICodeMaster(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public static OAMAPICodeMaster fromId(Long v) {
		for (OAMAPICodeMaster c : OAMAPICodeMaster.values()) {
			if (c.id.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v != null ? v.toString() : null);
	}

}
