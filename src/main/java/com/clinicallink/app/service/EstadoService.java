package com.clinicallink.app.service;

import com.clinicallink.app.service.dto.EstadoDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.clinicallink.app.domain.Estado}.
 */
public interface EstadoService {
    /**
     * Save a estado.
     *
     * @param estadoDto the entity to save.
     * @return the persisted entity.
     */
    EstadoDto save(EstadoDto estadoDto);

    /**
     * Updates a estado.
     *
     * @param estadoDto the entity to update.
     * @return the persisted entity.
     */
    EstadoDto update(EstadoDto estadoDto);

    /**
     * Partially updates a estado.
     *
     * @param estadoDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstadoDto> partialUpdate(EstadoDto estadoDto);

    /**
     * Get all the estados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstadoDto> findAll(Pageable pageable);

    /**
     * Get the "id" estado.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstadoDto> findOne(Long id);

    /**
     * Delete the "id" estado.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
