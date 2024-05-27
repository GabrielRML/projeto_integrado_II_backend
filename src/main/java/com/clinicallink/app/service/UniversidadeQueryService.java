package com.clinicallink.app.service;

import com.clinicallink.app.domain.*; // for static metamodels
import com.clinicallink.app.domain.Universidade;
import com.clinicallink.app.repository.UniversidadeRepository;
import com.clinicallink.app.service.criteria.UniversidadeCriteria;
import com.clinicallink.app.service.dto.UniversidadeDto;
import com.clinicallink.app.service.mapper.UniversidadeMapper;
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
 * Service for executing complex queries for {@link Universidade} entities in the database.
 * The main input is a {@link UniversidadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UniversidadeDto} or a {@link Page} of {@link UniversidadeDto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UniversidadeQueryService extends QueryService<Universidade> {

    private final Logger log = LoggerFactory.getLogger(UniversidadeQueryService.class);

    private final UniversidadeRepository universidadeRepository;

    private final UniversidadeMapper universidadeMapper;

    public UniversidadeQueryService(UniversidadeRepository universidadeRepository, UniversidadeMapper universidadeMapper) {
        this.universidadeRepository = universidadeRepository;
        this.universidadeMapper = universidadeMapper;
    }

    /**
     * Return a {@link List} of {@link UniversidadeDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UniversidadeDto> findByCriteria(UniversidadeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Universidade> specification = createSpecification(criteria);
        return universidadeMapper.toDto(universidadeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UniversidadeDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UniversidadeDto> findByCriteria(UniversidadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Universidade> specification = createSpecification(criteria);
        return universidadeRepository.findAll(specification, page).map(universidadeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UniversidadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Universidade> specification = createSpecification(criteria);
        return universidadeRepository.count(specification);
    }

    /**
     * Function to convert {@link UniversidadeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Universidade> createSpecification(UniversidadeCriteria criteria) {
        Specification<Universidade> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Universidade_.id));
            }
            if (criteria.getCnpj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCnpj(), Universidade_.cnpj));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Universidade_.name));
            }
            if (criteria.getCep() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCep(), Universidade_.cep));
            }
            if (criteria.getEspecialistaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEspecialistaId(),
                            root -> root.join(Universidade_.especialistas, JoinType.LEFT).get(Especialista_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
