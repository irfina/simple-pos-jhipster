package com.mobilepos.service;

import com.mobilepos.domain.Country;
import com.mobilepos.service.dto.CountryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mobilepos.domain.Country}.
 */
public interface CountryService {
    /**
     * Save a country.
     *
     * @param countryDTO the entity to save.
     * @return the persisted entity.
     */
    CountryDTO save(CountryDTO countryDTO);

    /**
     * Updates a country.
     *
     * @param countryDTO the entity to update.
     * @return the persisted entity.
     */
    CountryDTO update(CountryDTO countryDTO);

    /**
     * Partially updates a country.
     *
     * @param countryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CountryDTO> partialUpdate(CountryDTO countryDTO);

    /**
     * Get all the countries.
     *
     * @return the list of entities.
     */
    List<CountryDTO> findAll();

    /**
     * Get the "id" country.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CountryDTO> findOne(String id);

    Optional<Country> findByIsoCode(String _isoCode);

    /**
     * Delete the "id" country.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
