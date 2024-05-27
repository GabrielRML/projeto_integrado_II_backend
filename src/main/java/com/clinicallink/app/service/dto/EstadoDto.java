package com.clinicallink.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.clinicallink.app.domain.Estado} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadoDto implements Serializable {

    private Long id;

    private String nome;

    private String sigla;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoDto)) {
            return false;
        }

        EstadoDto estadoDto = (EstadoDto) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, estadoDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoDto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            "}";
    }
}
