package com.clinicallink.app.service.impl;

import com.clinicallink.app.domain.Universidade;
import com.clinicallink.app.repository.UniversidadeRepository;
import com.clinicallink.app.service.UniversidadeService;
import com.clinicallink.app.service.dto.UniversidadeDto;
import com.clinicallink.app.service.mapper.UniversidadeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinicallink.app.domain.Universidade}.
 */
@Service
@Transactional
public class UniversidadeServiceImpl implements UniversidadeService {

    private final Logger log = LoggerFactory.getLogger(UniversidadeServiceImpl.class);

    private final UniversidadeRepository universidadeRepository;

    private final UniversidadeMapper universidadeMapper;

    public UniversidadeServiceImpl(UniversidadeRepository universidadeRepository, UniversidadeMapper universidadeMapper) {
        this.universidadeRepository = universidadeRepository;
        this.universidadeMapper = universidadeMapper;
    }

    @Override
    public UniversidadeDto save(UniversidadeDto universidadeDto) {
        log.debug("Request to save Universidade : {}", universidadeDto);
        Universidade universidade = universidadeMapper.toEntity(universidadeDto);
        universidade = universidadeRepository.save(universidade);
        return universidadeMapper.toDto(universidade);
    }

    @Override
    public UniversidadeDto update(UniversidadeDto universidadeDto) {
        log.debug("Request to update Universidade : {}", universidadeDto);
        Universidade universidade = universidadeMapper.toEntity(universidadeDto);
        universidade = universidadeRepository.save(universidade);
        return universidadeMapper.toDto(universidade);
    }

    @Override
    public Optional<UniversidadeDto> partialUpdate(UniversidadeDto universidadeDto) {
        log.debug("Request to partially update Universidade : {}", universidadeDto);

        return universidadeRepository
            .findById(universidadeDto.getId())
            .map(existingUniversidade -> {
                universidadeMapper.partialUpdate(existingUniversidade, universidadeDto);

                return existingUniversidade;
            })
            .map(universidadeRepository::save)
            .map(universidadeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UniversidadeDto> findAll(Pageable pageable) {
        log.debug("Request to get all Universidades");
        return universidadeRepository.findAll(pageable).map(universidadeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UniversidadeDto> findOne(Long id) {
        log.debug("Request to get Universidade : {}", id);
        return universidadeRepository.findById(id).map(universidadeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Universidade : {}", id);
        universidadeRepository.deleteById(id);
    }
}
