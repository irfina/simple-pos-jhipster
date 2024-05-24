package com.mobilepos.web.rest;

import static com.mobilepos.domain.TaxTypeAsserts.*;
import static com.mobilepos.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobilepos.IntegrationTest;
import com.mobilepos.domain.TaxType;
import com.mobilepos.repository.TaxTypeRepository;
import com.mobilepos.service.dto.TaxTypeDTO;
import com.mobilepos.service.mapper.TaxTypeMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TaxTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaxTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tax-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaxTypeRepository taxTypeRepository;

    @Autowired
    private TaxTypeMapper taxTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxTypeMockMvc;

    private TaxType taxType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxType createEntity(EntityManager em) {
        TaxType taxType = new TaxType().code(DEFAULT_CODE).name(DEFAULT_NAME).value(DEFAULT_VALUE).notes(DEFAULT_NOTES);
        return taxType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxType createUpdatedEntity(EntityManager em) {
        TaxType taxType = new TaxType().code(UPDATED_CODE).name(UPDATED_NAME).value(UPDATED_VALUE).notes(UPDATED_NOTES);
        return taxType;
    }

    @BeforeEach
    public void initTest() {
        taxType = createEntity(em);
    }

    @Test
    @Transactional
    void createTaxType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TaxType
        TaxTypeDTO taxTypeDTO = taxTypeMapper.toDto(taxType);
        var returnedTaxTypeDTO = om.readValue(
            restTaxTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxTypeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TaxTypeDTO.class
        );

        // Validate the TaxType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTaxType = taxTypeMapper.toEntity(returnedTaxTypeDTO);
        assertTaxTypeUpdatableFieldsEquals(returnedTaxType, getPersistedTaxType(returnedTaxType));
    }

    @Test
    @Transactional
    void createTaxTypeWithExistingId() throws Exception {
        // Create the TaxType with an existing ID
        taxType.setId(1);
        TaxTypeDTO taxTypeDTO = taxTypeMapper.toDto(taxType);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaxType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        taxType.setCode(null);

        // Create the TaxType, which fails.
        TaxTypeDTO taxTypeDTO = taxTypeMapper.toDto(taxType);

        restTaxTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxTypeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        taxType.setName(null);

        // Create the TaxType, which fails.
        TaxTypeDTO taxTypeDTO = taxTypeMapper.toDto(taxType);

        restTaxTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxTypeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        taxType.setValue(null);

        // Create the TaxType, which fails.
        TaxTypeDTO taxTypeDTO = taxTypeMapper.toDto(taxType);

        restTaxTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxTypeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaxTypes() throws Exception {
        // Initialize the database
        taxTypeRepository.saveAndFlush(taxType);

        // Get all the taxTypeList
        restTaxTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getTaxType() throws Exception {
        // Initialize the database
        taxTypeRepository.saveAndFlush(taxType);

        // Get the taxType
        restTaxTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, taxType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingTaxType() throws Exception {
        // Get the taxType
        restTaxTypeMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTaxType() throws Exception {
        // Initialize the database
        taxTypeRepository.saveAndFlush(taxType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxType
        TaxType updatedTaxType = taxTypeRepository.findById(taxType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTaxType are not directly saved in db
        em.detach(updatedTaxType);
        updatedTaxType.code(UPDATED_CODE).name(UPDATED_NAME).value(UPDATED_VALUE).notes(UPDATED_NOTES);
        TaxTypeDTO taxTypeDTO = taxTypeMapper.toDto(updatedTaxType);

        restTaxTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taxTypeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaxType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTaxTypeToMatchAllProperties(updatedTaxType);
    }

    @Test
    @Transactional
    void putNonExistingTaxType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxType.setId(intCount.incrementAndGet());

        // Create the TaxType
        TaxTypeDTO taxTypeDTO = taxTypeMapper.toDto(taxType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taxTypeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaxType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxType.setId(intCount.incrementAndGet());

        // Create the TaxType
        TaxTypeDTO taxTypeDTO = taxTypeMapper.toDto(taxType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(taxTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaxType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxType.setId(intCount.incrementAndGet());

        // Create the TaxType
        TaxTypeDTO taxTypeDTO = taxTypeMapper.toDto(taxType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(taxTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaxTypeWithPatch() throws Exception {
        // Initialize the database
        taxTypeRepository.saveAndFlush(taxType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxType using partial update
        TaxType partialUpdatedTaxType = new TaxType();
        partialUpdatedTaxType.setId(taxType.getId());

        partialUpdatedTaxType.code(UPDATED_CODE).value(UPDATED_VALUE);

        restTaxTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaxType))
            )
            .andExpect(status().isOk());

        // Validate the TaxType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaxTypeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTaxType, taxType), getPersistedTaxType(taxType));
    }

    @Test
    @Transactional
    void fullUpdateTaxTypeWithPatch() throws Exception {
        // Initialize the database
        taxTypeRepository.saveAndFlush(taxType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the taxType using partial update
        TaxType partialUpdatedTaxType = new TaxType();
        partialUpdatedTaxType.setId(taxType.getId());

        partialUpdatedTaxType.code(UPDATED_CODE).name(UPDATED_NAME).value(UPDATED_VALUE).notes(UPDATED_NOTES);

        restTaxTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTaxType))
            )
            .andExpect(status().isOk());

        // Validate the TaxType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTaxTypeUpdatableFieldsEquals(partialUpdatedTaxType, getPersistedTaxType(partialUpdatedTaxType));
    }

    @Test
    @Transactional
    void patchNonExistingTaxType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxType.setId(intCount.incrementAndGet());

        // Create the TaxType
        TaxTypeDTO taxTypeDTO = taxTypeMapper.toDto(taxType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taxTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(taxTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaxType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxType.setId(intCount.incrementAndGet());

        // Create the TaxType
        TaxTypeDTO taxTypeDTO = taxTypeMapper.toDto(taxType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(taxTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaxType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        taxType.setId(intCount.incrementAndGet());

        // Create the TaxType
        TaxTypeDTO taxTypeDTO = taxTypeMapper.toDto(taxType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(taxTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaxType() throws Exception {
        // Initialize the database
        taxTypeRepository.saveAndFlush(taxType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the taxType
        restTaxTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, taxType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return taxTypeRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected TaxType getPersistedTaxType(TaxType taxType) {
        return taxTypeRepository.findById(taxType.getId()).orElseThrow();
    }

    protected void assertPersistedTaxTypeToMatchAllProperties(TaxType expectedTaxType) {
        assertTaxTypeAllPropertiesEquals(expectedTaxType, getPersistedTaxType(expectedTaxType));
    }

    protected void assertPersistedTaxTypeToMatchUpdatableProperties(TaxType expectedTaxType) {
        assertTaxTypeAllUpdatablePropertiesEquals(expectedTaxType, getPersistedTaxType(expectedTaxType));
    }
}
