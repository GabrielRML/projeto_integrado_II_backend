package com.clinicallink.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.clinicallink.app.domain.Avaliacao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AvaliacaoDto implements Serializable {

    private Long id;

    private String assessment;

    private Integer note;

    private EspecialistaDto avaliado;

    private UsuarioDto avaliador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public EspecialistaDto getAvaliado() {
        return avaliado;
    }

    public void setAvaliado(EspecialistaDto avaliado) {
        this.avaliado = avaliado;
    }

    public UsuarioDto getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(UsuarioDto avaliador) {
        this.avaliador = avaliador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvaliacaoDto)) {
            return false;
        }

        AvaliacaoDto avaliacaoDto = (AvaliacaoDto) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, avaliacaoDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvaliacaoDto{" +
            "id=" + getId() +
            ", assessment='" + getAssessment() + "'" +
            ", note=" + getNote() +
            ", avaliado=" + getAvaliado() +
            ", avaliador=" + getAvaliador() +
            "}";
    }
}
