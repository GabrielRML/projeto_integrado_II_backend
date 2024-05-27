package com.clinicallink.app.service.mapper;

import com.clinicallink.app.domain.Avaliacao;
import com.clinicallink.app.domain.Consulta;
import com.clinicallink.app.domain.Especialista;
import com.clinicallink.app.domain.Usuario;
import com.clinicallink.app.service.dto.AvaliacaoDto;
import com.clinicallink.app.service.dto.ConsultaDto;
import com.clinicallink.app.service.dto.EspecialistaDto;
import com.clinicallink.app.service.dto.UsuarioDto;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Consulta} and its DTO {@link ConsultaDto}.
 */
@Mapper(componentModel = "spring")
public interface ConsultaMapper extends EntityMapper<ConsultaDto, Consulta> {
    @Mapping(target = "avaliacao", source = "avaliacao", qualifiedByName = "avaliacaoId")
    @Mapping(target = "prestador", source = "prestador", qualifiedByName = "especialistaId")
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "usuarioId")
    ConsultaDto toDto(Consulta s);

    @Named("avaliacaoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AvaliacaoDto toDtoAvaliacaoId(Avaliacao avaliacao);

    @Named("especialistaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EspecialistaDto toDtoEspecialistaId(Especialista especialista);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDto toDtoUsuarioId(Usuario usuario);
}
