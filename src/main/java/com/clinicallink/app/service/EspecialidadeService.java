package com.clinicallink.app.service;

import com.clinicallink.app.service.dto.EspecialidadeDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.clinicallink.app.domain.Especialidade}.
 */
public interface EspecialidadeService {
    /**
     * Save a especialidade.
     *
     * @param especialidadeDto the entity to save.
     * @return the persisted entity.
     */
    EspecialidadeDto save(EspecialidadeDto especialidadeDto);

    /**
     * Updates a especialidade.
     *
     * @param especialidadeDto the entity to update.
     * @return the persisted entity.
     */
    EspecialidadeDto update(EspecialidadeDto especialidadeDto);

    /**
     * Partially updates a especialidade.
     *
     * @param especialidadeDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EspecialidadeDto> partialUpdate(EspecialidadeDto especialidadeDto);

    /**
     * Get all the especialidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EspecialidadeDto> findAll(Pageable pageable);

    /**
     * Get the "id" especialidade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EspecialidadeDto> findOne(Long id);

    /**
     * Delete the "id" especialidade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
