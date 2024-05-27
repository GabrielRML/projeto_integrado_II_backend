package com.clinicallink.app.service;

import com.clinicallink.app.domain.*; // for static metamodels
import com.clinicallink.app.domain.Consulta;
import com.clinicallink.app.repository.ConsultaRepository;
import com.clinicallink.app.service.criteria.ConsultaCriteria;
import com.clinicallink.app.service.dto.ConsultaDto;
import com.clinicallink.app.service.mapper.ConsultaMapper;
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
 * Service for executing complex queries for {@link Consulta} entities in the database.
 * The main input is a {@link ConsultaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConsultaDto} or a {@link Page} of {@link ConsultaDto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConsultaQueryService extends QueryService<Consulta> {

    private final Logger log = LoggerFactory.getLogger(ConsultaQueryService.class);

    private final ConsultaRepository consultaRepository;

    private final ConsultaMapper consultaMapper;

    public ConsultaQueryService(ConsultaRepository consultaRepository, ConsultaMapper consultaMapper) {
        this.consultaRepository = consultaRepository;
        this.consultaMapper = consultaMapper;
    }

    /**
     * Return a {@link List} of {@link ConsultaDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConsultaDto> findByCriteria(ConsultaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Consulta> specification = createSpecification(criteria);
        return consultaMapper.toDto(consultaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConsultaDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsultaDto> findByCriteria(ConsultaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Consulta> specification = createSpecification(criteria);
        return consultaRepository.findAll(specification, page).map(consultaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConsultaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Consulta> specification = createSpecification(criteria);
        return consultaRepository.count(specification);
    }

    /**
     * Function to convert {@link ConsultaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Consulta> createSpecification(ConsultaCriteria criteria) {
        Specification<Consulta> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Consulta_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Consulta_.date));
            }
            if (criteria.getReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReason(), Consulta_.reason));
            }
            if (criteria.getLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLink(), Consulta_.link));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Consulta_.status));
            }
            if (criteria.getAvaliacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAvaliacaoId(),
                            root -> root.join(Consulta_.avaliacao, JoinType.LEFT).get(Avaliacao_.id)
                        )
                    );
            }
            if (criteria.getPrestadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrestadorId(),
                            root -> root.join(Consulta_.prestador, JoinType.LEFT).get(Especialista_.id)
                        )
                    );
            }
            if (criteria.getClienteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getClienteId(), root -> root.join(Consulta_.cliente, JoinType.LEFT).get(Usuario_.id))
                    );
            }
        }
        return specification;
    }
}
