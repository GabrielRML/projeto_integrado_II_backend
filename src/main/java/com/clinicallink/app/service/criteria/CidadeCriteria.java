package com.clinicallink.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.clinicallink.app.domain.Cidade} entity. This class is used
 * in {@link com.clinicallink.app.web.rest.CidadeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cidades?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CidadeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter sigla;

    private LongFilter usuarioId;

    private LongFilter especialistaId;

    private LongFilter estadoId;

    private Boolean distinct;

    public CidadeCriteria() {}

    public CidadeCriteria(CidadeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.sigla = other.sigla == null ? null : other.sigla.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.especialistaId = other.especialistaId == null ? null : other.especialistaId.copy();
        this.estadoId = other.estadoId == null ? null : other.estadoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CidadeCriteria copy() {
        return new CidadeCriteria(this);
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

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getSigla() {
        return sigla;
    }

    public StringFilter sigla() {
        if (sigla == null) {
            sigla = new StringFilter();
        }
        return sigla;
    }

    public void setSigla(StringFilter sigla) {
        this.sigla = sigla;
    }

    public LongFilter getUsuarioId() {
        return usuarioId;
    }

    public LongFilter usuarioId() {
        if (usuarioId == null) {
            usuarioId = new LongFilter();
        }
        return usuarioId;
    }

    public void setUsuarioId(LongFilter usuarioId) {
        this.usuarioId = usuarioId;
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
        final CidadeCriteria that = (CidadeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(sigla, that.sigla) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(especialistaId, that.especialistaId) &&
            Objects.equals(estadoId, that.estadoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sigla, usuarioId, especialistaId, estadoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CidadeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (sigla != null ? "sigla=" + sigla + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (especialistaId != null ? "especialistaId=" + especialistaId + ", " : "") +
            (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
