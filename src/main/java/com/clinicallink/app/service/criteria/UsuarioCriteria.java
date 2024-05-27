package com.clinicallink.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.clinicallink.app.domain.Usuario} entity. This class is used
 * in {@link com.clinicallink.app.web.rest.UsuarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /usuarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cpf;

    private InstantFilter birthDate;

    private LongFilter internalUserId;

    private LongFilter avaliacaoId;

    private LongFilter consultaId;

    private LongFilter estadoId;

    private LongFilter cidadeId;

    private Boolean distinct;

    public UsuarioCriteria() {}

    public UsuarioCriteria(UsuarioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cpf = other.cpf == null ? null : other.cpf.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
        this.internalUserId = other.internalUserId == null ? null : other.internalUserId.copy();
        this.avaliacaoId = other.avaliacaoId == null ? null : other.avaliacaoId.copy();
        this.consultaId = other.consultaId == null ? null : other.consultaId.copy();
        this.estadoId = other.estadoId == null ? null : other.estadoId.copy();
        this.cidadeId = other.cidadeId == null ? null : other.cidadeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UsuarioCriteria copy() {
        return new UsuarioCriteria(this);
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
        final UsuarioCriteria that = (UsuarioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(internalUserId, that.internalUserId) &&
            Objects.equals(avaliacaoId, that.avaliacaoId) &&
            Objects.equals(consultaId, that.consultaId) &&
            Objects.equals(estadoId, that.estadoId) &&
            Objects.equals(cidadeId, that.cidadeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf, birthDate, internalUserId, avaliacaoId, consultaId, estadoId, cidadeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cpf != null ? "cpf=" + cpf + ", " : "") +
            (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
            (internalUserId != null ? "internalUserId=" + internalUserId + ", " : "") +
            (avaliacaoId != null ? "avaliacaoId=" + avaliacaoId + ", " : "") +
            (consultaId != null ? "consultaId=" + consultaId + ", " : "") +
            (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
            (cidadeId != null ? "cidadeId=" + cidadeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
