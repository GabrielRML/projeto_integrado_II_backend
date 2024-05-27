package com.clinicallink.app.service.impl;

import com.clinicallink.app.domain.Especialidade;
import com.clinicallink.app.repository.EspecialidadeRepository;
import com.clinicallink.app.service.EspecialidadeService;
import com.clinicallink.app.service.dto.EspecialidadeDto;
import com.clinicallink.app.service.mapper.EspecialidadeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinicallink.app.domain.Especialidade}.
 */
@Service
@Transactional
public class EspecialidadeServiceImpl implements EspecialidadeService {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeServiceImpl.class);

    private final EspecialidadeRepository especialidadeRepository;

    private final EspecialidadeMapper especialidadeMapper;

    public EspecialidadeServiceImpl(EspecialidadeRepository especialidadeRepository, EspecialidadeMapper especialidadeMapper) {
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeMapper = especialidadeMapper;
    }

    @Override
    public EspecialidadeDto save(EspecialidadeDto especialidadeDto) {
        log.debug("Request to save Especialidade : {}", especialidadeDto);
        Especialidade especialidade = especialidadeMapper.toEntity(especialidadeDto);
        especialidade = especialidadeRepository.save(especialidade);
        return especialidadeMapper.toDto(especialidade);
    }

    @Override
    public EspecialidadeDto update(EspecialidadeDto especialidadeDto) {
        log.debug("Request to update Especialidade : {}", especialidadeDto);
        Especialidade especialidade = especialidadeMapper.toEntity(especialidadeDto);
        especialidade = especialidadeRepository.save(especialidade);
        return especialidadeMapper.toDto(especialidade);
    }

    @Override
    public Optional<EspecialidadeDto> partialUpdate(EspecialidadeDto especialidadeDto) {
        log.debug("Request to partially update Especialidade : {}", especialidadeDto);

        return especialidadeRepository
            .findById(especialidadeDto.getId())
            .map(existingEspecialidade -> {
                especialidadeMapper.partialUpdate(existingEspecialidade, especialidadeDto);

                return existingEspecialidade;
            })
            .map(especialidadeRepository::save)
            .map(especialidadeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EspecialidadeDto> findAll(Pageable pageable) {
        log.debug("Request to get all Especialidades");
        return especialidadeRepository.findAll(pageable).map(especialidadeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EspecialidadeDto> findOne(Long id) {
        log.debug("Request to get Especialidade : {}", id);
        return especialidadeRepository.findById(id).map(especialidadeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Especialidade : {}", id);
        especialidadeRepository.deleteById(id);
    }
}
