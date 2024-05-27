package com.clinicallink.app.web.rest;

import com.clinicallink.app.repository.EspecialistaRepository;
import com.clinicallink.app.service.EspecialistaQueryService;
import com.clinicallink.app.service.EspecialistaService;
import com.clinicallink.app.service.criteria.EspecialistaCriteria;
import com.clinicallink.app.service.dto.EspecialistaDto;
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
 * REST controller for managing {@link com.clinicallink.app.domain.Especialista}.
 */
@RestController
@RequestMapping("/api/especialistas")
public class EspecialistaResource {

    private final Logger log = LoggerFactory.getLogger(EspecialistaResource.class);

    private static final String ENTITY_NAME = "especialista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EspecialistaService especialistaService;

    private final EspecialistaRepository especialistaRepository;

    private final EspecialistaQueryService especialistaQueryService;

    public EspecialistaResource(
        EspecialistaService especialistaService,
        EspecialistaRepository especialistaRepository,
        EspecialistaQueryService especialistaQueryService
    ) {
        this.especialistaService = especialistaService;
        this.especialistaRepository = especialistaRepository;
        this.especialistaQueryService = especialistaQueryService;
    }

    /**
     * {@code POST  /especialistas} : Create a new especialista.
     *
     * @param especialistaDto the especialistaDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new especialistaDto, or with status {@code 400 (Bad Request)} if the especialista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EspecialistaDto> createEspecialista(@RequestBody EspecialistaDto especialistaDto) throws URISyntaxException {
        log.debug("REST request to save Especialista : {}", especialistaDto);
        if (especialistaDto.getId() != null) {
            throw new BadRequestAlertException("A new especialista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EspecialistaDto result = especialistaService.save(especialistaDto);
        return ResponseEntity
            .created(new URI("/api/especialistas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /especialistas/:id} : Updates an existing especialista.
     *
     * @param id the id of the especialistaDto to save.
     * @param especialistaDto the especialistaDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especialistaDto,
     * or with status {@code 400 (Bad Request)} if the especialistaDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the especialistaDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EspecialistaDto> updateEspecialista(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EspecialistaDto especialistaDto
    ) throws URISyntaxException {
        log.debug("REST request to update Especialista : {}, {}", id, especialistaDto);
        if (especialistaDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, especialistaDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!especialistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EspecialistaDto result = especialistaService.update(especialistaDto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, especialistaDto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /especialistas/:id} : Partial updates given fields of an existing especialista, field will ignore if it is null
     *
     * @param id the id of the especialistaDto to save.
     * @param especialistaDto the especialistaDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especialistaDto,
     * or with status {@code 400 (Bad Request)} if the especialistaDto is not valid,
     * or with status {@code 404 (Not Found)} if the especialistaDto is not found,
     * or with status {@code 500 (Internal Server Error)} if the especialistaDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EspecialistaDto> partialUpdateEspecialista(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EspecialistaDto especialistaDto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Especialista partially : {}, {}", id, especialistaDto);
        if (especialistaDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, especialistaDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!especialistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EspecialistaDto> result = especialistaService.partialUpdate(especialistaDto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, especialistaDto.getId().toString())
        );
    }

    /**
     * {@code GET  /especialistas} : get all the especialistas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of especialistas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EspecialistaDto>> getAllEspecialistas(
        EspecialistaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Especialistas by criteria: {}", criteria);

        Page<EspecialistaDto> page = especialistaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /especialistas/count} : count all the especialistas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEspecialistas(EspecialistaCriteria criteria) {
        log.debug("REST request to count Especialistas by criteria: {}", criteria);
        return ResponseEntity.ok().body(especialistaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /especialistas/:id} : get the "id" especialista.
     *
     * @param id the id of the especialistaDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the especialistaDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EspecialistaDto> getEspecialista(@PathVariable Long id) {
        log.debug("REST request to get Especialista : {}", id);
        Optional<EspecialistaDto> especialistaDto = especialistaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(especialistaDto);
    }

    /**
     * {@code DELETE  /especialistas/:id} : delete the "id" especialista.
     *
     * @param id the id of the especialistaDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspecialista(@PathVariable Long id) {
        log.debug("REST request to delete Especialista : {}", id);
        especialistaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
