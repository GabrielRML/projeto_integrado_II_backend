package com.clinicallink.app.service;

import com.clinicallink.app.service.dto.EspecialidadeEspecialistaDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.clinicallink.app.domain.EspecialidadeEspecialista}.
 */
public interface EspecialidadeEspecialistaService {
    /**
     * Save a especialidadeEspecialista.
     *
     * @param especialidadeEspecialistaDto the entity to save.
     * @return the persisted entity.
     */
    EspecialidadeEspecialistaDto save(EspecialidadeEspecialistaDto especialidadeEspecialistaDto);

    /**
     * Updates a especialidadeEspecialista.
     *
     * @param especialidadeEspecialistaDto the entity to update.
     * @return the persisted entity.
     */
    EspecialidadeEspecialistaDto update(EspecialidadeEspecialistaDto especialidadeEspecialistaDto);

    /**
     * Partially updates a especialidadeEspecialista.
     *
     * @param especialidadeEspecialistaDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EspecialidadeEspecialistaDto> partialUpdate(EspecialidadeEspecialistaDto especialidadeEspecialistaDto);

    /**
     * Get all the especialidadeEspecialistas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EspecialidadeEspecialistaDto> findAll(Pageable pageable);

    /**
     * Get the "id" especialidadeEspecialista.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EspecialidadeEspecialistaDto> findOne(Long id);

    /**
     * Delete the "id" especialidadeEspecialista.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
