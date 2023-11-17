package com.opl.cbdc.config.repository.impl;

import com.opl.cbdc.utils.common.*;
import com.opl.cbdc.config.repository.*;
import org.springframework.stereotype.*;

import javax.persistence.*;

@Repository
public class NativeRepoImpl implements NativeRepo {

	@PersistenceContext
	private EntityManager entityManager;

	public String checkValidation(String panNumber, Long userId, Long applicationId, Long coAppId) {
		StoredProcedureQuery storedProcedureQuery = entityManager
				.createStoredProcedureQuery("ans_config.spCheckValidation");
		storedProcedureQuery.registerStoredProcedureParameter("panNumber", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("userId", Long.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("applicationId", Long.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("coAppId", Long.class, ParameterMode.IN);

		storedProcedureQuery.setParameter("panNumber", panNumber);
		storedProcedureQuery.setParameter("userId", userId);
		storedProcedureQuery.setParameter("applicationId", applicationId);

		if (!OPLUtils.isObjectNullOrEmpty(coAppId))
			storedProcedureQuery.setParameter("coAppId", coAppId);
		else
			storedProcedureQuery.setParameter("coAppId", -1l);
		
		storedProcedureQuery.execute();
		return (String) storedProcedureQuery.getSingleResult();
	}

}
