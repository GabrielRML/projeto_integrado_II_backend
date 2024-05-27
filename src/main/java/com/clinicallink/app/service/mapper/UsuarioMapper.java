package com.clinicallink.app.service.mapper;

import com.clinicallink.app.domain.Cidade;
import com.clinicallink.app.domain.Estado;
import com.clinicallink.app.domain.User;
import com.clinicallink.app.domain.Usuario;
import com.clinicallink.app.service.dto.CidadeDto;
import com.clinicallink.app.service.dto.EstadoDto;
import com.clinicallink.app.service.dto.UserDto;
import com.clinicallink.app.service.dto.UsuarioDto;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Usuario} and its DTO {@link UsuarioDto}.
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper extends EntityMapper<UsuarioDto, Usuario> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userId")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "estadoId")
    @Mapping(target = "cidade", source = "cidade", qualifiedByName = "cidadeId")
    UsuarioDto toDto(Usuario s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDto toDtoUserId(User user);

    @Named("estadoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EstadoDto toDtoEstadoId(Estado estado);

    @Named("cidadeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CidadeDto toDtoCidadeId(Cidade cidade);
}
