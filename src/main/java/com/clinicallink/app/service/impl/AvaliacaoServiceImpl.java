package com.clinicallink.app.service.impl;

import com.clinicallink.app.domain.Avaliacao;
import com.clinicallink.app.repository.AvaliacaoRepository;
import com.clinicallink.app.service.AvaliacaoService;
import com.clinicallink.app.service.dto.AvaliacaoDto;
import com.clinicallink.app.service.mapper.AvaliacaoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.clinicallink.app.domain.Avaliacao}.
 */
@Service
@Transactional
public class AvaliacaoServiceImpl implements AvaliacaoService {

    private final Logger log = LoggerFactory.getLogger(AvaliacaoServiceImpl.class);

    private final AvaliacaoRepository avaliacaoRepository;

    private final AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoServiceImpl(AvaliacaoRepository avaliacaoRepository, AvaliacaoMapper avaliacaoMapper) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    @Override
    public AvaliacaoDto save(AvaliacaoDto avaliacaoDto) {
        log.debug("Request to save Avaliacao : {}", avaliacaoDto);
        Avaliacao avaliacao = avaliacaoMapper.toEntity(avaliacaoDto);
        avaliacao = avaliacaoRepository.save(avaliacao);
        return avaliacaoMapper.toDto(avaliacao);
    }

    @Override
    public AvaliacaoDto update(AvaliacaoDto avaliacaoDto) {
        log.debug("Request to update Avaliacao : {}", avaliacaoDto);
        Avaliacao avaliacao = avaliacaoMapper.toEntity(avaliacaoDto);
        avaliacao = avaliacaoRepository.save(avaliacao);
        return avaliacaoMapper.toDto(avaliacao);
    }

    @Override
    public Optional<AvaliacaoDto> partialUpdate(AvaliacaoDto avaliacaoDto) {
        log.debug("Request to partially update Avaliacao : {}", avaliacaoDto);

        return avaliacaoRepository
            .findById(avaliacaoDto.getId())
            .map(existingAvaliacao -> {
                avaliacaoMapper.partialUpdate(existingAvaliacao, avaliacaoDto);

                return existingAvaliacao;
            })
            .map(avaliacaoRepository::save)
            .map(avaliacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AvaliacaoDto> findAll(Pageable pageable) {
        log.debug("Request to get all Avaliacaos");
        return avaliacaoRepository.findAll(pageable).map(avaliacaoMapper::toDto);
    }

    /**
     *  Get all the avaliacaos where Consulta is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AvaliacaoDto> findAllWhereConsultaIsNull() {
        log.debug("Request to get all avaliacaos where Consulta is null");
        return StreamSupport
            .stream(avaliacaoRepository.findAll().spliterator(), false)
            .filter(avaliacao -> avaliacao.getConsulta() == null)
            .map(avaliacaoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AvaliacaoDto> findOne(Long id) {
        log.debug("Request to get Avaliacao : {}", id);
        return avaliacaoRepository.findById(id).map(avaliacaoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Avaliacao : {}", id);
        avaliacaoRepository.deleteById(id);
    }
}
