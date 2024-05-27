package com.clinicallink.app.service.mapper;

import com.clinicallink.app.domain.Avaliacao;
import com.clinicallink.app.domain.Especialista;
import com.clinicallink.app.domain.Usuario;
import com.clinicallink.app.service.dto.AvaliacaoDto;
import com.clinicallink.app.service.dto.EspecialistaDto;
import com.clinicallink.app.service.dto.UsuarioDto;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Avaliacao} and its DTO {@link AvaliacaoDto}.
 */
@Mapper(componentModel = "spring")
public interface AvaliacaoMapper extends EntityMapper<AvaliacaoDto, Avaliacao> {
    @Mapping(target = "avaliado", source = "avaliado", qualifiedByName = "especialistaId")
    @Mapping(target = "avaliador", source = "avaliador", qualifiedByName = "usuarioId")
    AvaliacaoDto toDto(Avaliacao s);

    @Named("especialistaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EspecialistaDto toDtoEspecialistaId(Especialista especialista);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDto toDtoUsuarioId(Usuario usuario);
}
