package com.clinicallink.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clinicallink.app.IntegrationTest;
import com.clinicallink.app.domain.Cidade;
import com.clinicallink.app.domain.Especialista;
import com.clinicallink.app.domain.Estado;
import com.clinicallink.app.domain.Usuario;
import com.clinicallink.app.repository.CidadeRepository;
import com.clinicallink.app.service.dto.CidadeDto;
import com.clinicallink.app.service.mapper.CidadeMapper;
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
 * Integration tests for the {@link CidadeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CidadeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeMapper cidadeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCidadeMockMvc;

    private Cidade cidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cidade createEntity(EntityManager em) {
        Cidade cidade = new Cidade().nome(DEFAULT_NOME).sigla(DEFAULT_SIGLA);
        return cidade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cidade createUpdatedEntity(EntityManager em) {
        Cidade cidade = new Cidade().nome(UPDATED_NOME).sigla(UPDATED_SIGLA);
        return cidade;
    }

    @BeforeEach
    public void initTest() {
        cidade = createEntity(em);
    }

    @Test
    @Transactional
    void createCidade() throws Exception {
        int databaseSizeBeforeCreate = cidadeRepository.findAll().size();
        // Create the Cidade
        CidadeDto cidadeDto = cidadeMapper.toDto(cidade);
        restCidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cidadeDto)))
            .andExpect(status().isCreated());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Cidade testCidade = cidadeList.get(cidadeList.size() - 1);
        assertThat(testCidade.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCidade.getSigla()).isEqualTo(DEFAULT_SIGLA);
    }

    @Test
    @Transactional
    void createCidadeWithExistingId() throws Exception {
        // Create the Cidade with an existing ID
        cidade.setId(1L);
        CidadeDto cidadeDto = cidadeMapper.toDto(cidade);

        int databaseSizeBeforeCreate = cidadeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cidadeDto)))
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCidades() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }

    @Test
    @Transactional
    void getCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get the cidade
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL_ID, cidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cidade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA));
    }

    @Test
    @Transactional
    void getCidadesByIdFiltering() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        Long id = cidade.getId();

        defaultCidadeShouldBeFound("id.equals=" + id);
        defaultCidadeShouldNotBeFound("id.notEquals=" + id);

        defaultCidadeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCidadeShouldNotBeFound("id.greaterThan=" + id);

        defaultCidadeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCidadeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCidadesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where nome equals to DEFAULT_NOME
        defaultCidadeShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the cidadeList where nome equals to UPDATED_NOME
        defaultCidadeShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCidadesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultCidadeShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the cidadeList where nome equals to UPDATED_NOME
        defaultCidadeShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCidadesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where nome is not null
        defaultCidadeShouldBeFound("nome.specified=true");

        // Get all the cidadeList where nome is null
        defaultCidadeShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllCidadesByNomeContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where nome contains DEFAULT_NOME
        defaultCidadeShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the cidadeList where nome contains UPDATED_NOME
        defaultCidadeShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCidadesByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where nome does not contain DEFAULT_NOME
        defaultCidadeShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the cidadeList where nome does not contain UPDATED_NOME
        defaultCidadeShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCidadesBySiglaIsEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where sigla equals to DEFAULT_SIGLA
        defaultCidadeShouldBeFound("sigla.equals=" + DEFAULT_SIGLA);

        // Get all the cidadeList where sigla equals to UPDATED_SIGLA
        defaultCidadeShouldNotBeFound("sigla.equals=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    void getAllCidadesBySiglaIsInShouldWork() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where sigla in DEFAULT_SIGLA or UPDATED_SIGLA
        defaultCidadeShouldBeFound("sigla.in=" + DEFAULT_SIGLA + "," + UPDATED_SIGLA);

        // Get all the cidadeList where sigla equals to UPDATED_SIGLA
        defaultCidadeShouldNotBeFound("sigla.in=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    void getAllCidadesBySiglaIsNullOrNotNull() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where sigla is not null
        defaultCidadeShouldBeFound("sigla.specified=true");

        // Get all the cidadeList where sigla is null
        defaultCidadeShouldNotBeFound("sigla.specified=false");
    }

    @Test
    @Transactional
    void getAllCidadesBySiglaContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where sigla contains DEFAULT_SIGLA
        defaultCidadeShouldBeFound("sigla.contains=" + DEFAULT_SIGLA);

        // Get all the cidadeList where sigla contains UPDATED_SIGLA
        defaultCidadeShouldNotBeFound("sigla.contains=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    void getAllCidadesBySiglaNotContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where sigla does not contain DEFAULT_SIGLA
        defaultCidadeShouldNotBeFound("sigla.doesNotContain=" + DEFAULT_SIGLA);

        // Get all the cidadeList where sigla does not contain UPDATED_SIGLA
        defaultCidadeShouldBeFound("sigla.doesNotContain=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    void getAllCidadesByUsuarioIsEqualToSomething() throws Exception {
        Usuario usuario;
        if (TestUtil.findAll(em, Usuario.class).isEmpty()) {
            cidadeRepository.saveAndFlush(cidade);
            usuario = UsuarioResourceIT.createEntity(em);
        } else {
            usuario = TestUtil.findAll(em, Usuario.class).get(0);
        }
        em.persist(usuario);
        em.flush();
        cidade.addUsuario(usuario);
        cidadeRepository.saveAndFlush(cidade);
        Long usuarioId = usuario.getId();
        // Get all the cidadeList where usuario equals to usuarioId
        defaultCidadeShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the cidadeList where usuario equals to (usuarioId + 1)
        defaultCidadeShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    @Test
    @Transactional
    void getAllCidadesByEspecialistaIsEqualToSomething() throws Exception {
        Especialista especialista;
        if (TestUtil.findAll(em, Especialista.class).isEmpty()) {
            cidadeRepository.saveAndFlush(cidade);
            especialista = EspecialistaResourceIT.createEntity(em);
        } else {
            especialista = TestUtil.findAll(em, Especialista.class).get(0);
        }
        em.persist(especialista);
        em.flush();
        cidade.addEspecialista(especialista);
        cidadeRepository.saveAndFlush(cidade);
        Long especialistaId = especialista.getId();
        // Get all the cidadeList where especialista equals to especialistaId
        defaultCidadeShouldBeFound("especialistaId.equals=" + especialistaId);

        // Get all the cidadeList where especialista equals to (especialistaId + 1)
        defaultCidadeShouldNotBeFound("especialistaId.equals=" + (especialistaId + 1));
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoIsEqualToSomething() throws Exception {
        Estado estado;
        if (TestUtil.findAll(em, Estado.class).isEmpty()) {
            cidadeRepository.saveAndFlush(cidade);
            estado = EstadoResourceIT.createEntity(em);
        } else {
            estado = TestUtil.findAll(em, Estado.class).get(0);
        }
        em.persist(estado);
        em.flush();
        cidade.setEstado(estado);
        cidadeRepository.saveAndFlush(cidade);
        Long estadoId = estado.getId();
        // Get all the cidadeList where estado equals to estadoId
        defaultCidadeShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the cidadeList where estado equals to (estadoId + 1)
        defaultCidadeShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCidadeShouldBeFound(String filter) throws Exception {
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));

        // Check, that the count call also returns 1
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCidadeShouldNotBeFound(String filter) throws Exception {
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCidade() throws Exception {
        // Get the cidade
        restCidadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();

        // Update the cidade
        Cidade updatedCidade = cidadeRepository.findById(cidade.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCidade are not directly saved in db
        em.detach(updatedCidade);
        updatedCidade.nome(UPDATED_NOME).sigla(UPDATED_SIGLA);
        CidadeDto cidadeDto = cidadeMapper.toDto(updatedCidade);

        restCidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cidadeDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cidadeDto))
            )
            .andExpect(status().isOk());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
        Cidade testCidade = cidadeList.get(cidadeList.size() - 1);
        assertThat(testCidade.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCidade.getSigla()).isEqualTo(UPDATED_SIGLA);
    }

    @Test
    @Transactional
    void putNonExistingCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();
        cidade.setId(longCount.incrementAndGet());

        // Create the Cidade
        CidadeDto cidadeDto = cidadeMapper.toDto(cidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cidadeDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();
        cidade.setId(longCount.incrementAndGet());

        // Create the Cidade
        CidadeDto cidadeDto = cidadeMapper.toDto(cidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();
        cidade.setId(longCount.incrementAndGet());

        // Create the Cidade
        CidadeDto cidadeDto = cidadeMapper.toDto(cidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cidadeDto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCidadeWithPatch() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();

        // Update the cidade using partial update
        Cidade partialUpdatedCidade = new Cidade();
        partialUpdatedCidade.setId(cidade.getId());

        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCidade))
            )
            .andExpect(status().isOk());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
        Cidade testCidade = cidadeList.get(cidadeList.size() - 1);
        assertThat(testCidade.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCidade.getSigla()).isEqualTo(DEFAULT_SIGLA);
    }

    @Test
    @Transactional
    void fullUpdateCidadeWithPatch() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();

        // Update the cidade using partial update
        Cidade partialUpdatedCidade = new Cidade();
        partialUpdatedCidade.setId(cidade.getId());

        partialUpdatedCidade.nome(UPDATED_NOME).sigla(UPDATED_SIGLA);

        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCidade))
            )
            .andExpect(status().isOk());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
        Cidade testCidade = cidadeList.get(cidadeList.size() - 1);
        assertThat(testCidade.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCidade.getSigla()).isEqualTo(UPDATED_SIGLA);
    }

    @Test
    @Transactional
    void patchNonExistingCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();
        cidade.setId(longCount.incrementAndGet());

        // Create the Cidade
        CidadeDto cidadeDto = cidadeMapper.toDto(cidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cidadeDto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();
        cidade.setId(longCount.incrementAndGet());

        // Create the Cidade
        CidadeDto cidadeDto = cidadeMapper.toDto(cidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cidadeDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();
        cidade.setId(longCount.incrementAndGet());

        // Create the Cidade
        CidadeDto cidadeDto = cidadeMapper.toDto(cidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cidadeDto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        int databaseSizeBeforeDelete = cidadeRepository.findAll().size();

        // Delete the cidade
        restCidadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, cidade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
