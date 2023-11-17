package com.opl.cbdc.config.repository;

import com.opl.cbdc.config.domain.*;
import org.springframework.data.jpa.repository.*;

public interface MultipleBankConfigRepo extends JpaRepository<MultipleBankConfig, Long> {

    public MultipleBankConfig findByBankIdAndSchemeIdAndIsActive(final long bankId,final int schemeId,final boolean isActive);

}
