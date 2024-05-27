package com.clinicallink.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.clinicallink.app.domain.Avaliacao} entity. This class is used
 * in {@link com.clinicallink.app.web.rest.AvaliacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /avaliacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AvaliacaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter assessment;

    private IntegerFilter note;

    private LongFilter consultaId;

    private LongFilter avaliadoId;

    private LongFilter avaliadorId;

    private Boolean distinct;

    public AvaliacaoCriteria() {}

    public AvaliacaoCriteria(AvaliacaoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.assessment = other.assessment == null ? null : other.assessment.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.consultaId = other.consultaId == null ? null : other.consultaId.copy();
        this.avaliadoId = other.avaliadoId == null ? null : other.avaliadoId.copy();
        this.avaliadorId = other.avaliadorId == null ? null : other.avaliadorId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AvaliacaoCriteria copy() {
        return new AvaliacaoCriteria(this);
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

    public StringFilter getAssessment() {
        return assessment;
    }

    public StringFilter assessment() {
        if (assessment == null) {
            assessment = new StringFilter();
        }
        return assessment;
    }

    public void setAssessment(StringFilter assessment) {
        this.assessment = assessment;
    }

    public IntegerFilter getNote() {
        return note;
    }

    public IntegerFilter note() {
        if (note == null) {
            note = new IntegerFilter();
        }
        return note;
    }

    public void setNote(IntegerFilter note) {
        this.note = note;
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

    public LongFilter getAvaliadoId() {
        return avaliadoId;
    }

    public LongFilter avaliadoId() {
        if (avaliadoId == null) {
            avaliadoId = new LongFilter();
        }
        return avaliadoId;
    }

    public void setAvaliadoId(LongFilter avaliadoId) {
        this.avaliadoId = avaliadoId;
    }

    public LongFilter getAvaliadorId() {
        return avaliadorId;
    }

    public LongFilter avaliadorId() {
        if (avaliadorId == null) {
            avaliadorId = new LongFilter();
        }
        return avaliadorId;
    }

    public void setAvaliadorId(LongFilter avaliadorId) {
        this.avaliadorId = avaliadorId;
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
        final AvaliacaoCriteria that = (AvaliacaoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(assessment, that.assessment) &&
            Objects.equals(note, that.note) &&
            Objects.equals(consultaId, that.consultaId) &&
            Objects.equals(avaliadoId, that.avaliadoId) &&
            Objects.equals(avaliadorId, that.avaliadorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assessment, note, consultaId, avaliadoId, avaliadorId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvaliacaoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (assessment != null ? "assessment=" + assessment + ", " : "") +
            (note != null ? "note=" + note + ", " : "") +
            (consultaId != null ? "consultaId=" + consultaId + ", " : "") +
            (avaliadoId != null ? "avaliadoId=" + avaliadoId + ", " : "") +
            (avaliadorId != null ? "avaliadorId=" + avaliadorId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
