package com.clinicallink.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clinicallink.app.IntegrationTest;
import com.clinicallink.app.domain.Especialista;
import com.clinicallink.app.domain.Universidade;
import com.clinicallink.app.repository.UniversidadeRepository;
import com.clinicallink.app.service.dto.UniversidadeDto;
import com.clinicallink.app.service.mapper.UniversidadeMapper;
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
 * Integration tests for the {@link UniversidadeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UniversidadeResourceIT {

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/universidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UniversidadeRepository universidadeRepository;

    @Autowired
    private UniversidadeMapper universidadeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUniversidadeMockMvc;

    private Universidade universidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Universidade createEntity(EntityManager em) {
        Universidade universidade = new Universidade().cnpj(DEFAULT_CNPJ).name(DEFAULT_NAME).cep(DEFAULT_CEP);
        return universidade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Universidade createUpdatedEntity(EntityManager em) {
        Universidade universidade = new Universidade().cnpj(UPDATED_CNPJ).name(UPDATED_NAME).cep(UPDATED_CEP);
        return universidade;
    }

    @BeforeEach
    public void initTest() {
        universidade = createEntity(em);
    }

    @Test
    @Transactional
    void createUniversidade() throws Exception {
        int databaseSizeBeforeCreate = universidadeRepository.findAll().size();
        // Create the Universidade
        UniversidadeDto universidadeDto = universidadeMapper.toDto(universidade);
        restUniversidadeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(universidadeDto))
            )
            .andExpect(status().isCreated());

        // Validate the Universidade in the database
        List<Universidade> universidadeList = universidadeRepository.findAll();
        assertThat(universidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Universidade testUniversidade = universidadeList.get(universidadeList.size() - 1);
        assertThat(testUniversidade.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testUniversidade.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUniversidade.getCep()).isEqualTo(DEFAULT_CEP);
    }

    @Test
    @Transactional
    void createUniversidadeWithExistingId() throws Exception {
        // Create the Universidade with an existing ID
        universidade.setId(1L);
        UniversidadeDto universidadeDto = universidadeMapper.toDto(universidade);

        int databaseSizeBeforeCreate = universidadeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniversidadeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(universidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Universidade in the database
        List<Universidade> universidadeList = universidadeRepository.findAll();
        assertThat(universidadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUniversidades() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList
        restUniversidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(universidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)));
    }

    @Test
    @Transactional
    void getUniversidade() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get the universidade
        restUniversidadeMockMvc
            .perform(get(ENTITY_API_URL_ID, universidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(universidade.getId().intValue()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP));
    }

    @Test
    @Transactional
    void getUniversidadesByIdFiltering() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        Long id = universidade.getId();

        defaultUniversidadeShouldBeFound("id.equals=" + id);
        defaultUniversidadeShouldNotBeFound("id.notEquals=" + id);

        defaultUniversidadeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUniversidadeShouldNotBeFound("id.greaterThan=" + id);

        defaultUniversidadeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUniversidadeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUniversidadesByCnpjIsEqualToSomething() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where cnpj equals to DEFAULT_CNPJ
        defaultUniversidadeShouldBeFound("cnpj.equals=" + DEFAULT_CNPJ);

        // Get all the universidadeList where cnpj equals to UPDATED_CNPJ
        defaultUniversidadeShouldNotBeFound("cnpj.equals=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    void getAllUniversidadesByCnpjIsInShouldWork() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where cnpj in DEFAULT_CNPJ or UPDATED_CNPJ
        defaultUniversidadeShouldBeFound("cnpj.in=" + DEFAULT_CNPJ + "," + UPDATED_CNPJ);

        // Get all the universidadeList where cnpj equals to UPDATED_CNPJ
        defaultUniversidadeShouldNotBeFound("cnpj.in=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    void getAllUniversidadesByCnpjIsNullOrNotNull() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where cnpj is not null
        defaultUniversidadeShouldBeFound("cnpj.specified=true");

        // Get all the universidadeList where cnpj is null
        defaultUniversidadeShouldNotBeFound("cnpj.specified=false");
    }

    @Test
    @Transactional
    void getAllUniversidadesByCnpjContainsSomething() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where cnpj contains DEFAULT_CNPJ
        defaultUniversidadeShouldBeFound("cnpj.contains=" + DEFAULT_CNPJ);

        // Get all the universidadeList where cnpj contains UPDATED_CNPJ
        defaultUniversidadeShouldNotBeFound("cnpj.contains=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    void getAllUniversidadesByCnpjNotContainsSomething() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where cnpj does not contain DEFAULT_CNPJ
        defaultUniversidadeShouldNotBeFound("cnpj.doesNotContain=" + DEFAULT_CNPJ);

        // Get all the universidadeList where cnpj does not contain UPDATED_CNPJ
        defaultUniversidadeShouldBeFound("cnpj.doesNotContain=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    void getAllUniversidadesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where name equals to DEFAULT_NAME
        defaultUniversidadeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the universidadeList where name equals to UPDATED_NAME
        defaultUniversidadeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUniversidadesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUniversidadeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the universidadeList where name equals to UPDATED_NAME
        defaultUniversidadeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUniversidadesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where name is not null
        defaultUniversidadeShouldBeFound("name.specified=true");

        // Get all the universidadeList where name is null
        defaultUniversidadeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllUniversidadesByNameContainsSomething() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where name contains DEFAULT_NAME
        defaultUniversidadeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the universidadeList where name contains UPDATED_NAME
        defaultUniversidadeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUniversidadesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where name does not contain DEFAULT_NAME
        defaultUniversidadeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the universidadeList where name does not contain UPDATED_NAME
        defaultUniversidadeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUniversidadesByCepIsEqualToSomething() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where cep equals to DEFAULT_CEP
        defaultUniversidadeShouldBeFound("cep.equals=" + DEFAULT_CEP);

        // Get all the universidadeList where cep equals to UPDATED_CEP
        defaultUniversidadeShouldNotBeFound("cep.equals=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllUniversidadesByCepIsInShouldWork() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where cep in DEFAULT_CEP or UPDATED_CEP
        defaultUniversidadeShouldBeFound("cep.in=" + DEFAULT_CEP + "," + UPDATED_CEP);

        // Get all the universidadeList where cep equals to UPDATED_CEP
        defaultUniversidadeShouldNotBeFound("cep.in=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllUniversidadesByCepIsNullOrNotNull() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where cep is not null
        defaultUniversidadeShouldBeFound("cep.specified=true");

        // Get all the universidadeList where cep is null
        defaultUniversidadeShouldNotBeFound("cep.specified=false");
    }

    @Test
    @Transactional
    void getAllUniversidadesByCepContainsSomething() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where cep contains DEFAULT_CEP
        defaultUniversidadeShouldBeFound("cep.contains=" + DEFAULT_CEP);

        // Get all the universidadeList where cep contains UPDATED_CEP
        defaultUniversidadeShouldNotBeFound("cep.contains=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllUniversidadesByCepNotContainsSomething() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        // Get all the universidadeList where cep does not contain DEFAULT_CEP
        defaultUniversidadeShouldNotBeFound("cep.doesNotContain=" + DEFAULT_CEP);

        // Get all the universidadeList where cep does not contain UPDATED_CEP
        defaultUniversidadeShouldBeFound("cep.doesNotContain=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllUniversidadesByEspecialistaIsEqualToSomething() throws Exception {
        Especialista especialista;
        if (TestUtil.findAll(em, Especialista.class).isEmpty()) {
            universidadeRepository.saveAndFlush(universidade);
            especialista = EspecialistaResourceIT.createEntity(em);
        } else {
            especialista = TestUtil.findAll(em, Especialista.class).get(0);
        }
        em.persist(especialista);
        em.flush();
        universidade.addEspecialista(especialista);
        universidadeRepository.saveAndFlush(universidade);
        Long especialistaId = especialista.getId();
        // Get all the universidadeList where especialista equals to especialistaId
        defaultUniversidadeShouldBeFound("especialistaId.equals=" + especialistaId);

        // Get all the universidadeList where especialista equals to (especialistaId + 1)
        defaultUniversidadeShouldNotBeFound("especialistaId.equals=" + (especialistaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUniversidadeShouldBeFound(String filter) throws Exception {
        restUniversidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(universidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)));

        // Check, that the count call also returns 1
        restUniversidadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUniversidadeShouldNotBeFound(String filter) throws Exception {
        restUniversidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUniversidadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUniversidade() throws Exception {
        // Get the universidade
        restUniversidadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUniversidade() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        int databaseSizeBeforeUpdate = universidadeRepository.findAll().size();

        // Update the universidade
        Universidade updatedUniversidade = universidadeRepository.findById(universidade.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUniversidade are not directly saved in db
        em.detach(updatedUniversidade);
        updatedUniversidade.cnpj(UPDATED_CNPJ).name(UPDATED_NAME).cep(UPDATED_CEP);
        UniversidadeDto universidadeDto = universidadeMapper.toDto(updatedUniversidade);

        restUniversidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, universidadeDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(universidadeDto))
            )
            .andExpect(status().isOk());

        // Validate the Universidade in the database
        List<Universidade> universidadeList = universidadeRepository.findAll();
        assertThat(universidadeList).hasSize(databaseSizeBeforeUpdate);
        Universidade testUniversidade = universidadeList.get(universidadeList.size() - 1);
        assertThat(testUniversidade.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testUniversidade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUniversidade.getCep()).isEqualTo(UPDATED_CEP);
    }

    @Test
    @Transactional
    void putNonExistingUniversidade() throws Exception {
        int databaseSizeBeforeUpdate = universidadeRepository.findAll().size();
        universidade.setId(longCount.incrementAndGet());

        // Create the Universidade
        UniversidadeDto universidadeDto = universidadeMapper.toDto(universidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniversidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, universidadeDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(universidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Universidade in the database
        List<Universidade> universidadeList = universidadeRepository.findAll();
        assertThat(universidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUniversidade() throws Exception {
        int databaseSizeBeforeUpdate = universidadeRepository.findAll().size();
        universidade.setId(longCount.incrementAndGet());

        // Create the Universidade
        UniversidadeDto universidadeDto = universidadeMapper.toDto(universidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniversidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(universidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Universidade in the database
        List<Universidade> universidadeList = universidadeRepository.findAll();
        assertThat(universidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUniversidade() throws Exception {
        int databaseSizeBeforeUpdate = universidadeRepository.findAll().size();
        universidade.setId(longCount.incrementAndGet());

        // Create the Universidade
        UniversidadeDto universidadeDto = universidadeMapper.toDto(universidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniversidadeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(universidadeDto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Universidade in the database
        List<Universidade> universidadeList = universidadeRepository.findAll();
        assertThat(universidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUniversidadeWithPatch() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        int databaseSizeBeforeUpdate = universidadeRepository.findAll().size();

        // Update the universidade using partial update
        Universidade partialUpdatedUniversidade = new Universidade();
        partialUpdatedUniversidade.setId(universidade.getId());

        partialUpdatedUniversidade.cnpj(UPDATED_CNPJ).name(UPDATED_NAME).cep(UPDATED_CEP);

        restUniversidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUniversidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUniversidade))
            )
            .andExpect(status().isOk());

        // Validate the Universidade in the database
        List<Universidade> universidadeList = universidadeRepository.findAll();
        assertThat(universidadeList).hasSize(databaseSizeBeforeUpdate);
        Universidade testUniversidade = universidadeList.get(universidadeList.size() - 1);
        assertThat(testUniversidade.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testUniversidade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUniversidade.getCep()).isEqualTo(UPDATED_CEP);
    }

    @Test
    @Transactional
    void fullUpdateUniversidadeWithPatch() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        int databaseSizeBeforeUpdate = universidadeRepository.findAll().size();

        // Update the universidade using partial update
        Universidade partialUpdatedUniversidade = new Universidade();
        partialUpdatedUniversidade.setId(universidade.getId());

        partialUpdatedUniversidade.cnpj(UPDATED_CNPJ).name(UPDATED_NAME).cep(UPDATED_CEP);

        restUniversidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUniversidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUniversidade))
            )
            .andExpect(status().isOk());

        // Validate the Universidade in the database
        List<Universidade> universidadeList = universidadeRepository.findAll();
        assertThat(universidadeList).hasSize(databaseSizeBeforeUpdate);
        Universidade testUniversidade = universidadeList.get(universidadeList.size() - 1);
        assertThat(testUniversidade.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testUniversidade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUniversidade.getCep()).isEqualTo(UPDATED_CEP);
    }

    @Test
    @Transactional
    void patchNonExistingUniversidade() throws Exception {
        int databaseSizeBeforeUpdate = universidadeRepository.findAll().size();
        universidade.setId(longCount.incrementAndGet());

        // Create the Universidade
        UniversidadeDto universidadeDto = universidadeMapper.toDto(universidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniversidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, universidadeDto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(universidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Universidade in the database
        List<Universidade> universidadeList = universidadeRepository.findAll();
        assertThat(universidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUniversidade() throws Exception {
        int databaseSizeBeforeUpdate = universidadeRepository.findAll().size();
        universidade.setId(longCount.incrementAndGet());

        // Create the Universidade
        UniversidadeDto universidadeDto = universidadeMapper.toDto(universidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniversidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(universidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Universidade in the database
        List<Universidade> universidadeList = universidadeRepository.findAll();
        assertThat(universidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUniversidade() throws Exception {
        int databaseSizeBeforeUpdate = universidadeRepository.findAll().size();
        universidade.setId(longCount.incrementAndGet());

        // Create the Universidade
        UniversidadeDto universidadeDto = universidadeMapper.toDto(universidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniversidadeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(universidadeDto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Universidade in the database
        List<Universidade> universidadeList = universidadeRepository.findAll();
        assertThat(universidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUniversidade() throws Exception {
        // Initialize the database
        universidadeRepository.saveAndFlush(universidade);

        int databaseSizeBeforeDelete = universidadeRepository.findAll().size();

        // Delete the universidade
        restUniversidadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, universidade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Universidade> universidadeList = universidadeRepository.findAll();
        assertThat(universidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
