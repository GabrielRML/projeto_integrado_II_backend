package com.clinicallink.app.service.mapper;

import com.clinicallink.app.domain.Cidade;
import com.clinicallink.app.domain.Especialista;
import com.clinicallink.app.domain.Estado;
import com.clinicallink.app.domain.Universidade;
import com.clinicallink.app.domain.User;
import com.clinicallink.app.service.dto.CidadeDto;
import com.clinicallink.app.service.dto.EspecialistaDto;
import com.clinicallink.app.service.dto.EstadoDto;
import com.clinicallink.app.service.dto.UniversidadeDto;
import com.clinicallink.app.service.dto.UserDto;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Especialista} and its DTO {@link EspecialistaDto}.
 */
@Mapper(componentModel = "spring")
public interface EspecialistaMapper extends EntityMapper<EspecialistaDto, Especialista> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userId")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "estadoId")
    @Mapping(target = "cidade", source = "cidade", qualifiedByName = "cidadeId")
    @Mapping(target = "universidade", source = "universidade", qualifiedByName = "universidadeId")
    @Mapping(target = "supervisorId", source = "supervisorId", qualifiedByName = "especialistaId")
    EspecialistaDto toDto(Especialista s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDto toDtoUserId(User user);

    @Named("especialistaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EspecialistaDto toDtoEspecialistaId(Especialista especialista);

    @Named("estadoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EstadoDto toDtoEstadoId(Estado estado);

    @Named("cidadeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CidadeDto toDtoCidadeId(Cidade cidade);

    @Named("universidadeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UniversidadeDto toDtoUniversidadeId(Universidade universidade);
}
