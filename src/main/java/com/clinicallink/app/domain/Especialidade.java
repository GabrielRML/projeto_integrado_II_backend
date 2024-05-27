package com.clinicallink.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Especialidade.
 */
@Entity
@Table(name = "especialidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Especialidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "especialidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "especialidade", "especialista" }, allowSetters = true)
    private Set<EspecialidadeEspecialista> especialidadeEspecialistas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Especialidade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Especialidade name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EspecialidadeEspecialista> getEspecialidadeEspecialistas() {
        return this.especialidadeEspecialistas;
    }

    public void setEspecialidadeEspecialistas(Set<EspecialidadeEspecialista> especialidadeEspecialistas) {
        if (this.especialidadeEspecialistas != null) {
            this.especialidadeEspecialistas.forEach(i -> i.setEspecialidade(null));
        }
        if (especialidadeEspecialistas != null) {
            especialidadeEspecialistas.forEach(i -> i.setEspecialidade(this));
        }
        this.especialidadeEspecialistas = especialidadeEspecialistas;
    }

    public Especialidade especialidadeEspecialistas(Set<EspecialidadeEspecialista> especialidadeEspecialistas) {
        this.setEspecialidadeEspecialistas(especialidadeEspecialistas);
        return this;
    }

    public Especialidade addEspecialidadeEspecialista(EspecialidadeEspecialista especialidadeEspecialista) {
        this.especialidadeEspecialistas.add(especialidadeEspecialista);
        especialidadeEspecialista.setEspecialidade(this);
        return this;
    }

    public Especialidade removeEspecialidadeEspecialista(EspecialidadeEspecialista especialidadeEspecialista) {
        this.especialidadeEspecialistas.remove(especialidadeEspecialista);
        especialidadeEspecialista.setEspecialidade(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Especialidade)) {
            return false;
        }
        return getId() != null && getId().equals(((Especialidade) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Especialidade{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
