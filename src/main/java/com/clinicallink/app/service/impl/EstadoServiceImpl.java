package com.clinicallink.app.service.impl;

import com.clinicallink.app.domain.Estado;
import com.clinicallink.app.repository.EstadoRepository;
import com.clinicallink.app.service.EstadoService;
import com.clinicallink.app.service.dto.EstadoDto;
import com.clinicallink.app.service.mapper.EstadoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinicallink.app.domain.Estado}.
 */
@Service
@Transactional
public class EstadoServiceImpl implements EstadoService {

    private final Logger log = LoggerFactory.getLogger(EstadoServiceImpl.class);

    private final EstadoRepository estadoRepository;

    private final EstadoMapper estadoMapper;

    public EstadoServiceImpl(EstadoRepository estadoRepository, EstadoMapper estadoMapper) {
        this.estadoRepository = estadoRepository;
        this.estadoMapper = estadoMapper;
    }

    @Override
    public EstadoDto save(EstadoDto estadoDto) {
        log.debug("Request to save Estado : {}", estadoDto);
        Estado estado = estadoMapper.toEntity(estadoDto);
        estado = estadoRepository.save(estado);
        return estadoMapper.toDto(estado);
    }

    @Override
    public EstadoDto update(EstadoDto estadoDto) {
        log.debug("Request to update Estado : {}", estadoDto);
        Estado estado = estadoMapper.toEntity(estadoDto);
        estado = estadoRepository.save(estado);
        return estadoMapper.toDto(estado);
    }

    @Override
    public Optional<EstadoDto> partialUpdate(EstadoDto estadoDto) {
        log.debug("Request to partially update Estado : {}", estadoDto);

        return estadoRepository
            .findById(estadoDto.getId())
            .map(existingEstado -> {
                estadoMapper.partialUpdate(existingEstado, estadoDto);

                return existingEstado;
            })
            .map(estadoRepository::save)
            .map(estadoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstadoDto> findAll(Pageable pageable) {
        log.debug("Request to get all Estados");
        return estadoRepository.findAll(pageable).map(estadoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoDto> findOne(Long id) {
        log.debug("Request to get Estado : {}", id);
        return estadoRepository.findById(id).map(estadoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estado : {}", id);
        estadoRepository.deleteById(id);
    }
}
