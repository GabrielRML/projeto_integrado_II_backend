package com.clinicallink.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.clinicallink.app.domain.Usuario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuarioDto implements Serializable {

    private Long id;

    private String cpf;

    private Instant birthDate;

    private UserDto internalUser;

    private EstadoDto estado;

    private CidadeDto cidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public UserDto getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(UserDto internalUser) {
        this.internalUser = internalUser;
    }

    public EstadoDto getEstado() {
        return estado;
    }

    public void setEstado(EstadoDto estado) {
        this.estado = estado;
    }

    public CidadeDto getCidade() {
        return cidade;
    }

    public void setCidade(CidadeDto cidade) {
        this.cidade = cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioDto)) {
            return false;
        }

        UsuarioDto usuarioDto = (UsuarioDto) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, usuarioDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioDto{" +
            "id=" + getId() +
            ", cpf='" + getCpf() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", internalUser=" + getInternalUser() +
            ", estado=" + getEstado() +
            ", cidade=" + getCidade() +
            "}";
    }
}
