package com.clinicallink.app.web.rest;

import com.clinicallink.app.repository.AvaliacaoRepository;
import com.clinicallink.app.service.AvaliacaoQueryService;
import com.clinicallink.app.service.AvaliacaoService;
import com.clinicallink.app.service.criteria.AvaliacaoCriteria;
import com.clinicallink.app.service.dto.AvaliacaoDto;
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
 * REST controller for managing {@link com.clinicallink.app.domain.Avaliacao}.
 */
@RestController
@RequestMapping("/api/avaliacaos")
public class AvaliacaoResource {

    private final Logger log = LoggerFactory.getLogger(AvaliacaoResource.class);

    private static final String ENTITY_NAME = "avaliacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvaliacaoService avaliacaoService;

    private final AvaliacaoRepository avaliacaoRepository;

    private final AvaliacaoQueryService avaliacaoQueryService;

    public AvaliacaoResource(
        AvaliacaoService avaliacaoService,
        AvaliacaoRepository avaliacaoRepository,
        AvaliacaoQueryService avaliacaoQueryService
    ) {
        this.avaliacaoService = avaliacaoService;
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoQueryService = avaliacaoQueryService;
    }

    /**
     * {@code POST  /avaliacaos} : Create a new avaliacao.
     *
     * @param avaliacaoDto the avaliacaoDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avaliacaoDto, or with status {@code 400 (Bad Request)} if the avaliacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AvaliacaoDto> createAvaliacao(@RequestBody AvaliacaoDto avaliacaoDto) throws URISyntaxException {
        log.debug("REST request to save Avaliacao : {}", avaliacaoDto);
        if (avaliacaoDto.getId() != null) {
            throw new BadRequestAlertException("A new avaliacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvaliacaoDto result = avaliacaoService.save(avaliacaoDto);
        return ResponseEntity
            .created(new URI("/api/avaliacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avaliacaos/:id} : Updates an existing avaliacao.
     *
     * @param id the id of the avaliacaoDto to save.
     * @param avaliacaoDto the avaliacaoDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avaliacaoDto,
     * or with status {@code 400 (Bad Request)} if the avaliacaoDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avaliacaoDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoDto> updateAvaliacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AvaliacaoDto avaliacaoDto
    ) throws URISyntaxException {
        log.debug("REST request to update Avaliacao : {}, {}", id, avaliacaoDto);
        if (avaliacaoDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avaliacaoDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avaliacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AvaliacaoDto result = avaliacaoService.update(avaliacaoDto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avaliacaoDto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /avaliacaos/:id} : Partial updates given fields of an existing avaliacao, field will ignore if it is null
     *
     * @param id the id of the avaliacaoDto to save.
     * @param avaliacaoDto the avaliacaoDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avaliacaoDto,
     * or with status {@code 400 (Bad Request)} if the avaliacaoDto is not valid,
     * or with status {@code 404 (Not Found)} if the avaliacaoDto is not found,
     * or with status {@code 500 (Internal Server Error)} if the avaliacaoDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AvaliacaoDto> partialUpdateAvaliacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AvaliacaoDto avaliacaoDto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Avaliacao partially : {}, {}", id, avaliacaoDto);
        if (avaliacaoDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avaliacaoDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avaliacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AvaliacaoDto> result = avaliacaoService.partialUpdate(avaliacaoDto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avaliacaoDto.getId().toString())
        );
    }

    /**
     * {@code GET  /avaliacaos} : get all the avaliacaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avaliacaos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AvaliacaoDto>> getAllAvaliacaos(
        AvaliacaoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Avaliacaos by criteria: {}", criteria);

        Page<AvaliacaoDto> page = avaliacaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avaliacaos/count} : count all the avaliacaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAvaliacaos(AvaliacaoCriteria criteria) {
        log.debug("REST request to count Avaliacaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(avaliacaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /avaliacaos/:id} : get the "id" avaliacao.
     *
     * @param id the id of the avaliacaoDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avaliacaoDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoDto> getAvaliacao(@PathVariable Long id) {
        log.debug("REST request to get Avaliacao : {}", id);
        Optional<AvaliacaoDto> avaliacaoDto = avaliacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avaliacaoDto);
    }

    /**
     * {@code DELETE  /avaliacaos/:id} : delete the "id" avaliacao.
     *
     * @param id the id of the avaliacaoDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable Long id) {
        log.debug("REST request to delete Avaliacao : {}", id);
        avaliacaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
