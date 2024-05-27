package com.clinicallink.app.service;

import com.clinicallink.app.domain.*; // for static metamodels
import com.clinicallink.app.domain.Avaliacao;
import com.clinicallink.app.repository.AvaliacaoRepository;
import com.clinicallink.app.service.criteria.AvaliacaoCriteria;
import com.clinicallink.app.service.dto.AvaliacaoDto;
import com.clinicallink.app.service.mapper.AvaliacaoMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Avaliacao} entities in the database.
 * The main input is a {@link AvaliacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AvaliacaoDto} or a {@link Page} of {@link AvaliacaoDto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AvaliacaoQueryService extends QueryService<Avaliacao> {

    private final Logger log = LoggerFactory.getLogger(AvaliacaoQueryService.class);

    private final AvaliacaoRepository avaliacaoRepository;

    private final AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoQueryService(AvaliacaoRepository avaliacaoRepository, AvaliacaoMapper avaliacaoMapper) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    /**
     * Return a {@link List} of {@link AvaliacaoDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AvaliacaoDto> findByCriteria(AvaliacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Avaliacao> specification = createSpecification(criteria);
        return avaliacaoMapper.toDto(avaliacaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AvaliacaoDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AvaliacaoDto> findByCriteria(AvaliacaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Avaliacao> specification = createSpecification(criteria);
        return avaliacaoRepository.findAll(specification, page).map(avaliacaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AvaliacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Avaliacao> specification = createSpecification(criteria);
        return avaliacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link AvaliacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Avaliacao> createSpecification(AvaliacaoCriteria criteria) {
        Specification<Avaliacao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Avaliacao_.id));
            }
            if (criteria.getAssessment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssessment(), Avaliacao_.assessment));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNote(), Avaliacao_.note));
            }
            if (criteria.getConsultaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getConsultaId(),
                            root -> root.join(Avaliacao_.consulta, JoinType.LEFT).get(Consulta_.id)
                        )
                    );
            }
            if (criteria.getAvaliadoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAvaliadoId(),
                            root -> root.join(Avaliacao_.avaliado, JoinType.LEFT).get(Especialista_.id)
                        )
                    );
            }
            if (criteria.getAvaliadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAvaliadorId(),
                            root -> root.join(Avaliacao_.avaliador, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
