package com.clinicallink.app.service;

import com.clinicallink.app.domain.*; // for static metamodels
import com.clinicallink.app.domain.EspecialidadeEspecialista;
import com.clinicallink.app.repository.EspecialidadeEspecialistaRepository;
import com.clinicallink.app.service.criteria.EspecialidadeEspecialistaCriteria;
import com.clinicallink.app.service.dto.EspecialidadeEspecialistaDto;
import com.clinicallink.app.service.mapper.EspecialidadeEspecialistaMapper;
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
 * Service for executing complex queries for {@link EspecialidadeEspecialista} entities in the database.
 * The main input is a {@link EspecialidadeEspecialistaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EspecialidadeEspecialistaDto} or a {@link Page} of {@link EspecialidadeEspecialistaDto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EspecialidadeEspecialistaQueryService extends QueryService<EspecialidadeEspecialista> {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeEspecialistaQueryService.class);

    private final EspecialidadeEspecialistaRepository especialidadeEspecialistaRepository;

    private final EspecialidadeEspecialistaMapper especialidadeEspecialistaMapper;

    public EspecialidadeEspecialistaQueryService(
        EspecialidadeEspecialistaRepository especialidadeEspecialistaRepository,
        EspecialidadeEspecialistaMapper especialidadeEspecialistaMapper
    ) {
        this.especialidadeEspecialistaRepository = especialidadeEspecialistaRepository;
        this.especialidadeEspecialistaMapper = especialidadeEspecialistaMapper;
    }

    /**
     * Return a {@link List} of {@link EspecialidadeEspecialistaDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EspecialidadeEspecialistaDto> findByCriteria(EspecialidadeEspecialistaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EspecialidadeEspecialista> specification = createSpecification(criteria);
        return especialidadeEspecialistaMapper.toDto(especialidadeEspecialistaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EspecialidadeEspecialistaDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EspecialidadeEspecialistaDto> findByCriteria(EspecialidadeEspecialistaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EspecialidadeEspecialista> specification = createSpecification(criteria);
        return especialidadeEspecialistaRepository.findAll(specification, page).map(especialidadeEspecialistaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EspecialidadeEspecialistaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EspecialidadeEspecialista> specification = createSpecification(criteria);
        return especialidadeEspecialistaRepository.count(specification);
    }

    /**
     * Function to convert {@link EspecialidadeEspecialistaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EspecialidadeEspecialista> createSpecification(EspecialidadeEspecialistaCriteria criteria) {
        Specification<EspecialidadeEspecialista> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EspecialidadeEspecialista_.id));
            }
            if (criteria.getEspecialidadeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEspecialidadeId(),
                            root -> root.join(EspecialidadeEspecialista_.especialidade, JoinType.LEFT).get(Especialidade_.id)
                        )
                    );
            }
            if (criteria.getEspecialistaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEspecialistaId(),
                            root -> root.join(EspecialidadeEspecialista_.especialista, JoinType.LEFT).get(Especialista_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
