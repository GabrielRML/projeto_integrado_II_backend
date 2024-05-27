package com.clinicallink.app.service.impl;

import com.clinicallink.app.domain.EspecialidadeEspecialista;
import com.clinicallink.app.repository.EspecialidadeEspecialistaRepository;
import com.clinicallink.app.service.EspecialidadeEspecialistaService;
import com.clinicallink.app.service.dto.EspecialidadeEspecialistaDto;
import com.clinicallink.app.service.mapper.EspecialidadeEspecialistaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinicallink.app.domain.EspecialidadeEspecialista}.
 */
@Service
@Transactional
public class EspecialidadeEspecialistaServiceImpl implements EspecialidadeEspecialistaService {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeEspecialistaServiceImpl.class);

    private final EspecialidadeEspecialistaRepository especialidadeEspecialistaRepository;

    private final EspecialidadeEspecialistaMapper especialidadeEspecialistaMapper;

    public EspecialidadeEspecialistaServiceImpl(
        EspecialidadeEspecialistaRepository especialidadeEspecialistaRepository,
        EspecialidadeEspecialistaMapper especialidadeEspecialistaMapper
    ) {
        this.especialidadeEspecialistaRepository = especialidadeEspecialistaRepository;
        this.especialidadeEspecialistaMapper = especialidadeEspecialistaMapper;
    }

    @Override
    public EspecialidadeEspecialistaDto save(EspecialidadeEspecialistaDto especialidadeEspecialistaDto) {
        log.debug("Request to save EspecialidadeEspecialista : {}", especialidadeEspecialistaDto);
        EspecialidadeEspecialista especialidadeEspecialista = especialidadeEspecialistaMapper.toEntity(especialidadeEspecialistaDto);
        especialidadeEspecialista = especialidadeEspecialistaRepository.save(especialidadeEspecialista);
        return especialidadeEspecialistaMapper.toDto(especialidadeEspecialista);
    }

    @Override
    public EspecialidadeEspecialistaDto update(EspecialidadeEspecialistaDto especialidadeEspecialistaDto) {
        log.debug("Request to update EspecialidadeEspecialista : {}", especialidadeEspecialistaDto);
        EspecialidadeEspecialista especialidadeEspecialista = especialidadeEspecialistaMapper.toEntity(especialidadeEspecialistaDto);
        especialidadeEspecialista = especialidadeEspecialistaRepository.save(especialidadeEspecialista);
        return especialidadeEspecialistaMapper.toDto(especialidadeEspecialista);
    }

    @Override
    public Optional<EspecialidadeEspecialistaDto> partialUpdate(EspecialidadeEspecialistaDto especialidadeEspecialistaDto) {
        log.debug("Request to partially update EspecialidadeEspecialista : {}", especialidadeEspecialistaDto);

        return especialidadeEspecialistaRepository
            .findById(especialidadeEspecialistaDto.getId())
            .map(existingEspecialidadeEspecialista -> {
                especialidadeEspecialistaMapper.partialUpdate(existingEspecialidadeEspecialista, especialidadeEspecialistaDto);

                return existingEspecialidadeEspecialista;
            })
            .map(especialidadeEspecialistaRepository::save)
            .map(especialidadeEspecialistaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EspecialidadeEspecialistaDto> findAll(Pageable pageable) {
        log.debug("Request to get all EspecialidadeEspecialistas");
        return especialidadeEspecialistaRepository.findAll(pageable).map(especialidadeEspecialistaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EspecialidadeEspecialistaDto> findOne(Long id) {
        log.debug("Request to get EspecialidadeEspecialista : {}", id);
        return especialidadeEspecialistaRepository.findById(id).map(especialidadeEspecialistaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EspecialidadeEspecialista : {}", id);
        especialidadeEspecialistaRepository.deleteById(id);
    }
}
