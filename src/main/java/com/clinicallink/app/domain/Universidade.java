package com.clinicallink.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Universidade.
 */
@Entity
@Table(name = "universidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Universidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "name")
    private String name;

    @Column(name = "cep")
    private String cep;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Especialista> especialistas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Universidade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public Universidade cnpj(String cnpj) {
        this.setCnpj(cnpj);
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return this.name;
    }

    public Universidade name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCep() {
        return this.cep;
    }

    public Universidade cep(String cep) {
        this.setCep(cep);
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Set<Especialista> getEspecialistas() {
        return this.especialistas;
    }

    public void setEspecialistas(Set<Especialista> especialistas) {
        if (this.especialistas != null) {
            this.especialistas.forEach(i -> i.setUniversidade(null));
        }
        if (especialistas != null) {
            especialistas.forEach(i -> i.setUniversidade(this));
        }
        this.especialistas = especialistas;
    }

    public Universidade especialistas(Set<Especialista> especialistas) {
        this.setEspecialistas(especialistas);
        return this;
    }

    public Universidade addEspecialista(Especialista especialista) {
        this.especialistas.add(especialista);
        especialista.setUniversidade(this);
        return this;
    }

    public Universidade removeEspecialista(Especialista especialista) {
        this.especialistas.remove(especialista);
        especialista.setUniversidade(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Universidade)) {
            return false;
        }
        return getId() != null && getId().equals(((Universidade) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Universidade{" +
            "id=" + getId() +
            ", cnpj='" + getCnpj() + "'" +
            ", name='" + getName() + "'" +
            ", cep='" + getCep() + "'" +
            "}";
    }
}
