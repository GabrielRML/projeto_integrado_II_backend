package com.clinicallink.app.service.mapper;

import com.clinicallink.app.domain.Especialidade;
import com.clinicallink.app.domain.EspecialidadeEspecialista;
import com.clinicallink.app.domain.Especialista;
import com.clinicallink.app.service.dto.EspecialidadeDto;
import com.clinicallink.app.service.dto.EspecialidadeEspecialistaDto;
import com.clinicallink.app.service.dto.EspecialistaDto;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EspecialidadeEspecialista} and its DTO {@link EspecialidadeEspecialistaDto}.
 */
@Mapper(componentModel = "spring")
public interface EspecialidadeEspecialistaMapper extends EntityMapper<EspecialidadeEspecialistaDto, EspecialidadeEspecialista> {
    @Mapping(target = "especialidade", source = "especialidade", qualifiedByName = "especialidadeId")
    @Mapping(target = "especialista", source = "especialista", qualifiedByName = "especialistaId")
    EspecialidadeEspecialistaDto toDto(EspecialidadeEspecialista s);

    @Named("especialidadeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EspecialidadeDto toDtoEspecialidadeId(Especialidade especialidade);

    @Named("especialistaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EspecialistaDto toDtoEspecialistaId(Especialista especialista);
}
