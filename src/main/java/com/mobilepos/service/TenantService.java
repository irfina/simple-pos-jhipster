package com.mobilepos.service;

import com.mobilepos.service.dto.TenantDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.mobilepos.domain.Tenant}.
 */
public interface TenantService {
    /**
     * Save a tenant.
     *
     * @param tenantDTO the entity to save.
     * @return the persisted entity.
     */
    TenantDTO save(TenantDTO tenantDTO);

    /**
     * Updates a tenant.
     *
     * @param tenantDTO the entity to update.
     * @return the persisted entity.
     */
    TenantDTO update(TenantDTO tenantDTO);

    /**
     * Partially updates a tenant.
     *
     * @param tenantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TenantDTO> partialUpdate(TenantDTO tenantDTO);

    /**
     * Get all the tenants.
     *
     * @return the list of entities.
     */
    List<TenantDTO> findAll();

    /**
     * Get the "id" tenant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TenantDTO> findOne(UUID id);

    /**
     * Delete the "id" tenant.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
