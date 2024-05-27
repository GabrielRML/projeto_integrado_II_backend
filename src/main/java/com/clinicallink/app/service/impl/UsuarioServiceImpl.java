package com.clinicallink.app.service.impl;

import com.clinicallink.app.domain.Usuario;
import com.clinicallink.app.repository.UsuarioRepository;
import com.clinicallink.app.service.UsuarioService;
import com.clinicallink.app.service.dto.UsuarioDto;
import com.clinicallink.app.service.mapper.UsuarioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinicallink.app.domain.Usuario}.
 */
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public UsuarioDto save(UsuarioDto usuarioDto) {
        log.debug("Request to save Usuario : {}", usuarioDto);
        Usuario usuario = usuarioMapper.toEntity(usuarioDto);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioDto update(UsuarioDto usuarioDto) {
        log.debug("Request to update Usuario : {}", usuarioDto);
        Usuario usuario = usuarioMapper.toEntity(usuarioDto);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public Optional<UsuarioDto> partialUpdate(UsuarioDto usuarioDto) {
        log.debug("Request to partially update Usuario : {}", usuarioDto);

        return usuarioRepository
            .findById(usuarioDto.getId())
            .map(existingUsuario -> {
                usuarioMapper.partialUpdate(existingUsuario, usuarioDto);

                return existingUsuario;
            })
            .map(usuarioRepository::save)
            .map(usuarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioDto> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarios");
        return usuarioRepository.findAll(pageable).map(usuarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDto> findOne(Long id) {
        log.debug("Request to get Usuario : {}", id);
        return usuarioRepository.findById(id).map(usuarioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Usuario : {}", id);
        usuarioRepository.deleteById(id);
    }
}
