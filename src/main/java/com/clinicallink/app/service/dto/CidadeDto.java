package com.clinicallink.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.clinicallink.app.domain.Cidade} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CidadeDto implements Serializable {

    private Long id;

    private String nome;

    private String sigla;

    private EstadoDto estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public EstadoDto getEstado() {
        return estado;
    }

    public void setEstado(EstadoDto estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CidadeDto)) {
            return false;
        }

        CidadeDto cidadeDto = (CidadeDto) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cidadeDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CidadeDto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", estado=" + getEstado() +
            "}";
    }
}
