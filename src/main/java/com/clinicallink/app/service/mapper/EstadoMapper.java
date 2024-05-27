package com.clinicallink.app.service.mapper;

import com.clinicallink.app.domain.Estado;
import com.clinicallink.app.service.dto.EstadoDto;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Estado} and its DTO {@link EstadoDto}.
 */
@Mapper(componentModel = "spring")
public interface EstadoMapper extends EntityMapper<EstadoDto, Estado> {}
