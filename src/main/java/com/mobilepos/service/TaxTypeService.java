package com.mobilepos.service;

import com.mobilepos.service.dto.TaxTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mobilepos.domain.TaxType}.
 */
public interface TaxTypeService {
    /**
     * Save a taxType.
     *
     * @param taxTypeDTO the entity to save.
     * @return the persisted entity.
     */
    TaxTypeDTO save(TaxTypeDTO taxTypeDTO);

    /**
     * Updates a taxType.
     *
     * @param taxTypeDTO the entity to update.
     * @return the persisted entity.
     */
    TaxTypeDTO update(TaxTypeDTO taxTypeDTO);

    /**
     * Partially updates a taxType.
     *
     * @param taxTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaxTypeDTO> partialUpdate(TaxTypeDTO taxTypeDTO);

    /**
     * Get all the taxTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaxTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" taxType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaxTypeDTO> findOne(Integer id);

    /**
     * Delete the "id" taxType.
     *
     * @param id the id of the entity.
     */
    void delete(Integer id);
}
