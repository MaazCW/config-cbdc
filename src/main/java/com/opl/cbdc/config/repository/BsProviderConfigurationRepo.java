package com.opl.cbdc.config.repository;

import com.opl.cbdc.config.domain.*;
import org.springframework.data.jpa.repository.*;

public interface BsProviderConfigurationRepo extends JpaRepository<BsProviderConfiguration, Long> {
    BsProviderConfiguration findByCampIdAndIsActiveIsTrue(Long orgId);

    BsProviderConfiguration findByCampIdAndSchemeIdAndIsActiveIsTrue(Long orgId, Integer schemeId);
}
