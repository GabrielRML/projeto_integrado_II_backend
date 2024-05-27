package com.clinicallink.app.web.rest;

import com.clinicallink.app.repository.CidadeRepository;
import com.clinicallink.app.service.CidadeQueryService;
import com.clinicallink.app.service.CidadeService;
import com.clinicallink.app.service.criteria.CidadeCriteria;
import com.clinicallink.app.service.dto.CidadeDto;
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
 * REST controller for managing {@link com.clinicallink.app.domain.Cidade}.
 */
@RestController
@RequestMapping("/api/cidades")
public class CidadeResource {

    private final Logger log = LoggerFactory.getLogger(CidadeResource.class);

    private static final String ENTITY_NAME = "cidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CidadeService cidadeService;

    private final CidadeRepository cidadeRepository;

    private final CidadeQueryService cidadeQueryService;

    public CidadeResource(CidadeService cidadeService, CidadeRepository cidadeRepository, CidadeQueryService cidadeQueryService) {
        this.cidadeService = cidadeService;
        this.cidadeRepository = cidadeRepository;
        this.cidadeQueryService = cidadeQueryService;
    }

    /**
     * {@code POST  /cidades} : Create a new cidade.
     *
     * @param cidadeDto the cidadeDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cidadeDto, or with status {@code 400 (Bad Request)} if the cidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CidadeDto> createCidade(@RequestBody CidadeDto cidadeDto) throws URISyntaxException {
        log.debug("REST request to save Cidade : {}", cidadeDto);
        if (cidadeDto.getId() != null) {
            throw new BadRequestAlertException("A new cidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CidadeDto result = cidadeService.save(cidadeDto);
        return ResponseEntity
            .created(new URI("/api/cidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cidades/:id} : Updates an existing cidade.
     *
     * @param id the id of the cidadeDto to save.
     * @param cidadeDto the cidadeDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cidadeDto,
     * or with status {@code 400 (Bad Request)} if the cidadeDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cidadeDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CidadeDto> updateCidade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CidadeDto cidadeDto
    ) throws URISyntaxException {
        log.debug("REST request to update Cidade : {}, {}", id, cidadeDto);
        if (cidadeDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cidadeDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CidadeDto result = cidadeService.update(cidadeDto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cidadeDto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cidades/:id} : Partial updates given fields of an existing cidade, field will ignore if it is null
     *
     * @param id the id of the cidadeDto to save.
     * @param cidadeDto the cidadeDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cidadeDto,
     * or with status {@code 400 (Bad Request)} if the cidadeDto is not valid,
     * or with status {@code 404 (Not Found)} if the cidadeDto is not found,
     * or with status {@code 500 (Internal Server Error)} if the cidadeDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CidadeDto> partialUpdateCidade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CidadeDto cidadeDto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cidade partially : {}, {}", id, cidadeDto);
        if (cidadeDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cidadeDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CidadeDto> result = cidadeService.partialUpdate(cidadeDto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cidadeDto.getId().toString())
        );
    }

    /**
     * {@code GET  /cidades} : get all the cidades.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cidades in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CidadeDto>> getAllCidades(
        CidadeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Cidades by criteria: {}", criteria);

        Page<CidadeDto> page = cidadeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cidades/count} : count all the cidades.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCidades(CidadeCriteria criteria) {
        log.debug("REST request to count Cidades by criteria: {}", criteria);
        return ResponseEntity.ok().body(cidadeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cidades/:id} : get the "id" cidade.
     *
     * @param id the id of the cidadeDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cidadeDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CidadeDto> getCidade(@PathVariable Long id) {
        log.debug("REST request to get Cidade : {}", id);
        Optional<CidadeDto> cidadeDto = cidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cidadeDto);
    }

    /**
     * {@code DELETE  /cidades/:id} : delete the "id" cidade.
     *
     * @param id the id of the cidadeDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCidade(@PathVariable Long id) {
        log.debug("REST request to delete Cidade : {}", id);
        cidadeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
