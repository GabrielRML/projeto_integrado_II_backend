package com.clinicallink.app.service.impl;

import com.clinicallink.app.domain.Consulta;
import com.clinicallink.app.repository.ConsultaRepository;
import com.clinicallink.app.service.ConsultaService;
import com.clinicallink.app.service.dto.ConsultaDto;
import com.clinicallink.app.service.mapper.ConsultaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinicallink.app.domain.Consulta}.
 */
@Service
@Transactional
public class ConsultaServiceImpl implements ConsultaService {

    private final Logger log = LoggerFactory.getLogger(ConsultaServiceImpl.class);

    private final ConsultaRepository consultaRepository;

    private final ConsultaMapper consultaMapper;

    public ConsultaServiceImpl(ConsultaRepository consultaRepository, ConsultaMapper consultaMapper) {
        this.consultaRepository = consultaRepository;
        this.consultaMapper = consultaMapper;
    }

    @Override
    public ConsultaDto save(ConsultaDto consultaDto) {
        log.debug("Request to save Consulta : {}", consultaDto);
        Consulta consulta = consultaMapper.toEntity(consultaDto);
        consulta = consultaRepository.save(consulta);
        return consultaMapper.toDto(consulta);
    }

    @Override
    public ConsultaDto update(ConsultaDto consultaDto) {
        log.debug("Request to update Consulta : {}", consultaDto);
        Consulta consulta = consultaMapper.toEntity(consultaDto);
        consulta = consultaRepository.save(consulta);
        return consultaMapper.toDto(consulta);
    }

    @Override
    public Optional<ConsultaDto> partialUpdate(ConsultaDto consultaDto) {
        log.debug("Request to partially update Consulta : {}", consultaDto);

        return consultaRepository
            .findById(consultaDto.getId())
            .map(existingConsulta -> {
                consultaMapper.partialUpdate(existingConsulta, consultaDto);

                return existingConsulta;
            })
            .map(consultaRepository::save)
            .map(consultaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultaDto> findAll(Pageable pageable) {
        log.debug("Request to get all Consultas");
        return consultaRepository.findAll(pageable).map(consultaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConsultaDto> findOne(Long id) {
        log.debug("Request to get Consulta : {}", id);
        return consultaRepository.findById(id).map(consultaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consulta : {}", id);
        consultaRepository.deleteById(id);
    }
}
