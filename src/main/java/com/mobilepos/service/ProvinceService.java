package com.mobilepos.service;

import com.mobilepos.service.dto.ProvinceCreateDto;
import com.mobilepos.service.dto.ProvinceDto;
import com.mobilepos.service.dto.ProvinceUpdateDto;
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
     * @param _createDto the entity to save.
     * @return the persisted entity.
     */
    ProvinceDto save(ProvinceCreateDto _createDto);

    /**
     * Updates a province.
     *
     * @param _updateDto the entity to update.
     * @return the persisted entity.
     */
    ProvinceDto update(ProvinceUpdateDto _updateDto);

    /**
     * Partially updates a province.
     *
     * @param provinceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProvinceDto> partialUpdate(ProvinceDto provinceDTO);

    /**
     * Get all the provinces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProvinceDto> findAll(Pageable pageable);

    /**
     * Get the "id" province.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProvinceDto> findOne(Integer id);

    /**
     * Delete the "id" province.
     *
     * @param id the id of the entity.
     */
    void delete(Integer id);
}
