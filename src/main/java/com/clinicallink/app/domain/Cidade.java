package com.clinicallink.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cidade.
 */
@Entity
@Table(name = "cidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sigla")
    private String sigla;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "internalUser", "avaliacaos", "consultas", "estado", "cidade" }, allowSetters = true)
    private Set<Usuario> usuarios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cidades", "usuarios", "especialistas" }, allowSetters = true)
    private Estado estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cidade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Cidade nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return this.sigla;
    }

    public Cidade sigla(String sigla) {
        this.setSigla(sigla);
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Set<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        if (this.usuarios != null) {
            this.usuarios.forEach(i -> i.setCidade(null));
        }
        if (usuarios != null) {
            usuarios.forEach(i -> i.setCidade(this));
        }
        this.usuarios = usuarios;
    }

    public Cidade usuarios(Set<Usuario> usuarios) {
        this.setUsuarios(usuarios);
        return this;
    }

    public Cidade addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.setCidade(this);
        return this;
    }

    public Cidade removeUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
        usuario.setCidade(null);
        return this;
    }

    public Set<Especialista> getEspecialistas() {
        return this.especialistas;
    }

    public void setEspecialistas(Set<Especialista> especialistas) {
        if (this.especialistas != null) {
            this.especialistas.forEach(i -> i.setCidade(null));
        }
        if (especialistas != null) {
            especialistas.forEach(i -> i.setCidade(this));
        }
        this.especialistas = especialistas;
    }

    public Cidade especialistas(Set<Especialista> especialistas) {
        this.setEspecialistas(especialistas);
        return this;
    }

    public Cidade addEspecialista(Especialista especialista) {
        this.especialistas.add(especialista);
        especialista.setCidade(this);
        return this;
    }

    public Cidade removeEspecialista(Especialista especialista) {
        this.especialistas.remove(especialista);
        especialista.setCidade(null);
        return this;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cidade)) {
            return false;
        }
        return getId() != null && getId().equals(((Cidade) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cidade{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            "}";
    }
}
