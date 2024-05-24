package com.mobilepos.web.rest;

import com.mobilepos.repository.RegencyRepository;
import com.mobilepos.service.RegencyService;
import com.mobilepos.service.dto.RegencyDTO;
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
 * REST controller for managing {@link com.mobilepos.domain.Regency}.
 */
@RestController
@RequestMapping("/api/regencies")
public class RegencyResource {

    private final Logger log = LoggerFactory.getLogger(RegencyResource.class);

    private static final String ENTITY_NAME = "regency";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegencyService regencyService;

    private final RegencyRepository regencyRepository;

    public RegencyResource(RegencyService regencyService, RegencyRepository regencyRepository) {
        this.regencyService = regencyService;
        this.regencyRepository = regencyRepository;
    }

    /**
     * {@code POST  /regencies} : Create a new regency.
     *
     * @param regencyDTO the regencyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regencyDTO, or with status {@code 400 (Bad Request)} if the regency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RegencyDTO> createRegency(@Valid @RequestBody RegencyDTO regencyDTO) throws URISyntaxException {
        log.debug("REST request to save Regency : {}", regencyDTO);
        if (regencyDTO.getId() != null) {
            throw new BadRequestAlertException("A new regency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        regencyDTO = regencyService.save(regencyDTO);
        return ResponseEntity.created(new URI("/api/regencies/" + regencyDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, regencyDTO.getId().toString()))
            .body(regencyDTO);
    }

    /**
     * {@code PUT  /regencies/:id} : Updates an existing regency.
     *
     * @param id the id of the regencyDTO to save.
     * @param regencyDTO the regencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regencyDTO,
     * or with status {@code 400 (Bad Request)} if the regencyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RegencyDTO> updateRegency(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody RegencyDTO regencyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Regency : {}, {}", id, regencyDTO);
        if (regencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regencyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regencyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        regencyDTO = regencyService.update(regencyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regencyDTO.getId().toString()))
            .body(regencyDTO);
    }

    /**
     * {@code PATCH  /regencies/:id} : Partial updates given fields of an existing regency, field will ignore if it is null
     *
     * @param id the id of the regencyDTO to save.
     * @param regencyDTO the regencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regencyDTO,
     * or with status {@code 400 (Bad Request)} if the regencyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the regencyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the regencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RegencyDTO> partialUpdateRegency(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody RegencyDTO regencyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Regency partially : {}, {}", id, regencyDTO);
        if (regencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regencyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regencyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RegencyDTO> result = regencyService.partialUpdate(regencyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regencyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /regencies} : get all the regencies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regencies in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RegencyDTO>> getAllRegencies(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Regencies");
        Page<RegencyDTO> page = regencyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /regencies/:id} : get the "id" regency.
     *
     * @param id the id of the regencyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regencyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RegencyDTO> getRegency(@PathVariable("id") Integer id) {
        log.debug("REST request to get Regency : {}", id);
        Optional<RegencyDTO> regencyDTO = regencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regencyDTO);
    }

    /**
     * {@code DELETE  /regencies/:id} : delete the "id" regency.
     *
     * @param id the id of the regencyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegency(@PathVariable("id") Integer id) {
        log.debug("REST request to delete Regency : {}", id);
        regencyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
