package com.clinicallink.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clinicallink.app.IntegrationTest;
import com.clinicallink.app.domain.Especialidade;
import com.clinicallink.app.domain.EspecialidadeEspecialista;
import com.clinicallink.app.repository.EspecialidadeRepository;
import com.clinicallink.app.service.dto.EspecialidadeDto;
import com.clinicallink.app.service.mapper.EspecialidadeMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EspecialidadeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EspecialidadeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/especialidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private EspecialidadeMapper especialidadeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEspecialidadeMockMvc;

    private Especialidade especialidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Especialidade createEntity(EntityManager em) {
        Especialidade especialidade = new Especialidade().name(DEFAULT_NAME);
        return especialidade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Especialidade createUpdatedEntity(EntityManager em) {
        Especialidade especialidade = new Especialidade().name(UPDATED_NAME);
        return especialidade;
    }

    @BeforeEach
    public void initTest() {
        especialidade = createEntity(em);
    }

    @Test
    @Transactional
    void createEspecialidade() throws Exception {
        int databaseSizeBeforeCreate = especialidadeRepository.findAll().size();
        // Create the Especialidade
        EspecialidadeDto especialidadeDto = especialidadeMapper.toDto(especialidade);
        restEspecialidadeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(especialidadeDto))
            )
            .andExpect(status().isCreated());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Especialidade testEspecialidade = especialidadeList.get(especialidadeList.size() - 1);
        assertThat(testEspecialidade.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createEspecialidadeWithExistingId() throws Exception {
        // Create the Especialidade with an existing ID
        especialidade.setId(1L);
        EspecialidadeDto especialidadeDto = especialidadeMapper.toDto(especialidade);

        int databaseSizeBeforeCreate = especialidadeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspecialidadeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(especialidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEspecialidades() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        // Get all the especialidadeList
        restEspecialidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especialidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getEspecialidade() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        // Get the especialidade
        restEspecialidadeMockMvc
            .perform(get(ENTITY_API_URL_ID, especialidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(especialidade.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getEspecialidadesByIdFiltering() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        Long id = especialidade.getId();

        defaultEspecialidadeShouldBeFound("id.equals=" + id);
        defaultEspecialidadeShouldNotBeFound("id.notEquals=" + id);

        defaultEspecialidadeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEspecialidadeShouldNotBeFound("id.greaterThan=" + id);

        defaultEspecialidadeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEspecialidadeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEspecialidadesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        // Get all the especialidadeList where name equals to DEFAULT_NAME
        defaultEspecialidadeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the especialidadeList where name equals to UPDATED_NAME
        defaultEspecialidadeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEspecialidadesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        // Get all the especialidadeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEspecialidadeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the especialidadeList where name equals to UPDATED_NAME
        defaultEspecialidadeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEspecialidadesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        // Get all the especialidadeList where name is not null
        defaultEspecialidadeShouldBeFound("name.specified=true");

        // Get all the especialidadeList where name is null
        defaultEspecialidadeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllEspecialidadesByNameContainsSomething() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        // Get all the especialidadeList where name contains DEFAULT_NAME
        defaultEspecialidadeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the especialidadeList where name contains UPDATED_NAME
        defaultEspecialidadeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEspecialidadesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        // Get all the especialidadeList where name does not contain DEFAULT_NAME
        defaultEspecialidadeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the especialidadeList where name does not contain UPDATED_NAME
        defaultEspecialidadeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEspecialidadesByEspecialidadeEspecialistaIsEqualToSomething() throws Exception {
        EspecialidadeEspecialista especialidadeEspecialista;
        if (TestUtil.findAll(em, EspecialidadeEspecialista.class).isEmpty()) {
            especialidadeRepository.saveAndFlush(especialidade);
            especialidadeEspecialista = EspecialidadeEspecialistaResourceIT.createEntity(em);
        } else {
            especialidadeEspecialista = TestUtil.findAll(em, EspecialidadeEspecialista.class).get(0);
        }
        em.persist(especialidadeEspecialista);
        em.flush();
        especialidade.addEspecialidadeEspecialista(especialidadeEspecialista);
        especialidadeRepository.saveAndFlush(especialidade);
        Long especialidadeEspecialistaId = especialidadeEspecialista.getId();
        // Get all the especialidadeList where especialidadeEspecialista equals to especialidadeEspecialistaId
        defaultEspecialidadeShouldBeFound("especialidadeEspecialistaId.equals=" + especialidadeEspecialistaId);

        // Get all the especialidadeList where especialidadeEspecialista equals to (especialidadeEspecialistaId + 1)
        defaultEspecialidadeShouldNotBeFound("especialidadeEspecialistaId.equals=" + (especialidadeEspecialistaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEspecialidadeShouldBeFound(String filter) throws Exception {
        restEspecialidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especialidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restEspecialidadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEspecialidadeShouldNotBeFound(String filter) throws Exception {
        restEspecialidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEspecialidadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEspecialidade() throws Exception {
        // Get the especialidade
        restEspecialidadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEspecialidade() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        int databaseSizeBeforeUpdate = especialidadeRepository.findAll().size();

        // Update the especialidade
        Especialidade updatedEspecialidade = especialidadeRepository.findById(especialidade.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEspecialidade are not directly saved in db
        em.detach(updatedEspecialidade);
        updatedEspecialidade.name(UPDATED_NAME);
        EspecialidadeDto especialidadeDto = especialidadeMapper.toDto(updatedEspecialidade);

        restEspecialidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, especialidadeDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeDto))
            )
            .andExpect(status().isOk());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeUpdate);
        Especialidade testEspecialidade = especialidadeList.get(especialidadeList.size() - 1);
        assertThat(testEspecialidade.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingEspecialidade() throws Exception {
        int databaseSizeBeforeUpdate = especialidadeRepository.findAll().size();
        especialidade.setId(longCount.incrementAndGet());

        // Create the Especialidade
        EspecialidadeDto especialidadeDto = especialidadeMapper.toDto(especialidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspecialidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, especialidadeDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEspecialidade() throws Exception {
        int databaseSizeBeforeUpdate = especialidadeRepository.findAll().size();
        especialidade.setId(longCount.incrementAndGet());

        // Create the Especialidade
        EspecialidadeDto especialidadeDto = especialidadeMapper.toDto(especialidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEspecialidade() throws Exception {
        int databaseSizeBeforeUpdate = especialidadeRepository.findAll().size();
        especialidade.setId(longCount.incrementAndGet());

        // Create the Especialidade
        EspecialidadeDto especialidadeDto = especialidadeMapper.toDto(especialidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialidadeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(especialidadeDto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEspecialidadeWithPatch() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        int databaseSizeBeforeUpdate = especialidadeRepository.findAll().size();

        // Update the especialidade using partial update
        Especialidade partialUpdatedEspecialidade = new Especialidade();
        partialUpdatedEspecialidade.setId(especialidade.getId());

        partialUpdatedEspecialidade.name(UPDATED_NAME);

        restEspecialidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspecialidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspecialidade))
            )
            .andExpect(status().isOk());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeUpdate);
        Especialidade testEspecialidade = especialidadeList.get(especialidadeList.size() - 1);
        assertThat(testEspecialidade.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateEspecialidadeWithPatch() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        int databaseSizeBeforeUpdate = especialidadeRepository.findAll().size();

        // Update the especialidade using partial update
        Especialidade partialUpdatedEspecialidade = new Especialidade();
        partialUpdatedEspecialidade.setId(especialidade.getId());

        partialUpdatedEspecialidade.name(UPDATED_NAME);

        restEspecialidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspecialidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspecialidade))
            )
            .andExpect(status().isOk());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeUpdate);
        Especialidade testEspecialidade = especialidadeList.get(especialidadeList.size() - 1);
        assertThat(testEspecialidade.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingEspecialidade() throws Exception {
        int databaseSizeBeforeUpdate = especialidadeRepository.findAll().size();
        especialidade.setId(longCount.incrementAndGet());

        // Create the Especialidade
        EspecialidadeDto especialidadeDto = especialidadeMapper.toDto(especialidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspecialidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, especialidadeDto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEspecialidade() throws Exception {
        int databaseSizeBeforeUpdate = especialidadeRepository.findAll().size();
        especialidade.setId(longCount.incrementAndGet());

        // Create the Especialidade
        EspecialidadeDto especialidadeDto = especialidadeMapper.toDto(especialidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEspecialidade() throws Exception {
        int databaseSizeBeforeUpdate = especialidadeRepository.findAll().size();
        especialidade.setId(longCount.incrementAndGet());

        // Create the Especialidade
        EspecialidadeDto especialidadeDto = especialidadeMapper.toDto(especialidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialidadeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeDto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEspecialidade() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        int databaseSizeBeforeDelete = especialidadeRepository.findAll().size();

        // Delete the especialidade
        restEspecialidadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, especialidade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
