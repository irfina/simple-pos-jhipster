package com.mobilepos.web.rest;

import com.mobilepos.repository.CountryRepository;
import com.mobilepos.service.CountryService;
import com.mobilepos.service.dto.CountryDTO;
import com.mobilepos.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mobilepos.domain.Country}.
 */
@RestController
@RequestMapping("/api/countries")
public class CountryResource {

    private final Logger log = LoggerFactory.getLogger(CountryResource.class);

    private static final String ENTITY_NAME = "country";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountryService countryService;

    private final CountryRepository countryRepository;

    public CountryResource(CountryService countryService, CountryRepository countryRepository) {
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }

    /**
     * {@code POST  /countries} : Create a new country.
     *
     * @param countryDTO the countryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countryDTO, or with status {@code 400 (Bad Request)} if the country has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CountryDTO> createCountry(@Valid @RequestBody CountryDTO countryDTO) throws URISyntaxException {
        log.debug("REST request to save Country : {}", countryDTO);
        if (countryRepository.existsById(countryDTO.getIsoCode())) {
            throw new BadRequestAlertException("country already exists", ENTITY_NAME, "idexists");
        }
        countryDTO = countryService.save(countryDTO);
        return ResponseEntity.created(new URI("/api/countries/" + countryDTO.getIsoCode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, countryDTO.getIsoCode()))
            .body(countryDTO);
    }

    /**
     * {@code PUT  /countries/:isoCode} : Updates an existing country.
     *
     * @param isoCode the id of the countryDTO to save.
     * @param countryDTO the countryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countryDTO,
     * or with status {@code 400 (Bad Request)} if the countryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{isoCode}")
    public ResponseEntity<CountryDTO> updateCountry(
        @PathVariable(value = "isoCode", required = false) final String isoCode,
        @Valid @RequestBody CountryDTO countryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Country : {}, {}", isoCode, countryDTO);
        if (countryDTO.getIsoCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(isoCode, countryDTO.getIsoCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!countryRepository.existsById(isoCode)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        countryDTO = countryService.update(countryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countryDTO.getIsoCode()))
            .body(countryDTO);
    }

    /**
     * {@code PATCH  /countries/:isoCode} : Partial updates given fields of an existing country, field will ignore if it is null
     *
     * @param isoCode the id of the countryDTO to save.
     * @param countryDTO the countryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countryDTO,
     * or with status {@code 400 (Bad Request)} if the countryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the countryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the countryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{isoCode}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CountryDTO> partialUpdateCountry(
        @PathVariable(value = "isoCode", required = false) final String isoCode,
        @NotNull @RequestBody CountryDTO countryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Country partially : {}, {}", isoCode, countryDTO);
        if (countryDTO.getIsoCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(isoCode, countryDTO.getIsoCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!countryRepository.existsById(isoCode)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CountryDTO> result = countryService.partialUpdate(countryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countryDTO.getIsoCode())
        );
    }

    /**
     * {@code GET  /countries} : get all the countries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countries in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CountryDTO>> getAllCountries(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Countries");
        Page<CountryDTO> page = countryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /countries/:id} : get the "id" country.
     *
     * @param id the id of the countryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountry(@PathVariable("id") String id) {
        log.debug("REST request to get Country : {}", id);
        Optional<CountryDTO> countryDTO = countryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(countryDTO);
    }

    /**
     * {@code DELETE  /countries/:id} : delete the "id" country.
     *
     * @param id the id of the countryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable("id") String id) {
        log.debug("REST request to delete Country : {}", id);
        countryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
