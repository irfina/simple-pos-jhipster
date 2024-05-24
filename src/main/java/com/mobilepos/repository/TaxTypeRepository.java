package com.mobilepos.repository;

import com.mobilepos.domain.TaxType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TaxType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxTypeRepository extends JpaRepository<TaxType, Integer> {}
