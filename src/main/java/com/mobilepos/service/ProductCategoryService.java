package com.mobilepos.service;

import com.mobilepos.service.dto.ProductCategoryDTO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mobilepos.domain.ProductCategory}.
 */
public interface ProductCategoryService {
    /**
     * Save a productCategory.
     *
     * @param productCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    ProductCategoryDTO save(ProductCategoryDTO productCategoryDTO);

    /**
     * Updates a productCategory.
     *
     * @param productCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    ProductCategoryDTO update(ProductCategoryDTO productCategoryDTO);

    /**
     * Partially updates a productCategory.
     *
     * @param productCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductCategoryDTO> partialUpdate(ProductCategoryDTO productCategoryDTO);

    /**
     * Get all the productCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductCategoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" productCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductCategoryDTO> findOne(UUID id);

    /**
     * Delete the "id" productCategory.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
