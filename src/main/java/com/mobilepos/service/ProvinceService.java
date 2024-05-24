package com.mobilepos.service;

import com.mobilepos.service.dto.ProvinceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mobilepos.domain.Province}.
 */
public interface ProvinceService {
    /**
     * Save a province.
     *
     * @param provinceDTO the entity to save.
     * @return the persisted entity.
     */
    ProvinceDTO save(ProvinceDTO provinceDTO);

    /**
     * Updates a province.
     *
     * @param provinceDTO the entity to update.
     * @return the persisted entity.
     */
    ProvinceDTO update(ProvinceDTO provinceDTO);

    /**
     * Partially updates a province.
     *
     * @param provinceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProvinceDTO> partialUpdate(ProvinceDTO provinceDTO);

    /**
     * Get all the provinces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProvinceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" province.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProvinceDTO> findOne(Integer id);

    /**
     * Delete the "id" province.
     *
     * @param id the id of the entity.
     */
    void delete(Integer id);
}
