package com.clinicallink.app.service.impl;

import com.clinicallink.app.domain.Cidade;
import com.clinicallink.app.repository.CidadeRepository;
import com.clinicallink.app.service.CidadeService;
import com.clinicallink.app.service.dto.CidadeDto;
import com.clinicallink.app.service.mapper.CidadeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinicallink.app.domain.Cidade}.
 */
@Service
@Transactional
public class CidadeServiceImpl implements CidadeService {

    private final Logger log = LoggerFactory.getLogger(CidadeServiceImpl.class);

    private final CidadeRepository cidadeRepository;

    private final CidadeMapper cidadeMapper;

    public CidadeServiceImpl(CidadeRepository cidadeRepository, CidadeMapper cidadeMapper) {
        this.cidadeRepository = cidadeRepository;
        this.cidadeMapper = cidadeMapper;
    }

    @Override
    public CidadeDto save(CidadeDto cidadeDto) {
        log.debug("Request to save Cidade : {}", cidadeDto);
        Cidade cidade = cidadeMapper.toEntity(cidadeDto);
        cidade = cidadeRepository.save(cidade);
        return cidadeMapper.toDto(cidade);
    }

    @Override
    public CidadeDto update(CidadeDto cidadeDto) {
        log.debug("Request to update Cidade : {}", cidadeDto);
        Cidade cidade = cidadeMapper.toEntity(cidadeDto);
        cidade = cidadeRepository.save(cidade);
        return cidadeMapper.toDto(cidade);
    }

    @Override
    public Optional<CidadeDto> partialUpdate(CidadeDto cidadeDto) {
        log.debug("Request to partially update Cidade : {}", cidadeDto);

        return cidadeRepository
            .findById(cidadeDto.getId())
            .map(existingCidade -> {
                cidadeMapper.partialUpdate(existingCidade, cidadeDto);

                return existingCidade;
            })
            .map(cidadeRepository::save)
            .map(cidadeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CidadeDto> findAll(Pageable pageable) {
        log.debug("Request to get all Cidades");
        return cidadeRepository.findAll(pageable).map(cidadeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CidadeDto> findOne(Long id) {
        log.debug("Request to get Cidade : {}", id);
        return cidadeRepository.findById(id).map(cidadeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cidade : {}", id);
        cidadeRepository.deleteById(id);
    }
}
