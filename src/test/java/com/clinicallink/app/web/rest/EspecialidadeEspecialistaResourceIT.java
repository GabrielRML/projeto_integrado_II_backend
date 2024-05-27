package com.clinicallink.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clinicallink.app.IntegrationTest;
import com.clinicallink.app.domain.Especialidade;
import com.clinicallink.app.domain.EspecialidadeEspecialista;
import com.clinicallink.app.domain.Especialista;
import com.clinicallink.app.repository.EspecialidadeEspecialistaRepository;
import com.clinicallink.app.service.dto.EspecialidadeEspecialistaDto;
import com.clinicallink.app.service.mapper.EspecialidadeEspecialistaMapper;
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
 * Integration tests for the {@link EspecialidadeEspecialistaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EspecialidadeEspecialistaResourceIT {

    private static final String ENTITY_API_URL = "/api/especialidade-especialistas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EspecialidadeEspecialistaRepository especialidadeEspecialistaRepository;

    @Autowired
    private EspecialidadeEspecialistaMapper especialidadeEspecialistaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEspecialidadeEspecialistaMockMvc;

    private EspecialidadeEspecialista especialidadeEspecialista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EspecialidadeEspecialista createEntity(EntityManager em) {
        EspecialidadeEspecialista especialidadeEspecialista = new EspecialidadeEspecialista();
        return especialidadeEspecialista;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EspecialidadeEspecialista createUpdatedEntity(EntityManager em) {
        EspecialidadeEspecialista especialidadeEspecialista = new EspecialidadeEspecialista();
        return especialidadeEspecialista;
    }

    @BeforeEach
    public void initTest() {
        especialidadeEspecialista = createEntity(em);
    }

    @Test
    @Transactional
    void createEspecialidadeEspecialista() throws Exception {
        int databaseSizeBeforeCreate = especialidadeEspecialistaRepository.findAll().size();
        // Create the EspecialidadeEspecialista
        EspecialidadeEspecialistaDto especialidadeEspecialistaDto = especialidadeEspecialistaMapper.toDto(especialidadeEspecialista);
        restEspecialidadeEspecialistaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeEspecialistaDto))
            )
            .andExpect(status().isCreated());

        // Validate the EspecialidadeEspecialista in the database
        List<EspecialidadeEspecialista> especialidadeEspecialistaList = especialidadeEspecialistaRepository.findAll();
        assertThat(especialidadeEspecialistaList).hasSize(databaseSizeBeforeCreate + 1);
        EspecialidadeEspecialista testEspecialidadeEspecialista = especialidadeEspecialistaList.get(
            especialidadeEspecialistaList.size() - 1
        );
    }

    @Test
    @Transactional
    void createEspecialidadeEspecialistaWithExistingId() throws Exception {
        // Create the EspecialidadeEspecialista with an existing ID
        especialidadeEspecialista.setId(1L);
        EspecialidadeEspecialistaDto especialidadeEspecialistaDto = especialidadeEspecialistaMapper.toDto(especialidadeEspecialista);

        int databaseSizeBeforeCreate = especialidadeEspecialistaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspecialidadeEspecialistaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeEspecialistaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the EspecialidadeEspecialista in the database
        List<EspecialidadeEspecialista> especialidadeEspecialistaList = especialidadeEspecialistaRepository.findAll();
        assertThat(especialidadeEspecialistaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEspecialidadeEspecialistas() throws Exception {
        // Initialize the database
        especialidadeEspecialistaRepository.saveAndFlush(especialidadeEspecialista);

        // Get all the especialidadeEspecialistaList
        restEspecialidadeEspecialistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especialidadeEspecialista.getId().intValue())));
    }

    @Test
    @Transactional
    void getEspecialidadeEspecialista() throws Exception {
        // Initialize the database
        especialidadeEspecialistaRepository.saveAndFlush(especialidadeEspecialista);

        // Get the especialidadeEspecialista
        restEspecialidadeEspecialistaMockMvc
            .perform(get(ENTITY_API_URL_ID, especialidadeEspecialista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(especialidadeEspecialista.getId().intValue()));
    }

    @Test
    @Transactional
    void getEspecialidadeEspecialistasByIdFiltering() throws Exception {
        // Initialize the database
        especialidadeEspecialistaRepository.saveAndFlush(especialidadeEspecialista);

        Long id = especialidadeEspecialista.getId();

        defaultEspecialidadeEspecialistaShouldBeFound("id.equals=" + id);
        defaultEspecialidadeEspecialistaShouldNotBeFound("id.notEquals=" + id);

        defaultEspecialidadeEspecialistaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEspecialidadeEspecialistaShouldNotBeFound("id.greaterThan=" + id);

        defaultEspecialidadeEspecialistaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEspecialidadeEspecialistaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEspecialidadeEspecialistasByEspecialidadeIsEqualToSomething() throws Exception {
        Especialidade especialidade;
        if (TestUtil.findAll(em, Especialidade.class).isEmpty()) {
            especialidadeEspecialistaRepository.saveAndFlush(especialidadeEspecialista);
            especialidade = EspecialidadeResourceIT.createEntity(em);
        } else {
            especialidade = TestUtil.findAll(em, Especialidade.class).get(0);
        }
        em.persist(especialidade);
        em.flush();
        especialidadeEspecialista.setEspecialidade(especialidade);
        especialidadeEspecialistaRepository.saveAndFlush(especialidadeEspecialista);
        Long especialidadeId = especialidade.getId();
        // Get all the especialidadeEspecialistaList where especialidade equals to especialidadeId
        defaultEspecialidadeEspecialistaShouldBeFound("especialidadeId.equals=" + especialidadeId);

        // Get all the especialidadeEspecialistaList where especialidade equals to (especialidadeId + 1)
        defaultEspecialidadeEspecialistaShouldNotBeFound("especialidadeId.equals=" + (especialidadeId + 1));
    }

    @Test
    @Transactional
    void getAllEspecialidadeEspecialistasByEspecialistaIsEqualToSomething() throws Exception {
        Especialista especialista;
        if (TestUtil.findAll(em, Especialista.class).isEmpty()) {
            especialidadeEspecialistaRepository.saveAndFlush(especialidadeEspecialista);
            especialista = EspecialistaResourceIT.createEntity(em);
        } else {
            especialista = TestUtil.findAll(em, Especialista.class).get(0);
        }
        em.persist(especialista);
        em.flush();
        especialidadeEspecialista.setEspecialista(especialista);
        especialidadeEspecialistaRepository.saveAndFlush(especialidadeEspecialista);
        Long especialistaId = especialista.getId();
        // Get all the especialidadeEspecialistaList where especialista equals to especialistaId
        defaultEspecialidadeEspecialistaShouldBeFound("especialistaId.equals=" + especialistaId);

        // Get all the especialidadeEspecialistaList where especialista equals to (especialistaId + 1)
        defaultEspecialidadeEspecialistaShouldNotBeFound("especialistaId.equals=" + (especialistaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEspecialidadeEspecialistaShouldBeFound(String filter) throws Exception {
        restEspecialidadeEspecialistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especialidadeEspecialista.getId().intValue())));

        // Check, that the count call also returns 1
        restEspecialidadeEspecialistaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEspecialidadeEspecialistaShouldNotBeFound(String filter) throws Exception {
        restEspecialidadeEspecialistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEspecialidadeEspecialistaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEspecialidadeEspecialista() throws Exception {
        // Get the especialidadeEspecialista
        restEspecialidadeEspecialistaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEspecialidadeEspecialista() throws Exception {
        // Initialize the database
        especialidadeEspecialistaRepository.saveAndFlush(especialidadeEspecialista);

        int databaseSizeBeforeUpdate = especialidadeEspecialistaRepository.findAll().size();

        // Update the especialidadeEspecialista
        EspecialidadeEspecialista updatedEspecialidadeEspecialista = especialidadeEspecialistaRepository
            .findById(especialidadeEspecialista.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedEspecialidadeEspecialista are not directly saved in db
        em.detach(updatedEspecialidadeEspecialista);
        EspecialidadeEspecialistaDto especialidadeEspecialistaDto = especialidadeEspecialistaMapper.toDto(updatedEspecialidadeEspecialista);

        restEspecialidadeEspecialistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, especialidadeEspecialistaDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeEspecialistaDto))
            )
            .andExpect(status().isOk());

        // Validate the EspecialidadeEspecialista in the database
        List<EspecialidadeEspecialista> especialidadeEspecialistaList = especialidadeEspecialistaRepository.findAll();
        assertThat(especialidadeEspecialistaList).hasSize(databaseSizeBeforeUpdate);
        EspecialidadeEspecialista testEspecialidadeEspecialista = especialidadeEspecialistaList.get(
            especialidadeEspecialistaList.size() - 1
        );
    }

    @Test
    @Transactional
    void putNonExistingEspecialidadeEspecialista() throws Exception {
        int databaseSizeBeforeUpdate = especialidadeEspecialistaRepository.findAll().size();
        especialidadeEspecialista.setId(longCount.incrementAndGet());

        // Create the EspecialidadeEspecialista
        EspecialidadeEspecialistaDto especialidadeEspecialistaDto = especialidadeEspecialistaMapper.toDto(especialidadeEspecialista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspecialidadeEspecialistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, especialidadeEspecialistaDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeEspecialistaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the EspecialidadeEspecialista in the database
        List<EspecialidadeEspecialista> especialidadeEspecialistaList = especialidadeEspecialistaRepository.findAll();
        assertThat(especialidadeEspecialistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEspecialidadeEspecialista() throws Exception {
        int databaseSizeBeforeUpdate = especialidadeEspecialistaRepository.findAll().size();
        especialidadeEspecialista.setId(longCount.incrementAndGet());

        // Create the EspecialidadeEspecialista
        EspecialidadeEspecialistaDto especialidadeEspecialistaDto = especialidadeEspecialistaMapper.toDto(especialidadeEspecialista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialidadeEspecialistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeEspecialistaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the EspecialidadeEspecialista in the database
        List<EspecialidadeEspecialista> especialidadeEspecialistaList = especialidadeEspecialistaRepository.findAll();
        assertThat(especialidadeEspecialistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEspecialidadeEspecialista() throws Exception {
        int databaseSizeBeforeUpdate = especialidadeEspecialistaRepository.findAll().size();
        especialidadeEspecialista.setId(longCount.incrementAndGet());

        // Create the EspecialidadeEspecialista
        EspecialidadeEspecialistaDto especialidadeEspecialistaDto = especialidadeEspecialistaMapper.toDto(especialidadeEspecialista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialidadeEspecialistaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeEspecialistaDto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EspecialidadeEspecialista in the database
        List<EspecialidadeEspecialista> especialidadeEspecialistaList = especialidadeEspecialistaRepository.findAll();
        assertThat(especialidadeEspecialistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEspecialidadeEspecialistaWithPatch() throws Exception {
        // Initialize the database
        especialidadeEspecialistaRepository.saveAndFlush(especialidadeEspecialista);

        int databaseSizeBeforeUpdate = especialidadeEspecialistaRepository.findAll().size();

        // Update the especialidadeEspecialista using partial update
        EspecialidadeEspecialista partialUpdatedEspecialidadeEspecialista = new EspecialidadeEspecialista();
        partialUpdatedEspecialidadeEspecialista.setId(especialidadeEspecialista.getId());

        restEspecialidadeEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspecialidadeEspecialista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspecialidadeEspecialista))
            )
            .andExpect(status().isOk());

        // Validate the EspecialidadeEspecialista in the database
        List<EspecialidadeEspecialista> especialidadeEspecialistaList = especialidadeEspecialistaRepository.findAll();
        assertThat(especialidadeEspecialistaList).hasSize(databaseSizeBeforeUpdate);
        EspecialidadeEspecialista testEspecialidadeEspecialista = especialidadeEspecialistaList.get(
            especialidadeEspecialistaList.size() - 1
        );
    }

    @Test
    @Transactional
    void fullUpdateEspecialidadeEspecialistaWithPatch() throws Exception {
        // Initialize the database
        especialidadeEspecialistaRepository.saveAndFlush(especialidadeEspecialista);

        int databaseSizeBeforeUpdate = especialidadeEspecialistaRepository.findAll().size();

        // Update the especialidadeEspecialista using partial update
        EspecialidadeEspecialista partialUpdatedEspecialidadeEspecialista = new EspecialidadeEspecialista();
        partialUpdatedEspecialidadeEspecialista.setId(especialidadeEspecialista.getId());

        restEspecialidadeEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspecialidadeEspecialista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspecialidadeEspecialista))
            )
            .andExpect(status().isOk());

        // Validate the EspecialidadeEspecialista in the database
        List<EspecialidadeEspecialista> especialidadeEspecialistaList = especialidadeEspecialistaRepository.findAll();
        assertThat(especialidadeEspecialistaList).hasSize(databaseSizeBeforeUpdate);
        EspecialidadeEspecialista testEspecialidadeEspecialista = especialidadeEspecialistaList.get(
            especialidadeEspecialistaList.size() - 1
        );
    }

    @Test
    @Transactional
    void patchNonExistingEspecialidadeEspecialista() throws Exception {
        int databaseSizeBeforeUpdate = especialidadeEspecialistaRepository.findAll().size();
        especialidadeEspecialista.setId(longCount.incrementAndGet());

        // Create the EspecialidadeEspecialista
        EspecialidadeEspecialistaDto especialidadeEspecialistaDto = especialidadeEspecialistaMapper.toDto(especialidadeEspecialista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspecialidadeEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, especialidadeEspecialistaDto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeEspecialistaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the EspecialidadeEspecialista in the database
        List<EspecialidadeEspecialista> especialidadeEspecialistaList = especialidadeEspecialistaRepository.findAll();
        assertThat(especialidadeEspecialistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEspecialidadeEspecialista() throws Exception {
        int databaseSizeBeforeUpdate = especialidadeEspecialistaRepository.findAll().size();
        especialidadeEspecialista.setId(longCount.incrementAndGet());

        // Create the EspecialidadeEspecialista
        EspecialidadeEspecialistaDto especialidadeEspecialistaDto = especialidadeEspecialistaMapper.toDto(especialidadeEspecialista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialidadeEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeEspecialistaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the EspecialidadeEspecialista in the database
        List<EspecialidadeEspecialista> especialidadeEspecialistaList = especialidadeEspecialistaRepository.findAll();
        assertThat(especialidadeEspecialistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEspecialidadeEspecialista() throws Exception {
        int databaseSizeBeforeUpdate = especialidadeEspecialistaRepository.findAll().size();
        especialidadeEspecialista.setId(longCount.incrementAndGet());

        // Create the EspecialidadeEspecialista
        EspecialidadeEspecialistaDto especialidadeEspecialistaDto = especialidadeEspecialistaMapper.toDto(especialidadeEspecialista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialidadeEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(especialidadeEspecialistaDto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EspecialidadeEspecialista in the database
        List<EspecialidadeEspecialista> especialidadeEspecialistaList = especialidadeEspecialistaRepository.findAll();
        assertThat(especialidadeEspecialistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEspecialidadeEspecialista() throws Exception {
        // Initialize the database
        especialidadeEspecialistaRepository.saveAndFlush(especialidadeEspecialista);

        int databaseSizeBeforeDelete = especialidadeEspecialistaRepository.findAll().size();

        // Delete the especialidadeEspecialista
        restEspecialidadeEspecialistaMockMvc
            .perform(delete(ENTITY_API_URL_ID, especialidadeEspecialista.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EspecialidadeEspecialista> especialidadeEspecialistaList = especialidadeEspecialistaRepository.findAll();
        assertThat(especialidadeEspecialistaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
