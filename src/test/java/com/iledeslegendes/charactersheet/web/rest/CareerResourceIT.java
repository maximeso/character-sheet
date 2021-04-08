package com.iledeslegendes.charactersheet.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iledeslegendes.charactersheet.IntegrationTest;
import com.iledeslegendes.charactersheet.domain.Career;
import com.iledeslegendes.charactersheet.repository.CareerRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CareerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CareerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/careers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCareerMockMvc;

    private Career career;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Career createEntity(EntityManager em) {
        Career career = new Career().name(DEFAULT_NAME);
        return career;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Career createUpdatedEntity(EntityManager em) {
        Career career = new Career().name(UPDATED_NAME);
        return career;
    }

    @BeforeEach
    public void initTest() {
        career = createEntity(em);
    }

    @Test
    @Transactional
    void createCareer() throws Exception {
        int databaseSizeBeforeCreate = careerRepository.findAll().size();
        // Create the Career
        restCareerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(career)))
            .andExpect(status().isCreated());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeCreate + 1);
        Career testCareer = careerList.get(careerList.size() - 1);
        assertThat(testCareer.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCareerWithExistingId() throws Exception {
        // Create the Career with an existing ID
        career.setId(1L);

        int databaseSizeBeforeCreate = careerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCareerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(career)))
            .andExpect(status().isBadRequest());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = careerRepository.findAll().size();
        // set the field null
        career.setName(null);

        // Create the Career, which fails.

        restCareerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(career)))
            .andExpect(status().isBadRequest());

        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCareers() throws Exception {
        // Initialize the database
        careerRepository.saveAndFlush(career);

        // Get all the careerList
        restCareerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(career.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCareer() throws Exception {
        // Initialize the database
        careerRepository.saveAndFlush(career);

        // Get the career
        restCareerMockMvc
            .perform(get(ENTITY_API_URL_ID, career.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(career.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCareer() throws Exception {
        // Get the career
        restCareerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCareer() throws Exception {
        // Initialize the database
        careerRepository.saveAndFlush(career);

        int databaseSizeBeforeUpdate = careerRepository.findAll().size();

        // Update the career
        Career updatedCareer = careerRepository.findById(career.getId()).get();
        // Disconnect from session so that the updates on updatedCareer are not directly saved in db
        em.detach(updatedCareer);
        updatedCareer.name(UPDATED_NAME);

        restCareerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCareer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCareer))
            )
            .andExpect(status().isOk());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeUpdate);
        Career testCareer = careerList.get(careerList.size() - 1);
        assertThat(testCareer.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCareer() throws Exception {
        int databaseSizeBeforeUpdate = careerRepository.findAll().size();
        career.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCareerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, career.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(career))
            )
            .andExpect(status().isBadRequest());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCareer() throws Exception {
        int databaseSizeBeforeUpdate = careerRepository.findAll().size();
        career.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCareerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(career))
            )
            .andExpect(status().isBadRequest());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCareer() throws Exception {
        int databaseSizeBeforeUpdate = careerRepository.findAll().size();
        career.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCareerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(career)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCareerWithPatch() throws Exception {
        // Initialize the database
        careerRepository.saveAndFlush(career);

        int databaseSizeBeforeUpdate = careerRepository.findAll().size();

        // Update the career using partial update
        Career partialUpdatedCareer = new Career();
        partialUpdatedCareer.setId(career.getId());

        partialUpdatedCareer.name(UPDATED_NAME);

        restCareerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCareer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCareer))
            )
            .andExpect(status().isOk());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeUpdate);
        Career testCareer = careerList.get(careerList.size() - 1);
        assertThat(testCareer.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCareerWithPatch() throws Exception {
        // Initialize the database
        careerRepository.saveAndFlush(career);

        int databaseSizeBeforeUpdate = careerRepository.findAll().size();

        // Update the career using partial update
        Career partialUpdatedCareer = new Career();
        partialUpdatedCareer.setId(career.getId());

        partialUpdatedCareer.name(UPDATED_NAME);

        restCareerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCareer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCareer))
            )
            .andExpect(status().isOk());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeUpdate);
        Career testCareer = careerList.get(careerList.size() - 1);
        assertThat(testCareer.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCareer() throws Exception {
        int databaseSizeBeforeUpdate = careerRepository.findAll().size();
        career.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCareerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, career.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(career))
            )
            .andExpect(status().isBadRequest());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCareer() throws Exception {
        int databaseSizeBeforeUpdate = careerRepository.findAll().size();
        career.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCareerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(career))
            )
            .andExpect(status().isBadRequest());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCareer() throws Exception {
        int databaseSizeBeforeUpdate = careerRepository.findAll().size();
        career.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCareerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(career)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCareer() throws Exception {
        // Initialize the database
        careerRepository.saveAndFlush(career);

        int databaseSizeBeforeDelete = careerRepository.findAll().size();

        // Delete the career
        restCareerMockMvc
            .perform(delete(ENTITY_API_URL_ID, career.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
