package com.opl.cbdc.config.utils.namematch.models;

import lombok.*;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString

public class NameMatchRequestProxy {

	/*
	 * common
	 */
	private String uuid;

	private String name1;
	private String name2;
	private String type;
	private String preset;
	private Boolean allowPartialMatch;
	private Boolean suppressReorderPenalty;
	private String algoName;
	private Long apiId;
	private Double karzaPercentage;
	

}