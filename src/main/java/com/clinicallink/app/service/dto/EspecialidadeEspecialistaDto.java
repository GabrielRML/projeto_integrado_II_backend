package com.clinicallink.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.clinicallink.app.domain.EspecialidadeEspecialista} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EspecialidadeEspecialistaDto implements Serializable {

    private Long id;

    private EspecialidadeDto especialidade;

    private EspecialistaDto especialista;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EspecialidadeDto getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(EspecialidadeDto especialidade) {
        this.especialidade = especialidade;
    }

    public EspecialistaDto getEspecialista() {
        return especialista;
    }

    public void setEspecialista(EspecialistaDto especialista) {
        this.especialista = especialista;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EspecialidadeEspecialistaDto)) {
            return false;
        }

        EspecialidadeEspecialistaDto especialidadeEspecialistaDto = (EspecialidadeEspecialistaDto) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, especialidadeEspecialistaDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EspecialidadeEspecialistaDto{" +
            "id=" + getId() +
            ", especialidade=" + getEspecialidade() +
            ", especialista=" + getEspecialista() +
            "}";
    }
}
