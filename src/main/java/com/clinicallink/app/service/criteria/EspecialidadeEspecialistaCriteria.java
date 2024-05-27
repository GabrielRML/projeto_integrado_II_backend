package com.clinicallink.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.clinicallink.app.domain.EspecialidadeEspecialista} entity. This class is used
 * in {@link com.clinicallink.app.web.rest.EspecialidadeEspecialistaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /especialidade-especialistas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EspecialidadeEspecialistaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter especialidadeId;

    private LongFilter especialistaId;

    private Boolean distinct;

    public EspecialidadeEspecialistaCriteria() {}

    public EspecialidadeEspecialistaCriteria(EspecialidadeEspecialistaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.especialidadeId = other.especialidadeId == null ? null : other.especialidadeId.copy();
        this.especialistaId = other.especialistaId == null ? null : other.especialistaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EspecialidadeEspecialistaCriteria copy() {
        return new EspecialidadeEspecialistaCriteria(this);
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

    public LongFilter getEspecialidadeId() {
        return especialidadeId;
    }

    public LongFilter especialidadeId() {
        if (especialidadeId == null) {
            especialidadeId = new LongFilter();
        }
        return especialidadeId;
    }

    public void setEspecialidadeId(LongFilter especialidadeId) {
        this.especialidadeId = especialidadeId;
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
        final EspecialidadeEspecialistaCriteria that = (EspecialidadeEspecialistaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(especialidadeId, that.especialidadeId) &&
            Objects.equals(especialistaId, that.especialistaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, especialidadeId, especialistaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EspecialidadeEspecialistaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (especialidadeId != null ? "especialidadeId=" + especialidadeId + ", " : "") +
            (especialistaId != null ? "especialistaId=" + especialistaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
