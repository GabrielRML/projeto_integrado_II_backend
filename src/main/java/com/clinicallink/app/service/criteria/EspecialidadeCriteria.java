package com.clinicallink.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.clinicallink.app.domain.Especialidade} entity. This class is used
 * in {@link com.clinicallink.app.web.rest.EspecialidadeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /especialidades?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EspecialidadeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter especialidadeEspecialistaId;

    private Boolean distinct;

    public EspecialidadeCriteria() {}

    public EspecialidadeCriteria(EspecialidadeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.especialidadeEspecialistaId = other.especialidadeEspecialistaId == null ? null : other.especialidadeEspecialistaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EspecialidadeCriteria copy() {
        return new EspecialidadeCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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
        final EspecialidadeCriteria that = (EspecialidadeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(especialidadeEspecialistaId, that.especialidadeEspecialistaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, especialidadeEspecialistaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EspecialidadeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (especialidadeEspecialistaId != null ? "especialidadeEspecialistaId=" + especialidadeEspecialistaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
