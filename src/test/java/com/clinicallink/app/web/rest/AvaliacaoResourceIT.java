package com.clinicallink.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clinicallink.app.IntegrationTest;
import com.clinicallink.app.domain.Avaliacao;
import com.clinicallink.app.domain.Consulta;
import com.clinicallink.app.domain.Especialista;
import com.clinicallink.app.domain.Usuario;
import com.clinicallink.app.repository.AvaliacaoRepository;
import com.clinicallink.app.service.dto.AvaliacaoDto;
import com.clinicallink.app.service.mapper.AvaliacaoMapper;
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
 * Integration tests for the {@link AvaliacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AvaliacaoResourceIT {

    private static final String DEFAULT_ASSESSMENT = "AAAAAAAAAA";
    private static final String UPDATED_ASSESSMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOTE = 1;
    private static final Integer UPDATED_NOTE = 2;
    private static final Integer SMALLER_NOTE = 1 - 1;

    private static final String ENTITY_API_URL = "/api/avaliacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private AvaliacaoMapper avaliacaoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvaliacaoMockMvc;

    private Avaliacao avaliacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avaliacao createEntity(EntityManager em) {
        Avaliacao avaliacao = new Avaliacao().assessment(DEFAULT_ASSESSMENT).note(DEFAULT_NOTE);
        return avaliacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avaliacao createUpdatedEntity(EntityManager em) {
        Avaliacao avaliacao = new Avaliacao().assessment(UPDATED_ASSESSMENT).note(UPDATED_NOTE);
        return avaliacao;
    }

    @BeforeEach
    public void initTest() {
        avaliacao = createEntity(em);
    }

    @Test
    @Transactional
    void createAvaliacao() throws Exception {
        int databaseSizeBeforeCreate = avaliacaoRepository.findAll().size();
        // Create the Avaliacao
        AvaliacaoDto avaliacaoDto = avaliacaoMapper.toDto(avaliacao);
        restAvaliacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoDto)))
            .andExpect(status().isCreated());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Avaliacao testAvaliacao = avaliacaoList.get(avaliacaoList.size() - 1);
        assertThat(testAvaliacao.getAssessment()).isEqualTo(DEFAULT_ASSESSMENT);
        assertThat(testAvaliacao.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void createAvaliacaoWithExistingId() throws Exception {
        // Create the Avaliacao with an existing ID
        avaliacao.setId(1L);
        AvaliacaoDto avaliacaoDto = avaliacaoMapper.toDto(avaliacao);

        int databaseSizeBeforeCreate = avaliacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvaliacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoDto)))
            .andExpect(status().isBadRequest());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAvaliacaos() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList
        restAvaliacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avaliacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].assessment").value(hasItem(DEFAULT_ASSESSMENT)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @Test
    @Transactional
    void getAvaliacao() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get the avaliacao
        restAvaliacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, avaliacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avaliacao.getId().intValue()))
            .andExpect(jsonPath("$.assessment").value(DEFAULT_ASSESSMENT))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    void getAvaliacaosByIdFiltering() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        Long id = avaliacao.getId();

        defaultAvaliacaoShouldBeFound("id.equals=" + id);
        defaultAvaliacaoShouldNotBeFound("id.notEquals=" + id);

        defaultAvaliacaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAvaliacaoShouldNotBeFound("id.greaterThan=" + id);

        defaultAvaliacaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAvaliacaoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAvaliacaosByAssessmentIsEqualToSomething() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList where assessment equals to DEFAULT_ASSESSMENT
        defaultAvaliacaoShouldBeFound("assessment.equals=" + DEFAULT_ASSESSMENT);

        // Get all the avaliacaoList where assessment equals to UPDATED_ASSESSMENT
        defaultAvaliacaoShouldNotBeFound("assessment.equals=" + UPDATED_ASSESSMENT);
    }

    @Test
    @Transactional
    void getAllAvaliacaosByAssessmentIsInShouldWork() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList where assessment in DEFAULT_ASSESSMENT or UPDATED_ASSESSMENT
        defaultAvaliacaoShouldBeFound("assessment.in=" + DEFAULT_ASSESSMENT + "," + UPDATED_ASSESSMENT);

        // Get all the avaliacaoList where assessment equals to UPDATED_ASSESSMENT
        defaultAvaliacaoShouldNotBeFound("assessment.in=" + UPDATED_ASSESSMENT);
    }

    @Test
    @Transactional
    void getAllAvaliacaosByAssessmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList where assessment is not null
        defaultAvaliacaoShouldBeFound("assessment.specified=true");

        // Get all the avaliacaoList where assessment is null
        defaultAvaliacaoShouldNotBeFound("assessment.specified=false");
    }

    @Test
    @Transactional
    void getAllAvaliacaosByAssessmentContainsSomething() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList where assessment contains DEFAULT_ASSESSMENT
        defaultAvaliacaoShouldBeFound("assessment.contains=" + DEFAULT_ASSESSMENT);

        // Get all the avaliacaoList where assessment contains UPDATED_ASSESSMENT
        defaultAvaliacaoShouldNotBeFound("assessment.contains=" + UPDATED_ASSESSMENT);
    }

    @Test
    @Transactional
    void getAllAvaliacaosByAssessmentNotContainsSomething() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList where assessment does not contain DEFAULT_ASSESSMENT
        defaultAvaliacaoShouldNotBeFound("assessment.doesNotContain=" + DEFAULT_ASSESSMENT);

        // Get all the avaliacaoList where assessment does not contain UPDATED_ASSESSMENT
        defaultAvaliacaoShouldBeFound("assessment.doesNotContain=" + UPDATED_ASSESSMENT);
    }

    @Test
    @Transactional
    void getAllAvaliacaosByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList where note equals to DEFAULT_NOTE
        defaultAvaliacaoShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the avaliacaoList where note equals to UPDATED_NOTE
        defaultAvaliacaoShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    void getAllAvaliacaosByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultAvaliacaoShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the avaliacaoList where note equals to UPDATED_NOTE
        defaultAvaliacaoShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    void getAllAvaliacaosByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList where note is not null
        defaultAvaliacaoShouldBeFound("note.specified=true");

        // Get all the avaliacaoList where note is null
        defaultAvaliacaoShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    void getAllAvaliacaosByNoteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList where note is greater than or equal to DEFAULT_NOTE
        defaultAvaliacaoShouldBeFound("note.greaterThanOrEqual=" + DEFAULT_NOTE);

        // Get all the avaliacaoList where note is greater than or equal to UPDATED_NOTE
        defaultAvaliacaoShouldNotBeFound("note.greaterThanOrEqual=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    void getAllAvaliacaosByNoteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList where note is less than or equal to DEFAULT_NOTE
        defaultAvaliacaoShouldBeFound("note.lessThanOrEqual=" + DEFAULT_NOTE);

        // Get all the avaliacaoList where note is less than or equal to SMALLER_NOTE
        defaultAvaliacaoShouldNotBeFound("note.lessThanOrEqual=" + SMALLER_NOTE);
    }

    @Test
    @Transactional
    void getAllAvaliacaosByNoteIsLessThanSomething() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList where note is less than DEFAULT_NOTE
        defaultAvaliacaoShouldNotBeFound("note.lessThan=" + DEFAULT_NOTE);

        // Get all the avaliacaoList where note is less than UPDATED_NOTE
        defaultAvaliacaoShouldBeFound("note.lessThan=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    void getAllAvaliacaosByNoteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList where note is greater than DEFAULT_NOTE
        defaultAvaliacaoShouldNotBeFound("note.greaterThan=" + DEFAULT_NOTE);

        // Get all the avaliacaoList where note is greater than SMALLER_NOTE
        defaultAvaliacaoShouldBeFound("note.greaterThan=" + SMALLER_NOTE);
    }

    @Test
    @Transactional
    void getAllAvaliacaosByConsultaIsEqualToSomething() throws Exception {
        Consulta consulta;
        if (TestUtil.findAll(em, Consulta.class).isEmpty()) {
            avaliacaoRepository.saveAndFlush(avaliacao);
            consulta = ConsultaResourceIT.createEntity(em);
        } else {
            consulta = TestUtil.findAll(em, Consulta.class).get(0);
        }
        em.persist(consulta);
        em.flush();
        avaliacao.setConsulta(consulta);
        consulta.setAvaliacao(avaliacao);
        avaliacaoRepository.saveAndFlush(avaliacao);
        Long consultaId = consulta.getId();
        // Get all the avaliacaoList where consulta equals to consultaId
        defaultAvaliacaoShouldBeFound("consultaId.equals=" + consultaId);

        // Get all the avaliacaoList where consulta equals to (consultaId + 1)
        defaultAvaliacaoShouldNotBeFound("consultaId.equals=" + (consultaId + 1));
    }

    @Test
    @Transactional
    void getAllAvaliacaosByAvaliadoIsEqualToSomething() throws Exception {
        Especialista avaliado;
        if (TestUtil.findAll(em, Especialista.class).isEmpty()) {
            avaliacaoRepository.saveAndFlush(avaliacao);
            avaliado = EspecialistaResourceIT.createEntity(em);
        } else {
            avaliado = TestUtil.findAll(em, Especialista.class).get(0);
        }
        em.persist(avaliado);
        em.flush();
        avaliacao.setAvaliado(avaliado);
        avaliacaoRepository.saveAndFlush(avaliacao);
        Long avaliadoId = avaliado.getId();
        // Get all the avaliacaoList where avaliado equals to avaliadoId
        defaultAvaliacaoShouldBeFound("avaliadoId.equals=" + avaliadoId);

        // Get all the avaliacaoList where avaliado equals to (avaliadoId + 1)
        defaultAvaliacaoShouldNotBeFound("avaliadoId.equals=" + (avaliadoId + 1));
    }

    @Test
    @Transactional
    void getAllAvaliacaosByAvaliadorIsEqualToSomething() throws Exception {
        Usuario avaliador;
        if (TestUtil.findAll(em, Usuario.class).isEmpty()) {
            avaliacaoRepository.saveAndFlush(avaliacao);
            avaliador = UsuarioResourceIT.createEntity(em);
        } else {
            avaliador = TestUtil.findAll(em, Usuario.class).get(0);
        }
        em.persist(avaliador);
        em.flush();
        avaliacao.setAvaliador(avaliador);
        avaliacaoRepository.saveAndFlush(avaliacao);
        Long avaliadorId = avaliador.getId();
        // Get all the avaliacaoList where avaliador equals to avaliadorId
        defaultAvaliacaoShouldBeFound("avaliadorId.equals=" + avaliadorId);

        // Get all the avaliacaoList where avaliador equals to (avaliadorId + 1)
        defaultAvaliacaoShouldNotBeFound("avaliadorId.equals=" + (avaliadorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAvaliacaoShouldBeFound(String filter) throws Exception {
        restAvaliacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avaliacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].assessment").value(hasItem(DEFAULT_ASSESSMENT)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));

        // Check, that the count call also returns 1
        restAvaliacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAvaliacaoShouldNotBeFound(String filter) throws Exception {
        restAvaliacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAvaliacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAvaliacao() throws Exception {
        // Get the avaliacao
        restAvaliacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAvaliacao() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();

        // Update the avaliacao
        Avaliacao updatedAvaliacao = avaliacaoRepository.findById(avaliacao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAvaliacao are not directly saved in db
        em.detach(updatedAvaliacao);
        updatedAvaliacao.assessment(UPDATED_ASSESSMENT).note(UPDATED_NOTE);
        AvaliacaoDto avaliacaoDto = avaliacaoMapper.toDto(updatedAvaliacao);

        restAvaliacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avaliacaoDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avaliacaoDto))
            )
            .andExpect(status().isOk());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
        Avaliacao testAvaliacao = avaliacaoList.get(avaliacaoList.size() - 1);
        assertThat(testAvaliacao.getAssessment()).isEqualTo(UPDATED_ASSESSMENT);
        assertThat(testAvaliacao.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void putNonExistingAvaliacao() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();
        avaliacao.setId(longCount.incrementAndGet());

        // Create the Avaliacao
        AvaliacaoDto avaliacaoDto = avaliacaoMapper.toDto(avaliacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvaliacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avaliacaoDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avaliacaoDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAvaliacao() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();
        avaliacao.setId(longCount.incrementAndGet());

        // Create the Avaliacao
        AvaliacaoDto avaliacaoDto = avaliacaoMapper.toDto(avaliacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avaliacaoDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAvaliacao() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();
        avaliacao.setId(longCount.incrementAndGet());

        // Create the Avaliacao
        AvaliacaoDto avaliacaoDto = avaliacaoMapper.toDto(avaliacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacaoDto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAvaliacaoWithPatch() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();

        // Update the avaliacao using partial update
        Avaliacao partialUpdatedAvaliacao = new Avaliacao();
        partialUpdatedAvaliacao.setId(avaliacao.getId());

        partialUpdatedAvaliacao.note(UPDATED_NOTE);

        restAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvaliacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvaliacao))
            )
            .andExpect(status().isOk());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
        Avaliacao testAvaliacao = avaliacaoList.get(avaliacaoList.size() - 1);
        assertThat(testAvaliacao.getAssessment()).isEqualTo(DEFAULT_ASSESSMENT);
        assertThat(testAvaliacao.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void fullUpdateAvaliacaoWithPatch() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();

        // Update the avaliacao using partial update
        Avaliacao partialUpdatedAvaliacao = new Avaliacao();
        partialUpdatedAvaliacao.setId(avaliacao.getId());

        partialUpdatedAvaliacao.assessment(UPDATED_ASSESSMENT).note(UPDATED_NOTE);

        restAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvaliacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvaliacao))
            )
            .andExpect(status().isOk());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
        Avaliacao testAvaliacao = avaliacaoList.get(avaliacaoList.size() - 1);
        assertThat(testAvaliacao.getAssessment()).isEqualTo(UPDATED_ASSESSMENT);
        assertThat(testAvaliacao.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void patchNonExistingAvaliacao() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();
        avaliacao.setId(longCount.incrementAndGet());

        // Create the Avaliacao
        AvaliacaoDto avaliacaoDto = avaliacaoMapper.toDto(avaliacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, avaliacaoDto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avaliacaoDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAvaliacao() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();
        avaliacao.setId(longCount.incrementAndGet());

        // Create the Avaliacao
        AvaliacaoDto avaliacaoDto = avaliacaoMapper.toDto(avaliacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avaliacaoDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAvaliacao() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();
        avaliacao.setId(longCount.incrementAndGet());

        // Create the Avaliacao
        AvaliacaoDto avaliacaoDto = avaliacaoMapper.toDto(avaliacao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(avaliacaoDto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAvaliacao() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        int databaseSizeBeforeDelete = avaliacaoRepository.findAll().size();

        // Delete the avaliacao
        restAvaliacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, avaliacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
