package com.mobilepos.service;

import com.mobilepos.service.dto.RegencyDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mobilepos.domain.Regency}.
 */
public interface RegencyService {
    /**
     * Save a regency.
     *
     * @param regencyDTO the entity to save.
     * @return the persisted entity.
     */
    RegencyDTO save(RegencyDTO regencyDTO);

    /**
     * Updates a regency.
     *
     * @param regencyDTO the entity to update.
     * @return the persisted entity.
     */
    RegencyDTO update(RegencyDTO regencyDTO);

    /**
     * Partially updates a regency.
     *
     * @param regencyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RegencyDTO> partialUpdate(RegencyDTO regencyDTO);

    /**
     * Get all the regencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegencyDTO> findAll(Pageable pageable);

    /**
     * Get the "id" regency.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegencyDTO> findOne(Integer id);

    /**
     * Delete the "id" regency.
     *
     * @param id the id of the entity.
     */
    void delete(Integer id);
}
