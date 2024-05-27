package com.clinicallink.app.service;

import com.clinicallink.app.service.dto.UniversidadeDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.clinicallink.app.domain.Universidade}.
 */
public interface UniversidadeService {
    /**
     * Save a universidade.
     *
     * @param universidadeDto the entity to save.
     * @return the persisted entity.
     */
    UniversidadeDto save(UniversidadeDto universidadeDto);

    /**
     * Updates a universidade.
     *
     * @param universidadeDto the entity to update.
     * @return the persisted entity.
     */
    UniversidadeDto update(UniversidadeDto universidadeDto);

    /**
     * Partially updates a universidade.
     *
     * @param universidadeDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UniversidadeDto> partialUpdate(UniversidadeDto universidadeDto);

    /**
     * Get all the universidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UniversidadeDto> findAll(Pageable pageable);

    /**
     * Get the "id" universidade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UniversidadeDto> findOne(Long id);

    /**
     * Delete the "id" universidade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
