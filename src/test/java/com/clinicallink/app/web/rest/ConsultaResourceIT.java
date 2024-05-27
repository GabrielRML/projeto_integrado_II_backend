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
import com.clinicallink.app.domain.enumeration.StatusConsulta;
import com.clinicallink.app.repository.ConsultaRepository;
import com.clinicallink.app.service.dto.ConsultaDto;
import com.clinicallink.app.service.mapper.ConsultaMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ConsultaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConsultaResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final StatusConsulta DEFAULT_STATUS = StatusConsulta.CONFIRMADA;
    private static final StatusConsulta UPDATED_STATUS = StatusConsulta.CONCLUIDA;

    private static final String ENTITY_API_URL = "/api/consultas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaMapper consultaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsultaMockMvc;

    private Consulta consulta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consulta createEntity(EntityManager em) {
        Consulta consulta = new Consulta().date(DEFAULT_DATE).reason(DEFAULT_REASON).link(DEFAULT_LINK).status(DEFAULT_STATUS);
        return consulta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consulta createUpdatedEntity(EntityManager em) {
        Consulta consulta = new Consulta().date(UPDATED_DATE).reason(UPDATED_REASON).link(UPDATED_LINK).status(UPDATED_STATUS);
        return consulta;
    }

    @BeforeEach
    public void initTest() {
        consulta = createEntity(em);
    }

    @Test
    @Transactional
    void createConsulta() throws Exception {
        int databaseSizeBeforeCreate = consultaRepository.findAll().size();
        // Create the Consulta
        ConsultaDto consultaDto = consultaMapper.toDto(consulta);
        restConsultaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultaDto)))
            .andExpect(status().isCreated());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeCreate + 1);
        Consulta testConsulta = consultaList.get(consultaList.size() - 1);
        assertThat(testConsulta.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testConsulta.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testConsulta.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testConsulta.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createConsultaWithExistingId() throws Exception {
        // Create the Consulta with an existing ID
        consulta.setId(1L);
        ConsultaDto consultaDto = consultaMapper.toDto(consulta);

        int databaseSizeBeforeCreate = consultaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultaDto)))
            .andExpect(status().isBadRequest());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConsultas() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList
        restConsultaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consulta.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getConsulta() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get the consulta
        restConsultaMockMvc
            .perform(get(ENTITY_API_URL_ID, consulta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consulta.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getConsultasByIdFiltering() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        Long id = consulta.getId();

        defaultConsultaShouldBeFound("id.equals=" + id);
        defaultConsultaShouldNotBeFound("id.notEquals=" + id);

        defaultConsultaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConsultaShouldNotBeFound("id.greaterThan=" + id);

        defaultConsultaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConsultaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConsultasByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where date equals to DEFAULT_DATE
        defaultConsultaShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the consultaList where date equals to UPDATED_DATE
        defaultConsultaShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllConsultasByDateIsInShouldWork() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where date in DEFAULT_DATE or UPDATED_DATE
        defaultConsultaShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the consultaList where date equals to UPDATED_DATE
        defaultConsultaShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllConsultasByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where date is not null
        defaultConsultaShouldBeFound("date.specified=true");

        // Get all the consultaList where date is null
        defaultConsultaShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllConsultasByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where reason equals to DEFAULT_REASON
        defaultConsultaShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the consultaList where reason equals to UPDATED_REASON
        defaultConsultaShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllConsultasByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultConsultaShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the consultaList where reason equals to UPDATED_REASON
        defaultConsultaShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllConsultasByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where reason is not null
        defaultConsultaShouldBeFound("reason.specified=true");

        // Get all the consultaList where reason is null
        defaultConsultaShouldNotBeFound("reason.specified=false");
    }

    @Test
    @Transactional
    void getAllConsultasByReasonContainsSomething() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where reason contains DEFAULT_REASON
        defaultConsultaShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the consultaList where reason contains UPDATED_REASON
        defaultConsultaShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllConsultasByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where reason does not contain DEFAULT_REASON
        defaultConsultaShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the consultaList where reason does not contain UPDATED_REASON
        defaultConsultaShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllConsultasByLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where link equals to DEFAULT_LINK
        defaultConsultaShouldBeFound("link.equals=" + DEFAULT_LINK);

        // Get all the consultaList where link equals to UPDATED_LINK
        defaultConsultaShouldNotBeFound("link.equals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllConsultasByLinkIsInShouldWork() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where link in DEFAULT_LINK or UPDATED_LINK
        defaultConsultaShouldBeFound("link.in=" + DEFAULT_LINK + "," + UPDATED_LINK);

        // Get all the consultaList where link equals to UPDATED_LINK
        defaultConsultaShouldNotBeFound("link.in=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllConsultasByLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where link is not null
        defaultConsultaShouldBeFound("link.specified=true");

        // Get all the consultaList where link is null
        defaultConsultaShouldNotBeFound("link.specified=false");
    }

    @Test
    @Transactional
    void getAllConsultasByLinkContainsSomething() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where link contains DEFAULT_LINK
        defaultConsultaShouldBeFound("link.contains=" + DEFAULT_LINK);

        // Get all the consultaList where link contains UPDATED_LINK
        defaultConsultaShouldNotBeFound("link.contains=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllConsultasByLinkNotContainsSomething() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where link does not contain DEFAULT_LINK
        defaultConsultaShouldNotBeFound("link.doesNotContain=" + DEFAULT_LINK);

        // Get all the consultaList where link does not contain UPDATED_LINK
        defaultConsultaShouldBeFound("link.doesNotContain=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllConsultasByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where status equals to DEFAULT_STATUS
        defaultConsultaShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the consultaList where status equals to UPDATED_STATUS
        defaultConsultaShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllConsultasByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultConsultaShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the consultaList where status equals to UPDATED_STATUS
        defaultConsultaShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllConsultasByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList where status is not null
        defaultConsultaShouldBeFound("status.specified=true");

        // Get all the consultaList where status is null
        defaultConsultaShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllConsultasByAvaliacaoIsEqualToSomething() throws Exception {
        Avaliacao avaliacao;
        if (TestUtil.findAll(em, Avaliacao.class).isEmpty()) {
            consultaRepository.saveAndFlush(consulta);
            avaliacao = AvaliacaoResourceIT.createEntity(em);
        } else {
            avaliacao = TestUtil.findAll(em, Avaliacao.class).get(0);
        }
        em.persist(avaliacao);
        em.flush();
        consulta.setAvaliacao(avaliacao);
        consultaRepository.saveAndFlush(consulta);
        Long avaliacaoId = avaliacao.getId();
        // Get all the consultaList where avaliacao equals to avaliacaoId
        defaultConsultaShouldBeFound("avaliacaoId.equals=" + avaliacaoId);

        // Get all the consultaList where avaliacao equals to (avaliacaoId + 1)
        defaultConsultaShouldNotBeFound("avaliacaoId.equals=" + (avaliacaoId + 1));
    }

    @Test
    @Transactional
    void getAllConsultasByPrestadorIsEqualToSomething() throws Exception {
        Especialista prestador;
        if (TestUtil.findAll(em, Especialista.class).isEmpty()) {
            consultaRepository.saveAndFlush(consulta);
            prestador = EspecialistaResourceIT.createEntity(em);
        } else {
            prestador = TestUtil.findAll(em, Especialista.class).get(0);
        }
        em.persist(prestador);
        em.flush();
        consulta.setPrestador(prestador);
        consultaRepository.saveAndFlush(consulta);
        Long prestadorId = prestador.getId();
        // Get all the consultaList where prestador equals to prestadorId
        defaultConsultaShouldBeFound("prestadorId.equals=" + prestadorId);

        // Get all the consultaList where prestador equals to (prestadorId + 1)
        defaultConsultaShouldNotBeFound("prestadorId.equals=" + (prestadorId + 1));
    }

    @Test
    @Transactional
    void getAllConsultasByClienteIsEqualToSomething() throws Exception {
        Usuario cliente;
        if (TestUtil.findAll(em, Usuario.class).isEmpty()) {
            consultaRepository.saveAndFlush(consulta);
            cliente = UsuarioResourceIT.createEntity(em);
        } else {
            cliente = TestUtil.findAll(em, Usuario.class).get(0);
        }
        em.persist(cliente);
        em.flush();
        consulta.setCliente(cliente);
        consultaRepository.saveAndFlush(consulta);
        Long clienteId = cliente.getId();
        // Get all the consultaList where cliente equals to clienteId
        defaultConsultaShouldBeFound("clienteId.equals=" + clienteId);

        // Get all the consultaList where cliente equals to (clienteId + 1)
        defaultConsultaShouldNotBeFound("clienteId.equals=" + (clienteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConsultaShouldBeFound(String filter) throws Exception {
        restConsultaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consulta.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restConsultaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConsultaShouldNotBeFound(String filter) throws Exception {
        restConsultaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConsultaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConsulta() throws Exception {
        // Get the consulta
        restConsultaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConsulta() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();

        // Update the consulta
        Consulta updatedConsulta = consultaRepository.findById(consulta.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedConsulta are not directly saved in db
        em.detach(updatedConsulta);
        updatedConsulta.date(UPDATED_DATE).reason(UPDATED_REASON).link(UPDATED_LINK).status(UPDATED_STATUS);
        ConsultaDto consultaDto = consultaMapper.toDto(updatedConsulta);

        restConsultaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consultaDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultaDto))
            )
            .andExpect(status().isOk());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeUpdate);
        Consulta testConsulta = consultaList.get(consultaList.size() - 1);
        assertThat(testConsulta.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testConsulta.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testConsulta.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testConsulta.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingConsulta() throws Exception {
        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();
        consulta.setId(longCount.incrementAndGet());

        // Create the Consulta
        ConsultaDto consultaDto = consultaMapper.toDto(consulta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consultaDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConsulta() throws Exception {
        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();
        consulta.setId(longCount.incrementAndGet());

        // Create the Consulta
        ConsultaDto consultaDto = consultaMapper.toDto(consulta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConsulta() throws Exception {
        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();
        consulta.setId(longCount.incrementAndGet());

        // Create the Consulta
        ConsultaDto consultaDto = consultaMapper.toDto(consulta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultaDto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConsultaWithPatch() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();

        // Update the consulta using partial update
        Consulta partialUpdatedConsulta = new Consulta();
        partialUpdatedConsulta.setId(consulta.getId());

        partialUpdatedConsulta.reason(UPDATED_REASON).status(UPDATED_STATUS);

        restConsultaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsulta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsulta))
            )
            .andExpect(status().isOk());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeUpdate);
        Consulta testConsulta = consultaList.get(consultaList.size() - 1);
        assertThat(testConsulta.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testConsulta.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testConsulta.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testConsulta.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateConsultaWithPatch() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();

        // Update the consulta using partial update
        Consulta partialUpdatedConsulta = new Consulta();
        partialUpdatedConsulta.setId(consulta.getId());

        partialUpdatedConsulta.date(UPDATED_DATE).reason(UPDATED_REASON).link(UPDATED_LINK).status(UPDATED_STATUS);

        restConsultaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsulta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsulta))
            )
            .andExpect(status().isOk());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeUpdate);
        Consulta testConsulta = consultaList.get(consultaList.size() - 1);
        assertThat(testConsulta.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testConsulta.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testConsulta.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testConsulta.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingConsulta() throws Exception {
        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();
        consulta.setId(longCount.incrementAndGet());

        // Create the Consulta
        ConsultaDto consultaDto = consultaMapper.toDto(consulta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, consultaDto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consultaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConsulta() throws Exception {
        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();
        consulta.setId(longCount.incrementAndGet());

        // Create the Consulta
        ConsultaDto consultaDto = consultaMapper.toDto(consulta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consultaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConsulta() throws Exception {
        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();
        consulta.setId(longCount.incrementAndGet());

        // Create the Consulta
        ConsultaDto consultaDto = consultaMapper.toDto(consulta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(consultaDto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConsulta() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        int databaseSizeBeforeDelete = consultaRepository.findAll().size();

        // Delete the consulta
        restConsultaMockMvc
            .perform(delete(ENTITY_API_URL_ID, consulta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
