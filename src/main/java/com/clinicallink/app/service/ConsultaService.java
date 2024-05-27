package com.clinicallink.app.service;

import com.clinicallink.app.service.dto.ConsultaDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.clinicallink.app.domain.Consulta}.
 */
public interface ConsultaService {
    /**
     * Save a consulta.
     *
     * @param consultaDto the entity to save.
     * @return the persisted entity.
     */
    ConsultaDto save(ConsultaDto consultaDto);

    /**
     * Updates a consulta.
     *
     * @param consultaDto the entity to update.
     * @return the persisted entity.
     */
    ConsultaDto update(ConsultaDto consultaDto);

    /**
     * Partially updates a consulta.
     *
     * @param consultaDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConsultaDto> partialUpdate(ConsultaDto consultaDto);

    /**
     * Get all the consultas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsultaDto> findAll(Pageable pageable);

    /**
     * Get the "id" consulta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsultaDto> findOne(Long id);

    /**
     * Delete the "id" consulta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
