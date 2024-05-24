package com.mobilepos.repository;

import com.mobilepos.domain.Regency;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Regency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegencyRepository extends JpaRepository<Regency, Integer> {}
