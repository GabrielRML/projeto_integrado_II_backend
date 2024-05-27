package com.clinicallink.app.service;

import com.clinicallink.app.service.dto.AvaliacaoDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.clinicallink.app.domain.Avaliacao}.
 */
public interface AvaliacaoService {
    /**
     * Save a avaliacao.
     *
     * @param avaliacaoDto the entity to save.
     * @return the persisted entity.
     */
    AvaliacaoDto save(AvaliacaoDto avaliacaoDto);

    /**
     * Updates a avaliacao.
     *
     * @param avaliacaoDto the entity to update.
     * @return the persisted entity.
     */
    AvaliacaoDto update(AvaliacaoDto avaliacaoDto);

    /**
     * Partially updates a avaliacao.
     *
     * @param avaliacaoDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AvaliacaoDto> partialUpdate(AvaliacaoDto avaliacaoDto);

    /**
     * Get all the avaliacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AvaliacaoDto> findAll(Pageable pageable);

    /**
     * Get all the AvaliacaoDto where Consulta is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<AvaliacaoDto> findAllWhereConsultaIsNull();

    /**
     * Get the "id" avaliacao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AvaliacaoDto> findOne(Long id);

    /**
     * Delete the "id" avaliacao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
