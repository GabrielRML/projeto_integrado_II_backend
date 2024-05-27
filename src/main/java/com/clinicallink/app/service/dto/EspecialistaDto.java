package com.clinicallink.app.service.dto;

import com.clinicallink.app.domain.enumeration.SpecialistType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.clinicallink.app.domain.Especialista} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EspecialistaDto implements Serializable {

    private Long id;

    private String cpf;

    private String identification;

    private Instant birthDate;

    private String description;

    private Double price;

    private Integer timeSession;

    private SpecialistType specialistType;

    private UserDto internalUser;

    private EstadoDto estado;

    private CidadeDto cidade;

    private UniversidadeDto universidade;

    private EspecialistaDto supervisorId;

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

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTimeSession() {
        return timeSession;
    }

    public void setTimeSession(Integer timeSession) {
        this.timeSession = timeSession;
    }

    public SpecialistType getSpecialistType() {
        return specialistType;
    }

    public void setSpecialistType(SpecialistType specialistType) {
        this.specialistType = specialistType;
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

    public UniversidadeDto getUniversidade() {
        return universidade;
    }

    public void setUniversidade(UniversidadeDto universidade) {
        this.universidade = universidade;
    }

    public EspecialistaDto getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(EspecialistaDto supervisorId) {
        this.supervisorId = supervisorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EspecialistaDto)) {
            return false;
        }

        EspecialistaDto especialistaDto = (EspecialistaDto) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, especialistaDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EspecialistaDto{" +
            "id=" + getId() +
            ", cpf='" + getCpf() + "'" +
            ", identification='" + getIdentification() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", timeSession=" + getTimeSession() +
            ", specialistType='" + getSpecialistType() + "'" +
            ", internalUser=" + getInternalUser() +
            ", estado=" + getEstado() +
            ", cidade=" + getCidade() +
            ", universidade=" + getUniversidade() +
            ", supervisorId=" + getSupervisorId() +
            "}";
    }
}
