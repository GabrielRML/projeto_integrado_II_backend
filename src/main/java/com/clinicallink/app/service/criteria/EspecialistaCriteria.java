package com.clinicallink.app.service.criteria;

import com.clinicallink.app.domain.enumeration.SpecialistType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.clinicallink.app.domain.Especialista} entity. This class is used
 * in {@link com.clinicallink.app.web.rest.EspecialistaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /especialistas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EspecialistaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SpecialistType
     */
    public static class SpecialistTypeFilter extends Filter<SpecialistType> {

        public SpecialistTypeFilter() {}

        public SpecialistTypeFilter(SpecialistTypeFilter filter) {
            super(filter);
        }

        @Override
        public SpecialistTypeFilter copy() {
            return new SpecialistTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cpf;

    private StringFilter identification;

    private InstantFilter birthDate;

    private StringFilter description;

    private DoubleFilter price;

    private IntegerFilter timeSession;

    private SpecialistTypeFilter specialistType;

    private LongFilter internalUserId;

    private LongFilter especialidadeEspecialistaId;

    private LongFilter avaliacaoId;

    private LongFilter consultaId;

    private LongFilter especialistaId;

    private LongFilter estadoId;

    private LongFilter cidadeId;

    private LongFilter universidadeId;

    private LongFilter supervisorIdId;

    private Boolean distinct;

    public EspecialistaCriteria() {}

    public EspecialistaCriteria(EspecialistaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cpf = other.cpf == null ? null : other.cpf.copy();
        this.identification = other.identification == null ? null : other.identification.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.timeSession = other.timeSession == null ? null : other.timeSession.copy();
        this.specialistType = other.specialistType == null ? null : other.specialistType.copy();
        this.internalUserId = other.internalUserId == null ? null : other.internalUserId.copy();
        this.especialidadeEspecialistaId = other.especialidadeEspecialistaId == null ? null : other.especialidadeEspecialistaId.copy();
        this.avaliacaoId = other.avaliacaoId == null ? null : other.avaliacaoId.copy();
        this.consultaId = other.consultaId == null ? null : other.consultaId.copy();
        this.especialistaId = other.especialistaId == null ? null : other.especialistaId.copy();
        this.estadoId = other.estadoId == null ? null : other.estadoId.copy();
        this.cidadeId = other.cidadeId == null ? null : other.cidadeId.copy();
        this.universidadeId = other.universidadeId == null ? null : other.universidadeId.copy();
        this.supervisorIdId = other.supervisorIdId == null ? null : other.supervisorIdId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EspecialistaCriteria copy() {
        return new EspecialistaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCpf() {
        return cpf;
    }

    public StringFilter cpf() {
        if (cpf == null) {
            cpf = new StringFilter();
        }
        return cpf;
    }

    public void setCpf(StringFilter cpf) {
        this.cpf = cpf;
    }

    public StringFilter getIdentification() {
        return identification;
    }

    public StringFilter identification() {
        if (identification == null) {
            identification = new StringFilter();
        }
        return identification;
    }

    public void setIdentification(StringFilter identification) {
        this.identification = identification;
    }

    public InstantFilter getBirthDate() {
        return birthDate;
    }

    public InstantFilter birthDate() {
        if (birthDate == null) {
            birthDate = new InstantFilter();
        }
        return birthDate;
    }

    public void setBirthDate(InstantFilter birthDate) {
        this.birthDate = birthDate;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public DoubleFilter price() {
        if (price == null) {
            price = new DoubleFilter();
        }
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public IntegerFilter getTimeSession() {
        return timeSession;
    }

    public IntegerFilter timeSession() {
        if (timeSession == null) {
            timeSession = new IntegerFilter();
        }
        return timeSession;
    }

    public void setTimeSession(IntegerFilter timeSession) {
        this.timeSession = timeSession;
    }

    public SpecialistTypeFilter getSpecialistType() {
        return specialistType;
    }

    public SpecialistTypeFilter specialistType() {
        if (specialistType == null) {
            specialistType = new SpecialistTypeFilter();
        }
        return specialistType;
    }

    public void setSpecialistType(SpecialistTypeFilter specialistType) {
        this.specialistType = specialistType;
    }

    public LongFilter getInternalUserId() {
        return internalUserId;
    }

    public LongFilter internalUserId() {
        if (internalUserId == null) {
            internalUserId = new LongFilter();
        }
        return internalUserId;
    }

    public void setInternalUserId(LongFilter internalUserId) {
        this.internalUserId = internalUserId;
    }

    public LongFilter getEspecialidadeEspecialistaId() {
        return especialidadeEspecialistaId;
    }

    public LongFilter especialidadeEspecialistaId() {
        if (especialidadeEspecialistaId == null) {
            especialidadeEspecialistaId = new LongFilter();
        }
        return especialidadeEspecialistaId;
    }

    public void setEspecialidadeEspecialistaId(LongFilter especialidadeEspecialistaId) {
        this.especialidadeEspecialistaId = especialidadeEspecialistaId;
    }

    public LongFilter getAvaliacaoId() {
        return avaliacaoId;
    }

    public LongFilter avaliacaoId() {
        if (avaliacaoId == null) {
            avaliacaoId = new LongFilter();
        }
        return avaliacaoId;
    }

    public void setAvaliacaoId(LongFilter avaliacaoId) {
        this.avaliacaoId = avaliacaoId;
    }

    public LongFilter getConsultaId() {
        return consultaId;
    }

    public LongFilter consultaId() {
        if (consultaId == null) {
            consultaId = new LongFilter();
        }
        return consultaId;
    }

    public void setConsultaId(LongFilter consultaId) {
        this.consultaId = consultaId;
    }

    public LongFilter getEspecialistaId() {
        return especialistaId;
    }

    public LongFilter especialistaId() {
        if (especialistaId == null) {
            especialistaId = new LongFilter();
        }
        return especialistaId;
    }

    public void setEspecialistaId(LongFilter especialistaId) {
        this.especialistaId = especialistaId;
    }

    public LongFilter getEstadoId() {
        return estadoId;
    }

    public LongFilter estadoId() {
        if (estadoId == null) {
            estadoId = new LongFilter();
        }
        return estadoId;
    }

    public void setEstadoId(LongFilter estadoId) {
        this.estadoId = estadoId;
    }

    public LongFilter getCidadeId() {
        return cidadeId;
    }

    public LongFilter cidadeId() {
        if (cidadeId == null) {
            cidadeId = new LongFilter();
        }
        return cidadeId;
    }

    public void setCidadeId(LongFilter cidadeId) {
        this.cidadeId = cidadeId;
    }

    public LongFilter getUniversidadeId() {
        return universidadeId;
    }

    public LongFilter universidadeId() {
        if (universidadeId == null) {
            universidadeId = new LongFilter();
        }
        return universidadeId;
    }

    public void setUniversidadeId(LongFilter universidadeId) {
        this.universidadeId = universidadeId;
    }

    public LongFilter getSupervisorIdId() {
        return supervisorIdId;
    }

    public LongFilter supervisorIdId() {
        if (supervisorIdId == null) {
            supervisorIdId = new LongFilter();
        }
        return supervisorIdId;
    }

    public void setSupervisorIdId(LongFilter supervisorIdId) {
        this.supervisorIdId = supervisorIdId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EspecialistaCriteria that = (EspecialistaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(identification, that.identification) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(description, that.description) &&
            Objects.equals(price, that.price) &&
            Objects.equals(timeSession, that.timeSession) &&
            Objects.equals(specialistType, that.specialistType) &&
            Objects.equals(internalUserId, that.internalUserId) &&
            Objects.equals(especialidadeEspecialistaId, that.especialidadeEspecialistaId) &&
            Objects.equals(avaliacaoId, that.avaliacaoId) &&
            Objects.equals(consultaId, that.consultaId) &&
            Objects.equals(especialistaId, that.especialistaId) &&
            Objects.equals(estadoId, that.estadoId) &&
            Objects.equals(cidadeId, that.cidadeId) &&
            Objects.equals(universidadeId, that.universidadeId) &&
            Objects.equals(supervisorIdId, that.supervisorIdId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            cpf,
            identification,
            birthDate,
            description,
            price,
            timeSession,
            specialistType,
            internalUserId,
            especialidadeEspecialistaId,
            avaliacaoId,
            consultaId,
            especialistaId,
            estadoId,
            cidadeId,
            universidadeId,
            supervisorIdId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EspecialistaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cpf != null ? "cpf=" + cpf + ", " : "") +
            (identification != null ? "identification=" + identification + ", " : "") +
            (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (timeSession != null ? "timeSession=" + timeSession + ", " : "") +
            (specialistType != null ? "specialistType=" + specialistType + ", " : "") +
            (internalUserId != null ? "internalUserId=" + internalUserId + ", " : "") +
            (especialidadeEspecialistaId != null ? "especialidadeEspecialistaId=" + especialidadeEspecialistaId + ", " : "") +
            (avaliacaoId != null ? "avaliacaoId=" + avaliacaoId + ", " : "") +
            (consultaId != null ? "consultaId=" + consultaId + ", " : "") +
            (especialistaId != null ? "especialistaId=" + especialistaId + ", " : "") +
            (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
            (cidadeId != null ? "cidadeId=" + cidadeId + ", " : "") +
            (universidadeId != null ? "universidadeId=" + universidadeId + ", " : "") +
            (supervisorIdId != null ? "supervisorIdId=" + supervisorIdId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
