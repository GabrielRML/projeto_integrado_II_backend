package com.clinicallink.app.service;

import com.clinicallink.app.domain.*; // for static metamodels
import com.clinicallink.app.domain.Especialista;
import com.clinicallink.app.repository.EspecialistaRepository;
import com.clinicallink.app.service.criteria.EspecialistaCriteria;
import com.clinicallink.app.service.dto.EspecialistaDto;
import com.clinicallink.app.service.mapper.EspecialistaMapper;
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
 * Service for executing complex queries for {@link Especialista} entities in the database.
 * The main input is a {@link EspecialistaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EspecialistaDto} or a {@link Page} of {@link EspecialistaDto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EspecialistaQueryService extends QueryService<Especialista> {

    private final Logger log = LoggerFactory.getLogger(EspecialistaQueryService.class);

    private final EspecialistaRepository especialistaRepository;

    private final EspecialistaMapper especialistaMapper;

    public EspecialistaQueryService(EspecialistaRepository especialistaRepository, EspecialistaMapper especialistaMapper) {
        this.especialistaRepository = especialistaRepository;
        this.especialistaMapper = especialistaMapper;
    }

    /**
     * Return a {@link List} of {@link EspecialistaDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EspecialistaDto> findByCriteria(EspecialistaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Especialista> specification = createSpecification(criteria);
        return especialistaMapper.toDto(especialistaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EspecialistaDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EspecialistaDto> findByCriteria(EspecialistaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Especialista> specification = createSpecification(criteria);
        return especialistaRepository.findAll(specification, page).map(especialistaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EspecialistaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Especialista> specification = createSpecification(criteria);
        return especialistaRepository.count(specification);
    }

    /**
     * Function to convert {@link EspecialistaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Especialista> createSpecification(EspecialistaCriteria criteria) {
        Specification<Especialista> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Especialista_.id));
            }
            if (criteria.getCpf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCpf(), Especialista_.cpf));
            }
            if (criteria.getIdentification() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdentification(), Especialista_.identification));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthDate(), Especialista_.birthDate));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Especialista_.description));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Especialista_.price));
            }
            if (criteria.getTimeSession() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeSession(), Especialista_.timeSession));
            }
            if (criteria.getSpecialistType() != null) {
                specification = specification.and(buildSpecification(criteria.getSpecialistType(), Especialista_.specialistType));
            }
            if (criteria.getInternalUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInternalUserId(),
                            root -> root.join(Especialista_.internalUser, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getEspecialidadeEspecialistaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEspecialidadeEspecialistaId(),
                            root -> root.join(Especialista_.especialidadeEspecialistas, JoinType.LEFT).get(EspecialidadeEspecialista_.id)
                        )
                    );
            }
            if (criteria.getAvaliacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAvaliacaoId(),
                            root -> root.join(Especialista_.avaliacaos, JoinType.LEFT).get(Avaliacao_.id)
                        )
                    );
            }
            if (criteria.getConsultaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getConsultaId(),
                            root -> root.join(Especialista_.consultas, JoinType.LEFT).get(Consulta_.id)
                        )
                    );
            }
            if (criteria.getEspecialistaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEspecialistaId(),
                            root -> root.join(Especialista_.especialistas, JoinType.LEFT).get(Especialista_.id)
                        )
                    );
            }
            if (criteria.getEstadoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEstadoId(), root -> root.join(Especialista_.estado, JoinType.LEFT).get(Estado_.id))
                    );
            }
            if (criteria.getCidadeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCidadeId(), root -> root.join(Especialista_.cidade, JoinType.LEFT).get(Cidade_.id))
                    );
            }
            if (criteria.getUniversidadeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUniversidadeId(),
                            root -> root.join(Especialista_.universidade, JoinType.LEFT).get(Universidade_.id)
                        )
                    );
            }
            if (criteria.getSupervisorIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSupervisorIdId(),
                            root -> root.join(Especialista_.supervisorId, JoinType.LEFT).get(Especialista_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
