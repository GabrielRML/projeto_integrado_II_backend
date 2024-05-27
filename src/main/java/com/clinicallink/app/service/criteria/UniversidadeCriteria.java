package com.clinicallink.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.clinicallink.app.domain.Universidade} entity. This class is used
 * in {@link com.clinicallink.app.web.rest.UniversidadeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /universidades?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UniversidadeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cnpj;

    private StringFilter name;

    private StringFilter cep;

    private LongFilter especialistaId;

    private Boolean distinct;

    public UniversidadeCriteria() {}

    public UniversidadeCriteria(UniversidadeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cnpj = other.cnpj == null ? null : other.cnpj.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.cep = other.cep == null ? null : other.cep.copy();
        this.especialistaId = other.especialistaId == null ? null : other.especialistaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UniversidadeCriteria copy() {
        return new UniversidadeCriteria(this);
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

    public StringFilter getCnpj() {
        return cnpj;
    }

    public StringFilter cnpj() {
        if (cnpj == null) {
            cnpj = new StringFilter();
        }
        return cnpj;
    }

    public void setCnpj(StringFilter cnpj) {
        this.cnpj = cnpj;
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

    public StringFilter getCep() {
        return cep;
    }

    public StringFilter cep() {
        if (cep == null) {
            cep = new StringFilter();
        }
        return cep;
    }

    public void setCep(StringFilter cep) {
        this.cep = cep;
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
        final UniversidadeCriteria that = (UniversidadeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cnpj, that.cnpj) &&
            Objects.equals(name, that.name) &&
            Objects.equals(cep, that.cep) &&
            Objects.equals(especialistaId, that.especialistaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cnpj, name, cep, especialistaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UniversidadeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cnpj != null ? "cnpj=" + cnpj + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (cep != null ? "cep=" + cep + ", " : "") +
            (especialistaId != null ? "especialistaId=" + especialistaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
