package com.clinicallink.app.service;

import com.clinicallink.app.service.dto.CidadeDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.clinicallink.app.domain.Cidade}.
 */
public interface CidadeService {
    /**
     * Save a cidade.
     *
     * @param cidadeDto the entity to save.
     * @return the persisted entity.
     */
    CidadeDto save(CidadeDto cidadeDto);

    /**
     * Updates a cidade.
     *
     * @param cidadeDto the entity to update.
     * @return the persisted entity.
     */
    CidadeDto update(CidadeDto cidadeDto);

    /**
     * Partially updates a cidade.
     *
     * @param cidadeDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CidadeDto> partialUpdate(CidadeDto cidadeDto);

    /**
     * Get all the cidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CidadeDto> findAll(Pageable pageable);

    /**
     * Get the "id" cidade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CidadeDto> findOne(Long id);

    /**
     * Delete the "id" cidade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
