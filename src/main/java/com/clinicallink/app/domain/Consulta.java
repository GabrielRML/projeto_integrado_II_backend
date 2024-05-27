package com.clinicallink.app.domain;

import com.clinicallink.app.domain.enumeration.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Consulta.
 */
@Entity
@Table(name = "consulta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Consulta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "reason")
    private String reason;

    @Column(name = "link")
    private String link;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusConsulta status;

    @JsonIgnoreProperties(value = { "consulta", "avaliado", "avaliador" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Avaliacao avaliacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "internalUser",
            "especialidadeEspecialistas",
            "avaliacaos",
            "consultas",
            "especialistas",
            "estado",
            "cidade",
            "universidade",
            "supervisorId",
        },
        allowSetters = true
    )
    private Especialista prestador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "internalUser", "avaliacaos", "consultas", "estado", "cidade" }, allowSetters = true)
    private Usuario cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Consulta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Consulta date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getReason() {
        return this.reason;
    }

    public Consulta reason(String reason) {
        this.setReason(reason);
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLink() {
        return this.link;
    }

    public Consulta link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public StatusConsulta getStatus() {
        return this.status;
    }

    public Consulta status(StatusConsulta status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }

    public Avaliacao getAvaliacao() {
        return this.avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Consulta avaliacao(Avaliacao avaliacao) {
        this.setAvaliacao(avaliacao);
        return this;
    }

    public Especialista getPrestador() {
        return this.prestador;
    }

    public void setPrestador(Especialista especialista) {
        this.prestador = especialista;
    }

    public Consulta prestador(Especialista especialista) {
        this.setPrestador(especialista);
        return this;
    }

    public Usuario getCliente() {
        return this.cliente;
    }

    public void setCliente(Usuario usuario) {
        this.cliente = usuario;
    }

    public Consulta cliente(Usuario usuario) {
        this.setCliente(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consulta)) {
            return false;
        }
        return getId() != null && getId().equals(((Consulta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Consulta{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", reason='" + getReason() + "'" +
            ", link='" + getLink() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
