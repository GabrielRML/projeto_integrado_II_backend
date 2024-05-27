package com.clinicallink.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Avaliacao.
 */
@Entity
@Table(name = "avaliacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "assessment")
    private String assessment;

    @Column(name = "note")
    private Integer note;

    @JsonIgnoreProperties(value = { "avaliacao", "prestador", "cliente" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "avaliacao")
    private Consulta consulta;

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
    private Especialista avaliado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "internalUser", "avaliacaos", "consultas", "estado", "cidade" }, allowSetters = true)
    private Usuario avaliador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Avaliacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssessment() {
        return this.assessment;
    }

    public Avaliacao assessment(String assessment) {
        this.setAssessment(assessment);
        return this;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public Integer getNote() {
        return this.note;
    }

    public Avaliacao note(Integer note) {
        this.setNote(note);
        return this;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public Consulta getConsulta() {
        return this.consulta;
    }

    public void setConsulta(Consulta consulta) {
        if (this.consulta != null) {
            this.consulta.setAvaliacao(null);
        }
        if (consulta != null) {
            consulta.setAvaliacao(this);
        }
        this.consulta = consulta;
    }

    public Avaliacao consulta(Consulta consulta) {
        this.setConsulta(consulta);
        return this;
    }

    public Especialista getAvaliado() {
        return this.avaliado;
    }

    public void setAvaliado(Especialista especialista) {
        this.avaliado = especialista;
    }

    public Avaliacao avaliado(Especialista especialista) {
        this.setAvaliado(especialista);
        return this;
    }

    public Usuario getAvaliador() {
        return this.avaliador;
    }

    public void setAvaliador(Usuario usuario) {
        this.avaliador = usuario;
    }

    public Avaliacao avaliador(Usuario usuario) {
        this.setAvaliador(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Avaliacao)) {
            return false;
        }
        return getId() != null && getId().equals(((Avaliacao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Avaliacao{" +
            "id=" + getId() +
            ", assessment='" + getAssessment() + "'" +
            ", note=" + getNote() +
            "}";
    }
}
