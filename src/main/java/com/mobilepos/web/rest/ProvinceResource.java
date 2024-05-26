package com.mobilepos.web.rest;

import com.mobilepos.repository.ProvinceRepository;
import com.mobilepos.service.ProvinceService;
import com.mobilepos.service.dto.ProvinceCreateDto;
import com.mobilepos.service.dto.ProvinceDto;
import com.mobilepos.service.dto.ProvinceUpdateDto;
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
 * REST controller for managing {@link com.mobilepos.domain.Province}.
 */
@RestController
@RequestMapping("/api/provinces")
public class ProvinceResource {

    private final Logger log = LoggerFactory.getLogger(ProvinceResource.class);

    private static final String ENTITY_NAME = "province";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProvinceService provinceService;

    private final ProvinceRepository provinceRepository;

    public ProvinceResource(ProvinceService provinceService, ProvinceRepository provinceRepository) {
        this.provinceService = provinceService;
        this.provinceRepository = provinceRepository;
    }

    /**
     * {@code POST  /provinces} : Create a new province.
     *
     * @param _createDto the _createDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new _createDto, or with status {@code 400 (Bad Request)} if the province has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProvinceDto> createProvince(@Valid @RequestBody ProvinceCreateDto _createDto) throws URISyntaxException {
        log.debug("REST request to save Province : {}", _createDto);

        var provinceDto = provinceService.save(_createDto);
        return ResponseEntity.created(new URI("/api/provinces/" + provinceDto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, provinceDto.getId().toString()))
            .body(provinceDto);
    }

    /**
     * {@code PUT  /provinces/:id} : Updates an existing province.
     *
     * @param id          the id of the provinceDTO to save.
     * @param _updateDto the provinceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provinceDTO,
     * or with status {@code 400 (Bad Request)} if the provinceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the provinceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProvinceDto> updateProvince(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody ProvinceUpdateDto _updateDto
    ) throws URISyntaxException {
        log.debug("REST request to update Province : {}, {}", id, _updateDto);
        if (!provinceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        var provinceDto = provinceService.update(_updateDto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, provinceDto.getId().toString()))
            .body(provinceDto);
    }

    /**
     * {@code PATCH  /provinces/:id} : Partial updates given fields of an existing province, field will ignore if it is null
     *
     * @param id          the id of the provinceDTO to save.
     * @param provinceDTO the provinceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provinceDTO,
     * or with status {@code 400 (Bad Request)} if the provinceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the provinceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the provinceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProvinceDto> partialUpdateProvince(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody ProvinceDto provinceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Province partially : {}, {}", id, provinceDTO);
        if (provinceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, provinceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!provinceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProvinceDto> result = provinceService.partialUpdate(provinceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, provinceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /provinces} : get all the provinces.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of provinces in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProvinceDto>> getAllProvinces(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Provinces");
        Page<ProvinceDto> page = provinceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /provinces/:id} : get the "id" province.
     *
     * @param id the id of the provinceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the provinceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProvinceDto> getProvince(@PathVariable("id") Integer id) {
        log.debug("REST request to get Province : {}", id);
        Optional<ProvinceDto> provinceDTO = provinceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(provinceDTO);
    }

    /**
     * {@code DELETE  /provinces/:id} : delete the "id" province.
     *
     * @param id the id of the provinceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable("id") Integer id) {
        log.debug("REST request to delete Province : {}", id);
        provinceService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
