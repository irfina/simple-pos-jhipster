package com.mobilepos.web.rest;

import com.mobilepos.repository.TaxTypeRepository;
import com.mobilepos.service.TaxTypeService;
import com.mobilepos.service.dto.TaxTypeDTO;
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
 * REST controller for managing {@link com.mobilepos.domain.TaxType}.
 */
@RestController
@RequestMapping("/api/tax-types")
public class TaxTypeResource {

    private final Logger log = LoggerFactory.getLogger(TaxTypeResource.class);

    private static final String ENTITY_NAME = "taxType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxTypeService taxTypeService;

    private final TaxTypeRepository taxTypeRepository;

    public TaxTypeResource(TaxTypeService taxTypeService, TaxTypeRepository taxTypeRepository) {
        this.taxTypeService = taxTypeService;
        this.taxTypeRepository = taxTypeRepository;
    }

    /**
     * {@code POST  /tax-types} : Create a new taxType.
     *
     * @param taxTypeDTO the taxTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxTypeDTO, or with status {@code 400 (Bad Request)} if the taxType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TaxTypeDTO> createTaxType(@Valid @RequestBody TaxTypeDTO taxTypeDTO) throws URISyntaxException {
        log.debug("REST request to save TaxType : {}", taxTypeDTO);
        if (taxTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new taxType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        taxTypeDTO = taxTypeService.save(taxTypeDTO);
        return ResponseEntity.created(new URI("/api/tax-types/" + taxTypeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, taxTypeDTO.getId().toString()))
            .body(taxTypeDTO);
    }

    /**
     * {@code PUT  /tax-types/:id} : Updates an existing taxType.
     *
     * @param id the id of the taxTypeDTO to save.
     * @param taxTypeDTO the taxTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxTypeDTO,
     * or with status {@code 400 (Bad Request)} if the taxTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaxTypeDTO> updateTaxType(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody TaxTypeDTO taxTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaxType : {}, {}", id, taxTypeDTO);
        if (taxTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        taxTypeDTO = taxTypeService.update(taxTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxTypeDTO.getId().toString()))
            .body(taxTypeDTO);
    }

    /**
     * {@code PATCH  /tax-types/:id} : Partial updates given fields of an existing taxType, field will ignore if it is null
     *
     * @param id the id of the taxTypeDTO to save.
     * @param taxTypeDTO the taxTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxTypeDTO,
     * or with status {@code 400 (Bad Request)} if the taxTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taxTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taxTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaxTypeDTO> partialUpdateTaxType(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody TaxTypeDTO taxTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaxType partially : {}, {}", id, taxTypeDTO);
        if (taxTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaxTypeDTO> result = taxTypeService.partialUpdate(taxTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tax-types} : get all the taxTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TaxTypeDTO>> getAllTaxTypes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TaxTypes");
        Page<TaxTypeDTO> page = taxTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tax-types/:id} : get the "id" taxType.
     *
     * @param id the id of the taxTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaxTypeDTO> getTaxType(@PathVariable("id") Integer id) {
        log.debug("REST request to get TaxType : {}", id);
        Optional<TaxTypeDTO> taxTypeDTO = taxTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxTypeDTO);
    }

    /**
     * {@code DELETE  /tax-types/:id} : delete the "id" taxType.
     *
     * @param id the id of the taxTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxType(@PathVariable("id") Integer id) {
        log.debug("REST request to delete TaxType : {}", id);
        taxTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
