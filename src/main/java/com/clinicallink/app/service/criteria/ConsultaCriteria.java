package com.clinicallink.app.service.criteria;

import com.clinicallink.app.domain.enumeration.StatusConsulta;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.clinicallink.app.domain.Consulta} entity. This class is used
 * in {@link com.clinicallink.app.web.rest.ConsultaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /consultas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConsultaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering StatusConsulta
     */
    public static class StatusConsultaFilter extends Filter<StatusConsulta> {

        public StatusConsultaFilter() {}

        public StatusConsultaFilter(StatusConsultaFilter filter) {
            super(filter);
        }

        @Override
        public StatusConsultaFilter copy() {
            return new StatusConsultaFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter date;

    private StringFilter reason;

    private StringFilter link;

    private StatusConsultaFilter status;

    private LongFilter avaliacaoId;

    private LongFilter prestadorId;

    private LongFilter clienteId;

    private Boolean distinct;

    public ConsultaCriteria() {}

    public ConsultaCriteria(ConsultaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.link = other.link == null ? null : other.link.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.avaliacaoId = other.avaliacaoId == null ? null : other.avaliacaoId.copy();
        this.prestadorId = other.prestadorId == null ? null : other.prestadorId.copy();
        this.clienteId = other.clienteId == null ? null : other.clienteId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ConsultaCriteria copy() {
        return new ConsultaCriteria(this);
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

    public InstantFilter getDate() {
        return date;
    }

    public InstantFilter date() {
        if (date == null) {
            date = new InstantFilter();
        }
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public StringFilter getReason() {
        return reason;
    }

    public StringFilter reason() {
        if (reason == null) {
            reason = new StringFilter();
        }
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
    }

    public StringFilter getLink() {
        return link;
    }

    public StringFilter link() {
        if (link == null) {
            link = new StringFilter();
        }
        return link;
    }

    public void setLink(StringFilter link) {
        this.link = link;
    }

    public StatusConsultaFilter getStatus() {
        return status;
    }

    public StatusConsultaFilter status() {
        if (status == null) {
            status = new StatusConsultaFilter();
        }
        return status;
    }

    public void setStatus(StatusConsultaFilter status) {
        this.status = status;
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

    public LongFilter getPrestadorId() {
        return prestadorId;
    }

    public LongFilter prestadorId() {
        if (prestadorId == null) {
            prestadorId = new LongFilter();
        }
        return prestadorId;
    }

    public void setPrestadorId(LongFilter prestadorId) {
        this.prestadorId = prestadorId;
    }

    public LongFilter getClienteId() {
        return clienteId;
    }

    public LongFilter clienteId() {
        if (clienteId == null) {
            clienteId = new LongFilter();
        }
        return clienteId;
    }

    public void setClienteId(LongFilter clienteId) {
        this.clienteId = clienteId;
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
        final ConsultaCriteria that = (ConsultaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(link, that.link) &&
            Objects.equals(status, that.status) &&
            Objects.equals(avaliacaoId, that.avaliacaoId) &&
            Objects.equals(prestadorId, that.prestadorId) &&
            Objects.equals(clienteId, that.clienteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, reason, link, status, avaliacaoId, prestadorId, clienteId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsultaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (reason != null ? "reason=" + reason + ", " : "") +
            (link != null ? "link=" + link + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (avaliacaoId != null ? "avaliacaoId=" + avaliacaoId + ", " : "") +
            (prestadorId != null ? "prestadorId=" + prestadorId + ", " : "") +
            (clienteId != null ? "clienteId=" + clienteId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
