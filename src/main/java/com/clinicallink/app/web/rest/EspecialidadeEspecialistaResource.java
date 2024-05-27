package com.clinicallink.app.web.rest;

import com.clinicallink.app.repository.EspecialidadeEspecialistaRepository;
import com.clinicallink.app.service.EspecialidadeEspecialistaQueryService;
import com.clinicallink.app.service.EspecialidadeEspecialistaService;
import com.clinicallink.app.service.criteria.EspecialidadeEspecialistaCriteria;
import com.clinicallink.app.service.dto.EspecialidadeEspecialistaDto;
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
 * REST controller for managing {@link com.clinicallink.app.domain.EspecialidadeEspecialista}.
 */
@RestController
@RequestMapping("/api/especialidade-especialistas")
public class EspecialidadeEspecialistaResource {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeEspecialistaResource.class);

    private static final String ENTITY_NAME = "especialidadeEspecialista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EspecialidadeEspecialistaService especialidadeEspecialistaService;

    private final EspecialidadeEspecialistaRepository especialidadeEspecialistaRepository;

    private final EspecialidadeEspecialistaQueryService especialidadeEspecialistaQueryService;

    public EspecialidadeEspecialistaResource(
        EspecialidadeEspecialistaService especialidadeEspecialistaService,
        EspecialidadeEspecialistaRepository especialidadeEspecialistaRepository,
        EspecialidadeEspecialistaQueryService especialidadeEspecialistaQueryService
    ) {
        this.especialidadeEspecialistaService = especialidadeEspecialistaService;
        this.especialidadeEspecialistaRepository = especialidadeEspecialistaRepository;
        this.especialidadeEspecialistaQueryService = especialidadeEspecialistaQueryService;
    }

    /**
     * {@code POST  /especialidade-especialistas} : Create a new especialidadeEspecialista.
     *
     * @param especialidadeEspecialistaDto the especialidadeEspecialistaDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new especialidadeEspecialistaDto, or with status {@code 400 (Bad Request)} if the especialidadeEspecialista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EspecialidadeEspecialistaDto> createEspecialidadeEspecialista(
        @RequestBody EspecialidadeEspecialistaDto especialidadeEspecialistaDto
    ) throws URISyntaxException {
        log.debug("REST request to save EspecialidadeEspecialista : {}", especialidadeEspecialistaDto);
        if (especialidadeEspecialistaDto.getId() != null) {
            throw new BadRequestAlertException("A new especialidadeEspecialista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EspecialidadeEspecialistaDto result = especialidadeEspecialistaService.save(especialidadeEspecialistaDto);
        return ResponseEntity
            .created(new URI("/api/especialidade-especialistas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /especialidade-especialistas/:id} : Updates an existing especialidadeEspecialista.
     *
     * @param id the id of the especialidadeEspecialistaDto to save.
     * @param especialidadeEspecialistaDto the especialidadeEspecialistaDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especialidadeEspecialistaDto,
     * or with status {@code 400 (Bad Request)} if the especialidadeEspecialistaDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the especialidadeEspecialistaDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeEspecialistaDto> updateEspecialidadeEspecialista(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EspecialidadeEspecialistaDto especialidadeEspecialistaDto
    ) throws URISyntaxException {
        log.debug("REST request to update EspecialidadeEspecialista : {}, {}", id, especialidadeEspecialistaDto);
        if (especialidadeEspecialistaDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, especialidadeEspecialistaDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!especialidadeEspecialistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EspecialidadeEspecialistaDto result = especialidadeEspecialistaService.update(especialidadeEspecialistaDto);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, especialidadeEspecialistaDto.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /especialidade-especialistas/:id} : Partial updates given fields of an existing especialidadeEspecialista, field will ignore if it is null
     *
     * @param id the id of the especialidadeEspecialistaDto to save.
     * @param especialidadeEspecialistaDto the especialidadeEspecialistaDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especialidadeEspecialistaDto,
     * or with status {@code 400 (Bad Request)} if the especialidadeEspecialistaDto is not valid,
     * or with status {@code 404 (Not Found)} if the especialidadeEspecialistaDto is not found,
     * or with status {@code 500 (Internal Server Error)} if the especialidadeEspecialistaDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EspecialidadeEspecialistaDto> partialUpdateEspecialidadeEspecialista(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EspecialidadeEspecialistaDto especialidadeEspecialistaDto
    ) throws URISyntaxException {
        log.debug("REST request to partial update EspecialidadeEspecialista partially : {}, {}", id, especialidadeEspecialistaDto);
        if (especialidadeEspecialistaDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, especialidadeEspecialistaDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!especialidadeEspecialistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EspecialidadeEspecialistaDto> result = especialidadeEspecialistaService.partialUpdate(especialidadeEspecialistaDto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, especialidadeEspecialistaDto.getId().toString())
        );
    }

    /**
     * {@code GET  /especialidade-especialistas} : get all the especialidadeEspecialistas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of especialidadeEspecialistas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EspecialidadeEspecialistaDto>> getAllEspecialidadeEspecialistas(
        EspecialidadeEspecialistaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EspecialidadeEspecialistas by criteria: {}", criteria);

        Page<EspecialidadeEspecialistaDto> page = especialidadeEspecialistaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /especialidade-especialistas/count} : count all the especialidadeEspecialistas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEspecialidadeEspecialistas(EspecialidadeEspecialistaCriteria criteria) {
        log.debug("REST request to count EspecialidadeEspecialistas by criteria: {}", criteria);
        return ResponseEntity.ok().body(especialidadeEspecialistaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /especialidade-especialistas/:id} : get the "id" especialidadeEspecialista.
     *
     * @param id the id of the especialidadeEspecialistaDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the especialidadeEspecialistaDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeEspecialistaDto> getEspecialidadeEspecialista(@PathVariable Long id) {
        log.debug("REST request to get EspecialidadeEspecialista : {}", id);
        Optional<EspecialidadeEspecialistaDto> especialidadeEspecialistaDto = especialidadeEspecialistaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(especialidadeEspecialistaDto);
    }

    /**
     * {@code DELETE  /especialidade-especialistas/:id} : delete the "id" especialidadeEspecialista.
     *
     * @param id the id of the especialidadeEspecialistaDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspecialidadeEspecialista(@PathVariable Long id) {
        log.debug("REST request to delete EspecialidadeEspecialista : {}", id);
        especialidadeEspecialistaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
