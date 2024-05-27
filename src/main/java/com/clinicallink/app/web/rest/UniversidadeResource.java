package com.clinicallink.app.web.rest;

import com.clinicallink.app.repository.UniversidadeRepository;
import com.clinicallink.app.service.UniversidadeQueryService;
import com.clinicallink.app.service.UniversidadeService;
import com.clinicallink.app.service.criteria.UniversidadeCriteria;
import com.clinicallink.app.service.dto.UniversidadeDto;
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
 * REST controller for managing {@link com.clinicallink.app.domain.Universidade}.
 */
@RestController
@RequestMapping("/api/universidades")
public class UniversidadeResource {

    private final Logger log = LoggerFactory.getLogger(UniversidadeResource.class);

    private static final String ENTITY_NAME = "universidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UniversidadeService universidadeService;

    private final UniversidadeRepository universidadeRepository;

    private final UniversidadeQueryService universidadeQueryService;

    public UniversidadeResource(
        UniversidadeService universidadeService,
        UniversidadeRepository universidadeRepository,
        UniversidadeQueryService universidadeQueryService
    ) {
        this.universidadeService = universidadeService;
        this.universidadeRepository = universidadeRepository;
        this.universidadeQueryService = universidadeQueryService;
    }

    /**
     * {@code POST  /universidades} : Create a new universidade.
     *
     * @param universidadeDto the universidadeDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new universidadeDto, or with status {@code 400 (Bad Request)} if the universidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UniversidadeDto> createUniversidade(@RequestBody UniversidadeDto universidadeDto) throws URISyntaxException {
        log.debug("REST request to save Universidade : {}", universidadeDto);
        if (universidadeDto.getId() != null) {
            throw new BadRequestAlertException("A new universidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UniversidadeDto result = universidadeService.save(universidadeDto);
        return ResponseEntity
            .created(new URI("/api/universidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /universidades/:id} : Updates an existing universidade.
     *
     * @param id the id of the universidadeDto to save.
     * @param universidadeDto the universidadeDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated universidadeDto,
     * or with status {@code 400 (Bad Request)} if the universidadeDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the universidadeDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UniversidadeDto> updateUniversidade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UniversidadeDto universidadeDto
    ) throws URISyntaxException {
        log.debug("REST request to update Universidade : {}, {}", id, universidadeDto);
        if (universidadeDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, universidadeDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!universidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UniversidadeDto result = universidadeService.update(universidadeDto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, universidadeDto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /universidades/:id} : Partial updates given fields of an existing universidade, field will ignore if it is null
     *
     * @param id the id of the universidadeDto to save.
     * @param universidadeDto the universidadeDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated universidadeDto,
     * or with status {@code 400 (Bad Request)} if the universidadeDto is not valid,
     * or with status {@code 404 (Not Found)} if the universidadeDto is not found,
     * or with status {@code 500 (Internal Server Error)} if the universidadeDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UniversidadeDto> partialUpdateUniversidade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UniversidadeDto universidadeDto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Universidade partially : {}, {}", id, universidadeDto);
        if (universidadeDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, universidadeDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!universidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UniversidadeDto> result = universidadeService.partialUpdate(universidadeDto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, universidadeDto.getId().toString())
        );
    }

    /**
     * {@code GET  /universidades} : get all the universidades.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of universidades in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UniversidadeDto>> getAllUniversidades(
        UniversidadeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Universidades by criteria: {}", criteria);

        Page<UniversidadeDto> page = universidadeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /universidades/count} : count all the universidades.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countUniversidades(UniversidadeCriteria criteria) {
        log.debug("REST request to count Universidades by criteria: {}", criteria);
        return ResponseEntity.ok().body(universidadeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /universidades/:id} : get the "id" universidade.
     *
     * @param id the id of the universidadeDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the universidadeDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UniversidadeDto> getUniversidade(@PathVariable Long id) {
        log.debug("REST request to get Universidade : {}", id);
        Optional<UniversidadeDto> universidadeDto = universidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(universidadeDto);
    }

    /**
     * {@code DELETE  /universidades/:id} : delete the "id" universidade.
     *
     * @param id the id of the universidadeDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversidade(@PathVariable Long id) {
        log.debug("REST request to delete Universidade : {}", id);
        universidadeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
