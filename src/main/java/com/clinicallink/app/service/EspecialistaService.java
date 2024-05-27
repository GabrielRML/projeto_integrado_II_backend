package com.clinicallink.app.service;

import com.clinicallink.app.service.dto.EspecialistaDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.clinicallink.app.domain.Especialista}.
 */
public interface EspecialistaService {
    /**
     * Save a especialista.
     *
     * @param especialistaDto the entity to save.
     * @return the persisted entity.
     */
    EspecialistaDto save(EspecialistaDto especialistaDto);

    /**
     * Updates a especialista.
     *
     * @param especialistaDto the entity to update.
     * @return the persisted entity.
     */
    EspecialistaDto update(EspecialistaDto especialistaDto);

    /**
     * Partially updates a especialista.
     *
     * @param especialistaDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EspecialistaDto> partialUpdate(EspecialistaDto especialistaDto);

    /**
     * Get all the especialistas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EspecialistaDto> findAll(Pageable pageable);

    /**
     * Get the "id" especialista.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EspecialistaDto> findOne(Long id);

    /**
     * Delete the "id" especialista.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
