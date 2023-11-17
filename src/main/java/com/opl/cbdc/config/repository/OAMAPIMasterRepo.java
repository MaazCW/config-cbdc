package com.opl.cbdc.config.repository;

import com.opl.cbdc.config.domain.*;
import org.springframework.data.jpa.repository.*;

/**
 * @author sandip.bhetariya
 *
 */
public interface OAMAPIMasterRepo extends JpaRepository<OAMAPIMaster, Long> {

	public OAMAPIMaster findByApiCodeAndIsActive(final String apiCode, final boolean isActive);
}
