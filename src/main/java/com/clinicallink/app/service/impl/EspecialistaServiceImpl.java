package com.clinicallink.app.service.impl;

import com.clinicallink.app.domain.Especialista;
import com.clinicallink.app.repository.EspecialistaRepository;
import com.clinicallink.app.service.EspecialistaService;
import com.clinicallink.app.service.dto.EspecialistaDto;
import com.clinicallink.app.service.mapper.EspecialistaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinicallink.app.domain.Especialista}.
 */
@Service
@Transactional
public class EspecialistaServiceImpl implements EspecialistaService {

    private final Logger log = LoggerFactory.getLogger(EspecialistaServiceImpl.class);

    private final EspecialistaRepository especialistaRepository;

    private final EspecialistaMapper especialistaMapper;

    public EspecialistaServiceImpl(EspecialistaRepository especialistaRepository, EspecialistaMapper especialistaMapper) {
        this.especialistaRepository = especialistaRepository;
        this.especialistaMapper = especialistaMapper;
    }

    @Override
    public EspecialistaDto save(EspecialistaDto especialistaDto) {
        log.debug("Request to save Especialista : {}", especialistaDto);
        Especialista especialista = especialistaMapper.toEntity(especialistaDto);
        especialista = especialistaRepository.save(especialista);
        return especialistaMapper.toDto(especialista);
    }

    @Override
    public EspecialistaDto update(EspecialistaDto especialistaDto) {
        log.debug("Request to update Especialista : {}", especialistaDto);
        Especialista especialista = especialistaMapper.toEntity(especialistaDto);
        especialista = especialistaRepository.save(especialista);
        return especialistaMapper.toDto(especialista);
    }

    @Override
    public Optional<EspecialistaDto> partialUpdate(EspecialistaDto especialistaDto) {
        log.debug("Request to partially update Especialista : {}", especialistaDto);

        return especialistaRepository
            .findById(especialistaDto.getId())
            .map(existingEspecialista -> {
                especialistaMapper.partialUpdate(existingEspecialista, especialistaDto);

                return existingEspecialista;
            })
            .map(especialistaRepository::save)
            .map(especialistaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EspecialistaDto> findAll(Pageable pageable) {
        log.debug("Request to get all Especialistas");
        return especialistaRepository.findAll(pageable).map(especialistaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EspecialistaDto> findOne(Long id) {
        log.debug("Request to get Especialista : {}", id);
        return especialistaRepository.findById(id).map(especialistaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Especialista : {}", id);
        especialistaRepository.deleteById(id);
    }
}
