package com.mobilepos.web.rest;

import static com.mobilepos.domain.RegencyAsserts.*;
import static com.mobilepos.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobilepos.IntegrationTest;
import com.mobilepos.domain.Province;
import com.mobilepos.domain.Regency;
import com.mobilepos.repository.RegencyRepository;
import com.mobilepos.service.dto.RegencyDTO;
import com.mobilepos.service.mapper.RegencyMapper;
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
 * Integration tests for the {@link RegencyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegencyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/regencies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RegencyRepository regencyRepository;

    @Autowired
    private RegencyMapper regencyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegencyMockMvc;

    private Regency regency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regency createEntity(EntityManager em) {
        Regency regency = new Regency().name(DEFAULT_NAME);
        // Add required entity
        Province province;
        if (TestUtil.findAll(em, Province.class).isEmpty()) {
            province = ProvinceResourceIT.createEntity(em);
            em.persist(province);
            em.flush();
        } else {
            province = TestUtil.findAll(em, Province.class).get(0);
        }
        regency.setProvince(province);
        return regency;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regency createUpdatedEntity(EntityManager em) {
        Regency regency = new Regency().name(UPDATED_NAME);
        // Add required entity
        Province province;
        if (TestUtil.findAll(em, Province.class).isEmpty()) {
            province = ProvinceResourceIT.createUpdatedEntity(em);
            em.persist(province);
            em.flush();
        } else {
            province = TestUtil.findAll(em, Province.class).get(0);
        }
        regency.setProvince(province);
        return regency;
    }

    @BeforeEach
    public void initTest() {
        regency = createEntity(em);
    }

    @Test
    @Transactional
    void createRegency() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Regency
        RegencyDTO regencyDTO = regencyMapper.toDto(regency);
        var returnedRegencyDTO = om.readValue(
            restRegencyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regencyDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RegencyDTO.class
        );

        // Validate the Regency in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRegency = regencyMapper.toEntity(returnedRegencyDTO);
        assertRegencyUpdatableFieldsEquals(returnedRegency, getPersistedRegency(returnedRegency));
    }

    @Test
    @Transactional
    void createRegencyWithExistingId() throws Exception {
        // Create the Regency with an existing ID
        regency.setId(1);
        RegencyDTO regencyDTO = regencyMapper.toDto(regency);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Regency in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        regency.setName(null);

        // Create the Regency, which fails.
        RegencyDTO regencyDTO = regencyMapper.toDto(regency);

        restRegencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regencyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRegencies() throws Exception {
        // Initialize the database
        regencyRepository.saveAndFlush(regency);

        // Get all the regencyList
        restRegencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRegency() throws Exception {
        // Initialize the database
        regencyRepository.saveAndFlush(regency);

        // Get the regency
        restRegencyMockMvc
            .perform(get(ENTITY_API_URL_ID, regency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRegency() throws Exception {
        // Get the regency
        restRegencyMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegency() throws Exception {
        // Initialize the database
        regencyRepository.saveAndFlush(regency);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regency
        Regency updatedRegency = regencyRepository.findById(regency.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRegency are not directly saved in db
        em.detach(updatedRegency);
        updatedRegency.name(UPDATED_NAME);
        RegencyDTO regencyDTO = regencyMapper.toDto(updatedRegency);

        restRegencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regencyDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regencyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Regency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRegencyToMatchAllProperties(updatedRegency);
    }

    @Test
    @Transactional
    void putNonExistingRegency() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regency.setId(intCount.incrementAndGet());

        // Create the Regency
        RegencyDTO regencyDTO = regencyMapper.toDto(regency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regencyDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegency() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regency.setId(intCount.incrementAndGet());

        // Create the Regency
        RegencyDTO regencyDTO = regencyMapper.toDto(regency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(regencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegency() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regency.setId(intCount.incrementAndGet());

        // Create the Regency
        RegencyDTO regencyDTO = regencyMapper.toDto(regency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegencyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regencyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegencyWithPatch() throws Exception {
        // Initialize the database
        regencyRepository.saveAndFlush(regency);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regency using partial update
        Regency partialUpdatedRegency = new Regency();
        partialUpdatedRegency.setId(regency.getId());

        restRegencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegency.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRegency))
            )
            .andExpect(status().isOk());

        // Validate the Regency in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRegencyUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRegency, regency), getPersistedRegency(regency));
    }

    @Test
    @Transactional
    void fullUpdateRegencyWithPatch() throws Exception {
        // Initialize the database
        regencyRepository.saveAndFlush(regency);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regency using partial update
        Regency partialUpdatedRegency = new Regency();
        partialUpdatedRegency.setId(regency.getId());

        partialUpdatedRegency.name(UPDATED_NAME);

        restRegencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegency.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRegency))
            )
            .andExpect(status().isOk());

        // Validate the Regency in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRegencyUpdatableFieldsEquals(partialUpdatedRegency, getPersistedRegency(partialUpdatedRegency));
    }

    @Test
    @Transactional
    void patchNonExistingRegency() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regency.setId(intCount.incrementAndGet());

        // Create the Regency
        RegencyDTO regencyDTO = regencyMapper.toDto(regency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, regencyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(regencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegency() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regency.setId(intCount.incrementAndGet());

        // Create the Regency
        RegencyDTO regencyDTO = regencyMapper.toDto(regency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(regencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegency() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regency.setId(intCount.incrementAndGet());

        // Create the Regency
        RegencyDTO regencyDTO = regencyMapper.toDto(regency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegencyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(regencyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegency() throws Exception {
        // Initialize the database
        regencyRepository.saveAndFlush(regency);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the regency
        restRegencyMockMvc
            .perform(delete(ENTITY_API_URL_ID, regency.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return regencyRepository.count();
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

    protected Regency getPersistedRegency(Regency regency) {
        return regencyRepository.findById(regency.getId()).orElseThrow();
    }

    protected void assertPersistedRegencyToMatchAllProperties(Regency expectedRegency) {
        assertRegencyAllPropertiesEquals(expectedRegency, getPersistedRegency(expectedRegency));
    }

    protected void assertPersistedRegencyToMatchUpdatableProperties(Regency expectedRegency) {
        assertRegencyAllUpdatablePropertiesEquals(expectedRegency, getPersistedRegency(expectedRegency));
    }
}
