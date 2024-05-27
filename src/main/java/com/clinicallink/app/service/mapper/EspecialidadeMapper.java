package com.clinicallink.app.service.mapper;

import com.clinicallink.app.domain.Especialidade;
import com.clinicallink.app.service.dto.EspecialidadeDto;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Especialidade} and its DTO {@link EspecialidadeDto}.
 */
@Mapper(componentModel = "spring")
public interface EspecialidadeMapper extends EntityMapper<EspecialidadeDto, Especialidade> {}
