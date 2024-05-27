package com.clinicallink.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Estado.
 */
@Entity
@Table(name = "estado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sigla")
    private String sigla;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usuarios", "especialistas", "estado" }, allowSetters = true)
    private Set<Cidade> cidades = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "internalUser", "avaliacaos", "consultas", "estado", "cidade" }, allowSetters = true)
    private Set<Usuario> usuarios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estado")
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

    public Estado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Estado nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return this.sigla;
    }

    public Estado sigla(String sigla) {
        this.setSigla(sigla);
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Set<Cidade> getCidades() {
        return this.cidades;
    }

    public void setCidades(Set<Cidade> cidades) {
        if (this.cidades != null) {
            this.cidades.forEach(i -> i.setEstado(null));
        }
        if (cidades != null) {
            cidades.forEach(i -> i.setEstado(this));
        }
        this.cidades = cidades;
    }

    public Estado cidades(Set<Cidade> cidades) {
        this.setCidades(cidades);
        return this;
    }

    public Estado addCidade(Cidade cidade) {
        this.cidades.add(cidade);
        cidade.setEstado(this);
        return this;
    }

    public Estado removeCidade(Cidade cidade) {
        this.cidades.remove(cidade);
        cidade.setEstado(null);
        return this;
    }

    public Set<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        if (this.usuarios != null) {
            this.usuarios.forEach(i -> i.setEstado(null));
        }
        if (usuarios != null) {
            usuarios.forEach(i -> i.setEstado(this));
        }
        this.usuarios = usuarios;
    }

    public Estado usuarios(Set<Usuario> usuarios) {
        this.setUsuarios(usuarios);
        return this;
    }

    public Estado addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.setEstado(this);
        return this;
    }

    public Estado removeUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
        usuario.setEstado(null);
        return this;
    }

    public Set<Especialista> getEspecialistas() {
        return this.especialistas;
    }

    public void setEspecialistas(Set<Especialista> especialistas) {
        if (this.especialistas != null) {
            this.especialistas.forEach(i -> i.setEstado(null));
        }
        if (especialistas != null) {
            especialistas.forEach(i -> i.setEstado(this));
        }
        this.especialistas = especialistas;
    }

    public Estado especialistas(Set<Especialista> especialistas) {
        this.setEspecialistas(especialistas);
        return this;
    }

    public Estado addEspecialista(Especialista especialista) {
        this.especialistas.add(especialista);
        especialista.setEstado(this);
        return this;
    }

    public Estado removeEspecialista(Especialista especialista) {
        this.especialistas.remove(especialista);
        especialista.setEstado(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estado)) {
            return false;
        }
        return getId() != null && getId().equals(((Estado) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Estado{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            "}";
    }
}
