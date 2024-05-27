package com.clinicallink.app.web.rest;

import com.clinicallink.app.repository.ConsultaRepository;
import com.clinicallink.app.service.ConsultaQueryService;
import com.clinicallink.app.service.ConsultaService;
import com.clinicallink.app.service.criteria.ConsultaCriteria;
import com.clinicallink.app.service.dto.ConsultaDto;
import com.clinicallink.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.clinicallink.app.domain.Consulta}.
 */
@RestController
@RequestMapping("/api/consultas")
public class ConsultaResource {

    private final Logger log = LoggerFactory.getLogger(ConsultaResource.class);

    private static final String ENTITY_NAME = "consulta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsultaService consultaService;

    private final ConsultaRepository consultaRepository;

    private final ConsultaQueryService consultaQueryService;

    public ConsultaResource(
        ConsultaService consultaService,
        ConsultaRepository consultaRepository,
        ConsultaQueryService consultaQueryService
    ) {
        this.consultaService = consultaService;
        this.consultaRepository = consultaRepository;
        this.consultaQueryService = consultaQueryService;
    }

    /**
     * {@code POST  /consultas} : Create a new consulta.
     *
     * @param consultaDto the consultaDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consultaDto, or with status {@code 400 (Bad Request)} if the consulta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ConsultaDto> createConsulta(@RequestBody ConsultaDto consultaDto) throws URISyntaxException {
        log.debug("REST request to save Consulta : {}", consultaDto);
        if (consultaDto.getId() != null) {
            throw new BadRequestAlertException("A new consulta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultaDto result = consultaService.save(consultaDto);
        return ResponseEntity
            .created(new URI("/api/consultas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consultas/:id} : Updates an existing consulta.
     *
     * @param id the id of the consultaDto to save.
     * @param consultaDto the consultaDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultaDto,
     * or with status {@code 400 (Bad Request)} if the consultaDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consultaDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDto> updateConsulta(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConsultaDto consultaDto
    ) throws URISyntaxException {
        log.debug("REST request to update Consulta : {}, {}", id, consultaDto);
        if (consultaDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultaDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConsultaDto result = consultaService.update(consultaDto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consultaDto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /consultas/:id} : Partial updates given fields of an existing consulta, field will ignore if it is null
     *
     * @param id the id of the consultaDto to save.
     * @param consultaDto the consultaDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultaDto,
     * or with status {@code 400 (Bad Request)} if the consultaDto is not valid,
     * or with status {@code 404 (Not Found)} if the consultaDto is not found,
     * or with status {@code 500 (Internal Server Error)} if the consultaDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConsultaDto> partialUpdateConsulta(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConsultaDto consultaDto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Consulta partially : {}, {}", id, consultaDto);
        if (consultaDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultaDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConsultaDto> result = consultaService.partialUpdate(consultaDto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consultaDto.getId().toString())
        );
    }

    /**
     * {@code GET  /consultas} : get all the consultas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consultas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ConsultaDto>> getAllConsultas(
        ConsultaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Consultas by criteria: {}", criteria);

        Page<ConsultaDto> page = consultaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consultas/count} : count all the consultas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countConsultas(ConsultaCriteria criteria) {
        log.debug("REST request to count Consultas by criteria: {}", criteria);
        return ResponseEntity.ok().body(consultaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /consultas/:id} : get the "id" consulta.
     *
     * @param id the id of the consultaDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consultaDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDto> getConsulta(@PathVariable Long id) {
        log.debug("REST request to get Consulta : {}", id);
        Optional<ConsultaDto> consultaDto = consultaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consultaDto);
    }

    /**
     * {@code DELETE  /consultas/:id} : delete the "id" consulta.
     *
     * @param id the id of the consultaDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsulta(@PathVariable Long id) {
        log.debug("REST request to delete Consulta : {}", id);
        consultaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
