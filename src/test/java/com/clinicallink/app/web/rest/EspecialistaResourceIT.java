package com.clinicallink.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clinicallink.app.IntegrationTest;
import com.clinicallink.app.domain.Avaliacao;
import com.clinicallink.app.domain.Cidade;
import com.clinicallink.app.domain.Consulta;
import com.clinicallink.app.domain.EspecialidadeEspecialista;
import com.clinicallink.app.domain.Especialista;
import com.clinicallink.app.domain.Especialista;
import com.clinicallink.app.domain.Estado;
import com.clinicallink.app.domain.Universidade;
import com.clinicallink.app.domain.User;
import com.clinicallink.app.domain.enumeration.SpecialistType;
import com.clinicallink.app.repository.EspecialistaRepository;
import com.clinicallink.app.service.dto.EspecialistaDto;
import com.clinicallink.app.service.mapper.EspecialistaMapper;
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
 * Integration tests for the {@link EspecialistaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EspecialistaResourceIT {

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    private static final Integer DEFAULT_TIME_SESSION = 1;
    private static final Integer UPDATED_TIME_SESSION = 2;
    private static final Integer SMALLER_TIME_SESSION = 1 - 1;

    private static final SpecialistType DEFAULT_SPECIALIST_TYPE = SpecialistType.SUPERVISOR;
    private static final SpecialistType UPDATED_SPECIALIST_TYPE = SpecialistType.PSICOLOGO;

    private static final String ENTITY_API_URL = "/api/especialistas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EspecialistaRepository especialistaRepository;

    @Autowired
    private EspecialistaMapper especialistaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEspecialistaMockMvc;

    private Especialista especialista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Especialista createEntity(EntityManager em) {
        Especialista especialista = new Especialista()
            .cpf(DEFAULT_CPF)
            .identification(DEFAULT_IDENTIFICATION)
            .birthDate(DEFAULT_BIRTH_DATE)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .timeSession(DEFAULT_TIME_SESSION)
            .specialistType(DEFAULT_SPECIALIST_TYPE);
        return especialista;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Especialista createUpdatedEntity(EntityManager em) {
        Especialista especialista = new Especialista()
            .cpf(UPDATED_CPF)
            .identification(UPDATED_IDENTIFICATION)
            .birthDate(UPDATED_BIRTH_DATE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .timeSession(UPDATED_TIME_SESSION)
            .specialistType(UPDATED_SPECIALIST_TYPE);
        return especialista;
    }

    @BeforeEach
    public void initTest() {
        especialista = createEntity(em);
    }

    @Test
    @Transactional
    void createEspecialista() throws Exception {
        int databaseSizeBeforeCreate = especialistaRepository.findAll().size();
        // Create the Especialista
        EspecialistaDto especialistaDto = especialistaMapper.toDto(especialista);
        restEspecialistaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(especialistaDto))
            )
            .andExpect(status().isCreated());

        // Validate the Especialista in the database
        List<Especialista> especialistaList = especialistaRepository.findAll();
        assertThat(especialistaList).hasSize(databaseSizeBeforeCreate + 1);
        Especialista testEspecialista = especialistaList.get(especialistaList.size() - 1);
        assertThat(testEspecialista.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testEspecialista.getIdentification()).isEqualTo(DEFAULT_IDENTIFICATION);
        assertThat(testEspecialista.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEspecialista.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEspecialista.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testEspecialista.getTimeSession()).isEqualTo(DEFAULT_TIME_SESSION);
        assertThat(testEspecialista.getSpecialistType()).isEqualTo(DEFAULT_SPECIALIST_TYPE);
    }

    @Test
    @Transactional
    void createEspecialistaWithExistingId() throws Exception {
        // Create the Especialista with an existing ID
        especialista.setId(1L);
        EspecialistaDto especialistaDto = especialistaMapper.toDto(especialista);

        int databaseSizeBeforeCreate = especialistaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspecialistaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(especialistaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialista in the database
        List<Especialista> especialistaList = especialistaRepository.findAll();
        assertThat(especialistaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEspecialistas() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList
        restEspecialistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especialista.getId().intValue())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].identification").value(hasItem(DEFAULT_IDENTIFICATION)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].timeSession").value(hasItem(DEFAULT_TIME_SESSION)))
            .andExpect(jsonPath("$.[*].specialistType").value(hasItem(DEFAULT_SPECIALIST_TYPE.toString())));
    }

    @Test
    @Transactional
    void getEspecialista() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get the especialista
        restEspecialistaMockMvc
            .perform(get(ENTITY_API_URL_ID, especialista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(especialista.getId().intValue()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.identification").value(DEFAULT_IDENTIFICATION))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.timeSession").value(DEFAULT_TIME_SESSION))
            .andExpect(jsonPath("$.specialistType").value(DEFAULT_SPECIALIST_TYPE.toString()));
    }

    @Test
    @Transactional
    void getEspecialistasByIdFiltering() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        Long id = especialista.getId();

        defaultEspecialistaShouldBeFound("id.equals=" + id);
        defaultEspecialistaShouldNotBeFound("id.notEquals=" + id);

        defaultEspecialistaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEspecialistaShouldNotBeFound("id.greaterThan=" + id);

        defaultEspecialistaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEspecialistaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEspecialistasByCpfIsEqualToSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where cpf equals to DEFAULT_CPF
        defaultEspecialistaShouldBeFound("cpf.equals=" + DEFAULT_CPF);

        // Get all the especialistaList where cpf equals to UPDATED_CPF
        defaultEspecialistaShouldNotBeFound("cpf.equals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllEspecialistasByCpfIsInShouldWork() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where cpf in DEFAULT_CPF or UPDATED_CPF
        defaultEspecialistaShouldBeFound("cpf.in=" + DEFAULT_CPF + "," + UPDATED_CPF);

        // Get all the especialistaList where cpf equals to UPDATED_CPF
        defaultEspecialistaShouldNotBeFound("cpf.in=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllEspecialistasByCpfIsNullOrNotNull() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where cpf is not null
        defaultEspecialistaShouldBeFound("cpf.specified=true");

        // Get all the especialistaList where cpf is null
        defaultEspecialistaShouldNotBeFound("cpf.specified=false");
    }

    @Test
    @Transactional
    void getAllEspecialistasByCpfContainsSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where cpf contains DEFAULT_CPF
        defaultEspecialistaShouldBeFound("cpf.contains=" + DEFAULT_CPF);

        // Get all the especialistaList where cpf contains UPDATED_CPF
        defaultEspecialistaShouldNotBeFound("cpf.contains=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllEspecialistasByCpfNotContainsSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where cpf does not contain DEFAULT_CPF
        defaultEspecialistaShouldNotBeFound("cpf.doesNotContain=" + DEFAULT_CPF);

        // Get all the especialistaList where cpf does not contain UPDATED_CPF
        defaultEspecialistaShouldBeFound("cpf.doesNotContain=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllEspecialistasByIdentificationIsEqualToSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where identification equals to DEFAULT_IDENTIFICATION
        defaultEspecialistaShouldBeFound("identification.equals=" + DEFAULT_IDENTIFICATION);

        // Get all the especialistaList where identification equals to UPDATED_IDENTIFICATION
        defaultEspecialistaShouldNotBeFound("identification.equals=" + UPDATED_IDENTIFICATION);
    }

    @Test
    @Transactional
    void getAllEspecialistasByIdentificationIsInShouldWork() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where identification in DEFAULT_IDENTIFICATION or UPDATED_IDENTIFICATION
        defaultEspecialistaShouldBeFound("identification.in=" + DEFAULT_IDENTIFICATION + "," + UPDATED_IDENTIFICATION);

        // Get all the especialistaList where identification equals to UPDATED_IDENTIFICATION
        defaultEspecialistaShouldNotBeFound("identification.in=" + UPDATED_IDENTIFICATION);
    }

    @Test
    @Transactional
    void getAllEspecialistasByIdentificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where identification is not null
        defaultEspecialistaShouldBeFound("identification.specified=true");

        // Get all the especialistaList where identification is null
        defaultEspecialistaShouldNotBeFound("identification.specified=false");
    }

    @Test
    @Transactional
    void getAllEspecialistasByIdentificationContainsSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where identification contains DEFAULT_IDENTIFICATION
        defaultEspecialistaShouldBeFound("identification.contains=" + DEFAULT_IDENTIFICATION);

        // Get all the especialistaList where identification contains UPDATED_IDENTIFICATION
        defaultEspecialistaShouldNotBeFound("identification.contains=" + UPDATED_IDENTIFICATION);
    }

    @Test
    @Transactional
    void getAllEspecialistasByIdentificationNotContainsSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where identification does not contain DEFAULT_IDENTIFICATION
        defaultEspecialistaShouldNotBeFound("identification.doesNotContain=" + DEFAULT_IDENTIFICATION);

        // Get all the especialistaList where identification does not contain UPDATED_IDENTIFICATION
        defaultEspecialistaShouldBeFound("identification.doesNotContain=" + UPDATED_IDENTIFICATION);
    }

    @Test
    @Transactional
    void getAllEspecialistasByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultEspecialistaShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the especialistaList where birthDate equals to UPDATED_BIRTH_DATE
        defaultEspecialistaShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllEspecialistasByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultEspecialistaShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the especialistaList where birthDate equals to UPDATED_BIRTH_DATE
        defaultEspecialistaShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllEspecialistasByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where birthDate is not null
        defaultEspecialistaShouldBeFound("birthDate.specified=true");

        // Get all the especialistaList where birthDate is null
        defaultEspecialistaShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEspecialistasByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where description equals to DEFAULT_DESCRIPTION
        defaultEspecialistaShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the especialistaList where description equals to UPDATED_DESCRIPTION
        defaultEspecialistaShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEspecialistasByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEspecialistaShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the especialistaList where description equals to UPDATED_DESCRIPTION
        defaultEspecialistaShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEspecialistasByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where description is not null
        defaultEspecialistaShouldBeFound("description.specified=true");

        // Get all the especialistaList where description is null
        defaultEspecialistaShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllEspecialistasByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where description contains DEFAULT_DESCRIPTION
        defaultEspecialistaShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the especialistaList where description contains UPDATED_DESCRIPTION
        defaultEspecialistaShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEspecialistasByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where description does not contain DEFAULT_DESCRIPTION
        defaultEspecialistaShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the especialistaList where description does not contain UPDATED_DESCRIPTION
        defaultEspecialistaShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEspecialistasByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where price equals to DEFAULT_PRICE
        defaultEspecialistaShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the especialistaList where price equals to UPDATED_PRICE
        defaultEspecialistaShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllEspecialistasByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultEspecialistaShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the especialistaList where price equals to UPDATED_PRICE
        defaultEspecialistaShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllEspecialistasByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where price is not null
        defaultEspecialistaShouldBeFound("price.specified=true");

        // Get all the especialistaList where price is null
        defaultEspecialistaShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllEspecialistasByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where price is greater than or equal to DEFAULT_PRICE
        defaultEspecialistaShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the especialistaList where price is greater than or equal to UPDATED_PRICE
        defaultEspecialistaShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllEspecialistasByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where price is less than or equal to DEFAULT_PRICE
        defaultEspecialistaShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the especialistaList where price is less than or equal to SMALLER_PRICE
        defaultEspecialistaShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllEspecialistasByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where price is less than DEFAULT_PRICE
        defaultEspecialistaShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the especialistaList where price is less than UPDATED_PRICE
        defaultEspecialistaShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllEspecialistasByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where price is greater than DEFAULT_PRICE
        defaultEspecialistaShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the especialistaList where price is greater than SMALLER_PRICE
        defaultEspecialistaShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllEspecialistasByTimeSessionIsEqualToSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where timeSession equals to DEFAULT_TIME_SESSION
        defaultEspecialistaShouldBeFound("timeSession.equals=" + DEFAULT_TIME_SESSION);

        // Get all the especialistaList where timeSession equals to UPDATED_TIME_SESSION
        defaultEspecialistaShouldNotBeFound("timeSession.equals=" + UPDATED_TIME_SESSION);
    }

    @Test
    @Transactional
    void getAllEspecialistasByTimeSessionIsInShouldWork() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where timeSession in DEFAULT_TIME_SESSION or UPDATED_TIME_SESSION
        defaultEspecialistaShouldBeFound("timeSession.in=" + DEFAULT_TIME_SESSION + "," + UPDATED_TIME_SESSION);

        // Get all the especialistaList where timeSession equals to UPDATED_TIME_SESSION
        defaultEspecialistaShouldNotBeFound("timeSession.in=" + UPDATED_TIME_SESSION);
    }

    @Test
    @Transactional
    void getAllEspecialistasByTimeSessionIsNullOrNotNull() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where timeSession is not null
        defaultEspecialistaShouldBeFound("timeSession.specified=true");

        // Get all the especialistaList where timeSession is null
        defaultEspecialistaShouldNotBeFound("timeSession.specified=false");
    }

    @Test
    @Transactional
    void getAllEspecialistasByTimeSessionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where timeSession is greater than or equal to DEFAULT_TIME_SESSION
        defaultEspecialistaShouldBeFound("timeSession.greaterThanOrEqual=" + DEFAULT_TIME_SESSION);

        // Get all the especialistaList where timeSession is greater than or equal to UPDATED_TIME_SESSION
        defaultEspecialistaShouldNotBeFound("timeSession.greaterThanOrEqual=" + UPDATED_TIME_SESSION);
    }

    @Test
    @Transactional
    void getAllEspecialistasByTimeSessionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where timeSession is less than or equal to DEFAULT_TIME_SESSION
        defaultEspecialistaShouldBeFound("timeSession.lessThanOrEqual=" + DEFAULT_TIME_SESSION);

        // Get all the especialistaList where timeSession is less than or equal to SMALLER_TIME_SESSION
        defaultEspecialistaShouldNotBeFound("timeSession.lessThanOrEqual=" + SMALLER_TIME_SESSION);
    }

    @Test
    @Transactional
    void getAllEspecialistasByTimeSessionIsLessThanSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where timeSession is less than DEFAULT_TIME_SESSION
        defaultEspecialistaShouldNotBeFound("timeSession.lessThan=" + DEFAULT_TIME_SESSION);

        // Get all the especialistaList where timeSession is less than UPDATED_TIME_SESSION
        defaultEspecialistaShouldBeFound("timeSession.lessThan=" + UPDATED_TIME_SESSION);
    }

    @Test
    @Transactional
    void getAllEspecialistasByTimeSessionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where timeSession is greater than DEFAULT_TIME_SESSION
        defaultEspecialistaShouldNotBeFound("timeSession.greaterThan=" + DEFAULT_TIME_SESSION);

        // Get all the especialistaList where timeSession is greater than SMALLER_TIME_SESSION
        defaultEspecialistaShouldBeFound("timeSession.greaterThan=" + SMALLER_TIME_SESSION);
    }

    @Test
    @Transactional
    void getAllEspecialistasBySpecialistTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where specialistType equals to DEFAULT_SPECIALIST_TYPE
        defaultEspecialistaShouldBeFound("specialistType.equals=" + DEFAULT_SPECIALIST_TYPE);

        // Get all the especialistaList where specialistType equals to UPDATED_SPECIALIST_TYPE
        defaultEspecialistaShouldNotBeFound("specialistType.equals=" + UPDATED_SPECIALIST_TYPE);
    }

    @Test
    @Transactional
    void getAllEspecialistasBySpecialistTypeIsInShouldWork() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where specialistType in DEFAULT_SPECIALIST_TYPE or UPDATED_SPECIALIST_TYPE
        defaultEspecialistaShouldBeFound("specialistType.in=" + DEFAULT_SPECIALIST_TYPE + "," + UPDATED_SPECIALIST_TYPE);

        // Get all the especialistaList where specialistType equals to UPDATED_SPECIALIST_TYPE
        defaultEspecialistaShouldNotBeFound("specialistType.in=" + UPDATED_SPECIALIST_TYPE);
    }

    @Test
    @Transactional
    void getAllEspecialistasBySpecialistTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList where specialistType is not null
        defaultEspecialistaShouldBeFound("specialistType.specified=true");

        // Get all the especialistaList where specialistType is null
        defaultEspecialistaShouldNotBeFound("specialistType.specified=false");
    }

    @Test
    @Transactional
    void getAllEspecialistasByInternalUserIsEqualToSomething() throws Exception {
        User internalUser;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            especialistaRepository.saveAndFlush(especialista);
            internalUser = UserResourceIT.createEntity(em);
        } else {
            internalUser = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(internalUser);
        em.flush();
        especialista.setInternalUser(internalUser);
        especialistaRepository.saveAndFlush(especialista);
        Long internalUserId = internalUser.getId();
        // Get all the especialistaList where internalUser equals to internalUserId
        defaultEspecialistaShouldBeFound("internalUserId.equals=" + internalUserId);

        // Get all the especialistaList where internalUser equals to (internalUserId + 1)
        defaultEspecialistaShouldNotBeFound("internalUserId.equals=" + (internalUserId + 1));
    }

    @Test
    @Transactional
    void getAllEspecialistasByEspecialidadeEspecialistaIsEqualToSomething() throws Exception {
        EspecialidadeEspecialista especialidadeEspecialista;
        if (TestUtil.findAll(em, EspecialidadeEspecialista.class).isEmpty()) {
            especialistaRepository.saveAndFlush(especialista);
            especialidadeEspecialista = EspecialidadeEspecialistaResourceIT.createEntity(em);
        } else {
            especialidadeEspecialista = TestUtil.findAll(em, EspecialidadeEspecialista.class).get(0);
        }
        em.persist(especialidadeEspecialista);
        em.flush();
        especialista.addEspecialidadeEspecialista(especialidadeEspecialista);
        especialistaRepository.saveAndFlush(especialista);
        Long especialidadeEspecialistaId = especialidadeEspecialista.getId();
        // Get all the especialistaList where especialidadeEspecialista equals to especialidadeEspecialistaId
        defaultEspecialistaShouldBeFound("especialidadeEspecialistaId.equals=" + especialidadeEspecialistaId);

        // Get all the especialistaList where especialidadeEspecialista equals to (especialidadeEspecialistaId + 1)
        defaultEspecialistaShouldNotBeFound("especialidadeEspecialistaId.equals=" + (especialidadeEspecialistaId + 1));
    }

    @Test
    @Transactional
    void getAllEspecialistasByAvaliacaoIsEqualToSomething() throws Exception {
        Avaliacao avaliacao;
        if (TestUtil.findAll(em, Avaliacao.class).isEmpty()) {
            especialistaRepository.saveAndFlush(especialista);
            avaliacao = AvaliacaoResourceIT.createEntity(em);
        } else {
            avaliacao = TestUtil.findAll(em, Avaliacao.class).get(0);
        }
        em.persist(avaliacao);
        em.flush();
        especialista.addAvaliacao(avaliacao);
        especialistaRepository.saveAndFlush(especialista);
        Long avaliacaoId = avaliacao.getId();
        // Get all the especialistaList where avaliacao equals to avaliacaoId
        defaultEspecialistaShouldBeFound("avaliacaoId.equals=" + avaliacaoId);

        // Get all the especialistaList where avaliacao equals to (avaliacaoId + 1)
        defaultEspecialistaShouldNotBeFound("avaliacaoId.equals=" + (avaliacaoId + 1));
    }

    @Test
    @Transactional
    void getAllEspecialistasByConsultaIsEqualToSomething() throws Exception {
        Consulta consulta;
        if (TestUtil.findAll(em, Consulta.class).isEmpty()) {
            especialistaRepository.saveAndFlush(especialista);
            consulta = ConsultaResourceIT.createEntity(em);
        } else {
            consulta = TestUtil.findAll(em, Consulta.class).get(0);
        }
        em.persist(consulta);
        em.flush();
        especialista.addConsulta(consulta);
        especialistaRepository.saveAndFlush(especialista);
        Long consultaId = consulta.getId();
        // Get all the especialistaList where consulta equals to consultaId
        defaultEspecialistaShouldBeFound("consultaId.equals=" + consultaId);

        // Get all the especialistaList where consulta equals to (consultaId + 1)
        defaultEspecialistaShouldNotBeFound("consultaId.equals=" + (consultaId + 1));
    }

    @Test
    @Transactional
    void getAllEspecialistasByEspecialistaIsEqualToSomething() throws Exception {
        Especialista especialista;
        if (TestUtil.findAll(em, Especialista.class).isEmpty()) {
            especialistaRepository.saveAndFlush(especialista);
            especialista = EspecialistaResourceIT.createEntity(em);
        } else {
            especialista = TestUtil.findAll(em, Especialista.class).get(0);
        }
        em.persist(especialista);
        em.flush();
        especialista.addEspecialista(especialista);
        especialistaRepository.saveAndFlush(especialista);
        Long especialistaId = especialista.getId();
        // Get all the especialistaList where especialista equals to especialistaId
        defaultEspecialistaShouldBeFound("especialistaId.equals=" + especialistaId);

        // Get all the especialistaList where especialista equals to (especialistaId + 1)
        defaultEspecialistaShouldNotBeFound("especialistaId.equals=" + (especialistaId + 1));
    }

    @Test
    @Transactional
    void getAllEspecialistasByEstadoIsEqualToSomething() throws Exception {
        Estado estado;
        if (TestUtil.findAll(em, Estado.class).isEmpty()) {
            especialistaRepository.saveAndFlush(especialista);
            estado = EstadoResourceIT.createEntity(em);
        } else {
            estado = TestUtil.findAll(em, Estado.class).get(0);
        }
        em.persist(estado);
        em.flush();
        especialista.setEstado(estado);
        especialistaRepository.saveAndFlush(especialista);
        Long estadoId = estado.getId();
        // Get all the especialistaList where estado equals to estadoId
        defaultEspecialistaShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the especialistaList where estado equals to (estadoId + 1)
        defaultEspecialistaShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }

    @Test
    @Transactional
    void getAllEspecialistasByCidadeIsEqualToSomething() throws Exception {
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            especialistaRepository.saveAndFlush(especialista);
            cidade = CidadeResourceIT.createEntity(em);
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        em.persist(cidade);
        em.flush();
        especialista.setCidade(cidade);
        especialistaRepository.saveAndFlush(especialista);
        Long cidadeId = cidade.getId();
        // Get all the especialistaList where cidade equals to cidadeId
        defaultEspecialistaShouldBeFound("cidadeId.equals=" + cidadeId);

        // Get all the especialistaList where cidade equals to (cidadeId + 1)
        defaultEspecialistaShouldNotBeFound("cidadeId.equals=" + (cidadeId + 1));
    }

    @Test
    @Transactional
    void getAllEspecialistasByUniversidadeIsEqualToSomething() throws Exception {
        Universidade universidade;
        if (TestUtil.findAll(em, Universidade.class).isEmpty()) {
            especialistaRepository.saveAndFlush(especialista);
            universidade = UniversidadeResourceIT.createEntity(em);
        } else {
            universidade = TestUtil.findAll(em, Universidade.class).get(0);
        }
        em.persist(universidade);
        em.flush();
        especialista.setUniversidade(universidade);
        especialistaRepository.saveAndFlush(especialista);
        Long universidadeId = universidade.getId();
        // Get all the especialistaList where universidade equals to universidadeId
        defaultEspecialistaShouldBeFound("universidadeId.equals=" + universidadeId);

        // Get all the especialistaList where universidade equals to (universidadeId + 1)
        defaultEspecialistaShouldNotBeFound("universidadeId.equals=" + (universidadeId + 1));
    }

    @Test
    @Transactional
    void getAllEspecialistasBySupervisorIdIsEqualToSomething() throws Exception {
        Especialista supervisorId;
        if (TestUtil.findAll(em, Especialista.class).isEmpty()) {
            especialistaRepository.saveAndFlush(especialista);
            supervisorId = EspecialistaResourceIT.createEntity(em);
        } else {
            supervisorId = TestUtil.findAll(em, Especialista.class).get(0);
        }
        em.persist(supervisorId);
        em.flush();
        especialista.setSupervisorId(supervisorId);
        especialistaRepository.saveAndFlush(especialista);
        Long supervisorIdId = supervisorId.getId();
        // Get all the especialistaList where supervisorId equals to supervisorIdId
        defaultEspecialistaShouldBeFound("supervisorIdId.equals=" + supervisorIdId);

        // Get all the especialistaList where supervisorId equals to (supervisorIdId + 1)
        defaultEspecialistaShouldNotBeFound("supervisorIdId.equals=" + (supervisorIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEspecialistaShouldBeFound(String filter) throws Exception {
        restEspecialistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especialista.getId().intValue())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].identification").value(hasItem(DEFAULT_IDENTIFICATION)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].timeSession").value(hasItem(DEFAULT_TIME_SESSION)))
            .andExpect(jsonPath("$.[*].specialistType").value(hasItem(DEFAULT_SPECIALIST_TYPE.toString())));

        // Check, that the count call also returns 1
        restEspecialistaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEspecialistaShouldNotBeFound(String filter) throws Exception {
        restEspecialistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEspecialistaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEspecialista() throws Exception {
        // Get the especialista
        restEspecialistaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEspecialista() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        int databaseSizeBeforeUpdate = especialistaRepository.findAll().size();

        // Update the especialista
        Especialista updatedEspecialista = especialistaRepository.findById(especialista.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEspecialista are not directly saved in db
        em.detach(updatedEspecialista);
        updatedEspecialista
            .cpf(UPDATED_CPF)
            .identification(UPDATED_IDENTIFICATION)
            .birthDate(UPDATED_BIRTH_DATE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .timeSession(UPDATED_TIME_SESSION)
            .specialistType(UPDATED_SPECIALIST_TYPE);
        EspecialistaDto especialistaDto = especialistaMapper.toDto(updatedEspecialista);

        restEspecialistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, especialistaDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especialistaDto))
            )
            .andExpect(status().isOk());

        // Validate the Especialista in the database
        List<Especialista> especialistaList = especialistaRepository.findAll();
        assertThat(especialistaList).hasSize(databaseSizeBeforeUpdate);
        Especialista testEspecialista = especialistaList.get(especialistaList.size() - 1);
        assertThat(testEspecialista.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testEspecialista.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);
        assertThat(testEspecialista.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEspecialista.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEspecialista.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testEspecialista.getTimeSession()).isEqualTo(UPDATED_TIME_SESSION);
        assertThat(testEspecialista.getSpecialistType()).isEqualTo(UPDATED_SPECIALIST_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEspecialista() throws Exception {
        int databaseSizeBeforeUpdate = especialistaRepository.findAll().size();
        especialista.setId(longCount.incrementAndGet());

        // Create the Especialista
        EspecialistaDto especialistaDto = especialistaMapper.toDto(especialista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspecialistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, especialistaDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especialistaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialista in the database
        List<Especialista> especialistaList = especialistaRepository.findAll();
        assertThat(especialistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEspecialista() throws Exception {
        int databaseSizeBeforeUpdate = especialistaRepository.findAll().size();
        especialista.setId(longCount.incrementAndGet());

        // Create the Especialista
        EspecialistaDto especialistaDto = especialistaMapper.toDto(especialista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especialistaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialista in the database
        List<Especialista> especialistaList = especialistaRepository.findAll();
        assertThat(especialistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEspecialista() throws Exception {
        int databaseSizeBeforeUpdate = especialistaRepository.findAll().size();
        especialista.setId(longCount.incrementAndGet());

        // Create the Especialista
        EspecialistaDto especialistaDto = especialistaMapper.toDto(especialista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialistaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(especialistaDto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Especialista in the database
        List<Especialista> especialistaList = especialistaRepository.findAll();
        assertThat(especialistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEspecialistaWithPatch() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        int databaseSizeBeforeUpdate = especialistaRepository.findAll().size();

        // Update the especialista using partial update
        Especialista partialUpdatedEspecialista = new Especialista();
        partialUpdatedEspecialista.setId(especialista.getId());

        partialUpdatedEspecialista
            .identification(UPDATED_IDENTIFICATION)
            .description(UPDATED_DESCRIPTION)
            .specialistType(UPDATED_SPECIALIST_TYPE);

        restEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspecialista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspecialista))
            )
            .andExpect(status().isOk());

        // Validate the Especialista in the database
        List<Especialista> especialistaList = especialistaRepository.findAll();
        assertThat(especialistaList).hasSize(databaseSizeBeforeUpdate);
        Especialista testEspecialista = especialistaList.get(especialistaList.size() - 1);
        assertThat(testEspecialista.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testEspecialista.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);
        assertThat(testEspecialista.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEspecialista.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEspecialista.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testEspecialista.getTimeSession()).isEqualTo(DEFAULT_TIME_SESSION);
        assertThat(testEspecialista.getSpecialistType()).isEqualTo(UPDATED_SPECIALIST_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEspecialistaWithPatch() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        int databaseSizeBeforeUpdate = especialistaRepository.findAll().size();

        // Update the especialista using partial update
        Especialista partialUpdatedEspecialista = new Especialista();
        partialUpdatedEspecialista.setId(especialista.getId());

        partialUpdatedEspecialista
            .cpf(UPDATED_CPF)
            .identification(UPDATED_IDENTIFICATION)
            .birthDate(UPDATED_BIRTH_DATE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .timeSession(UPDATED_TIME_SESSION)
            .specialistType(UPDATED_SPECIALIST_TYPE);

        restEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspecialista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspecialista))
            )
            .andExpect(status().isOk());

        // Validate the Especialista in the database
        List<Especialista> especialistaList = especialistaRepository.findAll();
        assertThat(especialistaList).hasSize(databaseSizeBeforeUpdate);
        Especialista testEspecialista = especialistaList.get(especialistaList.size() - 1);
        assertThat(testEspecialista.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testEspecialista.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);
        assertThat(testEspecialista.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEspecialista.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEspecialista.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testEspecialista.getTimeSession()).isEqualTo(UPDATED_TIME_SESSION);
        assertThat(testEspecialista.getSpecialistType()).isEqualTo(UPDATED_SPECIALIST_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEspecialista() throws Exception {
        int databaseSizeBeforeUpdate = especialistaRepository.findAll().size();
        especialista.setId(longCount.incrementAndGet());

        // Create the Especialista
        EspecialistaDto especialistaDto = especialistaMapper.toDto(especialista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, especialistaDto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(especialistaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialista in the database
        List<Especialista> especialistaList = especialistaRepository.findAll();
        assertThat(especialistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEspecialista() throws Exception {
        int databaseSizeBeforeUpdate = especialistaRepository.findAll().size();
        especialista.setId(longCount.incrementAndGet());

        // Create the Especialista
        EspecialistaDto especialistaDto = especialistaMapper.toDto(especialista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(especialistaDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialista in the database
        List<Especialista> especialistaList = especialistaRepository.findAll();
        assertThat(especialistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEspecialista() throws Exception {
        int databaseSizeBeforeUpdate = especialistaRepository.findAll().size();
        especialista.setId(longCount.incrementAndGet());

        // Create the Especialista
        EspecialistaDto especialistaDto = especialistaMapper.toDto(especialista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(especialistaDto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Especialista in the database
        List<Especialista> especialistaList = especialistaRepository.findAll();
        assertThat(especialistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEspecialista() throws Exception {
        // Initialize the database
        especialistaRepository.saveAndFlush(especialista);

        int databaseSizeBeforeDelete = especialistaRepository.findAll().size();

        // Delete the especialista
        restEspecialistaMockMvc
            .perform(delete(ENTITY_API_URL_ID, especialista.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Especialista> especialistaList = especialistaRepository.findAll();
        assertThat(especialistaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
