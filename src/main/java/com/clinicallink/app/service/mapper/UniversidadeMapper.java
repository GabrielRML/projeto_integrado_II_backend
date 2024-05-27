package com.clinicallink.app.service.mapper;

import com.clinicallink.app.domain.Universidade;
import com.clinicallink.app.service.dto.UniversidadeDto;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Universidade} and its DTO {@link UniversidadeDto}.
 */
@Mapper(componentModel = "spring")
public interface UniversidadeMapper extends EntityMapper<UniversidadeDto, Universidade> {}
