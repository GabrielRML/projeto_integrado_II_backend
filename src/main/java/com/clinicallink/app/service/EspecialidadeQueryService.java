package com.clinicallink.app.service;

import com.clinicallink.app.domain.*; // for static metamodels
import com.clinicallink.app.domain.Especialidade;
import com.clinicallink.app.repository.EspecialidadeRepository;
import com.clinicallink.app.service.criteria.EspecialidadeCriteria;
import com.clinicallink.app.service.dto.EspecialidadeDto;
import com.clinicallink.app.service.mapper.EspecialidadeMapper;
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
 * Service for executing complex queries for {@link Especialidade} entities in the database.
 * The main input is a {@link EspecialidadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EspecialidadeDto} or a {@link Page} of {@link EspecialidadeDto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EspecialidadeQueryService extends QueryService<Especialidade> {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeQueryService.class);

    private final EspecialidadeRepository especialidadeRepository;

    private final EspecialidadeMapper especialidadeMapper;

    public EspecialidadeQueryService(EspecialidadeRepository especialidadeRepository, EspecialidadeMapper especialidadeMapper) {
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeMapper = especialidadeMapper;
    }

    /**
     * Return a {@link List} of {@link EspecialidadeDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EspecialidadeDto> findByCriteria(EspecialidadeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Especialidade> specification = createSpecification(criteria);
        return especialidadeMapper.toDto(especialidadeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EspecialidadeDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EspecialidadeDto> findByCriteria(EspecialidadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Especialidade> specification = createSpecification(criteria);
        return especialidadeRepository.findAll(specification, page).map(especialidadeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EspecialidadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Especialidade> specification = createSpecification(criteria);
        return especialidadeRepository.count(specification);
    }

    /**
     * Function to convert {@link EspecialidadeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Especialidade> createSpecification(EspecialidadeCriteria criteria) {
        Specification<Especialidade> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Especialidade_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Especialidade_.name));
            }
            if (criteria.getEspecialidadeEspecialistaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEspecialidadeEspecialistaId(),
                            root -> root.join(Especialidade_.especialidadeEspecialistas, JoinType.LEFT).get(EspecialidadeEspecialista_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
