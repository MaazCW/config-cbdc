package com.opl.cbdc.config.repository;

public interface NativeRepo {
	public String checkValidation(String panNumber,Long userId,Long applicationId,Long coAppId);
}
