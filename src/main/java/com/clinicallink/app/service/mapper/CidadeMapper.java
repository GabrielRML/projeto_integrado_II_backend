package com.clinicallink.app.service.mapper;

import com.clinicallink.app.domain.Cidade;
import com.clinicallink.app.domain.Estado;
import com.clinicallink.app.service.dto.CidadeDto;
import com.clinicallink.app.service.dto.EstadoDto;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cidade} and its DTO {@link CidadeDto}.
 */
@Mapper(componentModel = "spring")
public interface CidadeMapper extends EntityMapper<CidadeDto, Cidade> {
    @Mapping(target = "estado", source = "estado", qualifiedByName = "estadoId")
    CidadeDto toDto(Cidade s);

    @Named("estadoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EstadoDto toDtoEstadoId(Estado estado);
}
