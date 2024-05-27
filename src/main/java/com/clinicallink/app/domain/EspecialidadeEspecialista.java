package com.clinicallink.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EspecialidadeEspecialista.
 */
@Entity
@Table(name = "especialidade_especialista")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EspecialidadeEspecialista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "especialidadeEspecialistas" }, allowSetters = true)
    private Especialidade especialidade;

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
    private Especialista especialista;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EspecialidadeEspecialista id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Especialidade getEspecialidade() {
        return this.especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public EspecialidadeEspecialista especialidade(Especialidade especialidade) {
        this.setEspecialidade(especialidade);
        return this;
    }

    public Especialista getEspecialista() {
        return this.especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public EspecialidadeEspecialista especialista(Especialista especialista) {
        this.setEspecialista(especialista);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EspecialidadeEspecialista)) {
            return false;
        }
        return getId() != null && getId().equals(((EspecialidadeEspecialista) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EspecialidadeEspecialista{" +
            "id=" + getId() +
            "}";
    }
}
