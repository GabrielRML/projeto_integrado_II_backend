package com.clinicallink.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.clinicallink.app.domain.Universidade} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UniversidadeDto implements Serializable {

    private Long id;

    private String cnpj;

    private String name;

    private String cep;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UniversidadeDto)) {
            return false;
        }

        UniversidadeDto universidadeDto = (UniversidadeDto) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, universidadeDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UniversidadeDto{" +
            "id=" + getId() +
            ", cnpj='" + getCnpj() + "'" +
            ", name='" + getName() + "'" +
            ", cep='" + getCep() + "'" +
            "}";
    }
}
