package com.clinicallink.app.service;

import com.clinicallink.app.service.dto.UsuarioDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.clinicallink.app.domain.Usuario}.
 */
public interface UsuarioService {
    /**
     * Save a usuario.
     *
     * @param usuarioDto the entity to save.
     * @return the persisted entity.
     */
    UsuarioDto save(UsuarioDto usuarioDto);

    /**
     * Updates a usuario.
     *
     * @param usuarioDto the entity to update.
     * @return the persisted entity.
     */
    UsuarioDto update(UsuarioDto usuarioDto);

    /**
     * Partially updates a usuario.
     *
     * @param usuarioDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UsuarioDto> partialUpdate(UsuarioDto usuarioDto);

    /**
     * Get all the usuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UsuarioDto> findAll(Pageable pageable);

    /**
     * Get the "id" usuario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UsuarioDto> findOne(Long id);

    /**
     * Delete the "id" usuario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
