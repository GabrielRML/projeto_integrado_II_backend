package com.clinicallink.app.web.rest;

import com.clinicallink.app.repository.EspecialidadeRepository;
import com.clinicallink.app.service.EspecialidadeQueryService;
import com.clinicallink.app.service.EspecialidadeService;
import com.clinicallink.app.service.criteria.EspecialidadeCriteria;
import com.clinicallink.app.service.dto.EspecialidadeDto;
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
 * REST controller for managing {@link com.clinicallink.app.domain.Especialidade}.
 */
@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadeResource {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeResource.class);

    private static final String ENTITY_NAME = "especialidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EspecialidadeService especialidadeService;

    private final EspecialidadeRepository especialidadeRepository;

    private final EspecialidadeQueryService especialidadeQueryService;

    public EspecialidadeResource(
        EspecialidadeService especialidadeService,
        EspecialidadeRepository especialidadeRepository,
        EspecialidadeQueryService especialidadeQueryService
    ) {
        this.especialidadeService = especialidadeService;
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeQueryService = especialidadeQueryService;
    }

    /**
     * {@code POST  /especialidades} : Create a new especialidade.
     *
     * @param especialidadeDto the especialidadeDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new especialidadeDto, or with status {@code 400 (Bad Request)} if the especialidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EspecialidadeDto> createEspecialidade(@RequestBody EspecialidadeDto especialidadeDto) throws URISyntaxException {
        log.debug("REST request to save Especialidade : {}", especialidadeDto);
        if (especialidadeDto.getId() != null) {
            throw new BadRequestAlertException("A new especialidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EspecialidadeDto result = especialidadeService.save(especialidadeDto);
        return ResponseEntity
            .created(new URI("/api/especialidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /especialidades/:id} : Updates an existing especialidade.
     *
     * @param id the id of the especialidadeDto to save.
     * @param especialidadeDto the especialidadeDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especialidadeDto,
     * or with status {@code 400 (Bad Request)} if the especialidadeDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the especialidadeDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeDto> updateEspecialidade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EspecialidadeDto especialidadeDto
    ) throws URISyntaxException {
        log.debug("REST request to update Especialidade : {}, {}", id, especialidadeDto);
        if (especialidadeDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, especialidadeDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!especialidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EspecialidadeDto result = especialidadeService.update(especialidadeDto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, especialidadeDto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /especialidades/:id} : Partial updates given fields of an existing especialidade, field will ignore if it is null
     *
     * @param id the id of the especialidadeDto to save.
     * @param especialidadeDto the especialidadeDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especialidadeDto,
     * or with status {@code 400 (Bad Request)} if the especialidadeDto is not valid,
     * or with status {@code 404 (Not Found)} if the especialidadeDto is not found,
     * or with status {@code 500 (Internal Server Error)} if the especialidadeDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EspecialidadeDto> partialUpdateEspecialidade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EspecialidadeDto especialidadeDto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Especialidade partially : {}, {}", id, especialidadeDto);
        if (especialidadeDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, especialidadeDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!especialidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EspecialidadeDto> result = especialidadeService.partialUpdate(especialidadeDto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, especialidadeDto.getId().toString())
        );
    }

    /**
     * {@code GET  /especialidades} : get all the especialidades.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of especialidades in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EspecialidadeDto>> getAllEspecialidades(
        EspecialidadeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Especialidades by criteria: {}", criteria);

        Page<EspecialidadeDto> page = especialidadeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /especialidades/count} : count all the especialidades.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEspecialidades(EspecialidadeCriteria criteria) {
        log.debug("REST request to count Especialidades by criteria: {}", criteria);
        return ResponseEntity.ok().body(especialidadeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /especialidades/:id} : get the "id" especialidade.
     *
     * @param id the id of the especialidadeDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the especialidadeDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeDto> getEspecialidade(@PathVariable Long id) {
        log.debug("REST request to get Especialidade : {}", id);
        Optional<EspecialidadeDto> especialidadeDto = especialidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(especialidadeDto);
    }

    /**
     * {@code DELETE  /especialidades/:id} : delete the "id" especialidade.
     *
     * @param id the id of the especialidadeDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspecialidade(@PathVariable Long id) {
        log.debug("REST request to delete Especialidade : {}", id);
        especialidadeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
