package com.clinicallink.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "birth_date")
    private Instant birthDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User internalUser;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "avaliador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "consulta", "avaliado", "avaliador" }, allowSetters = true)
    private Set<Avaliacao> avaliacaos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "avaliacao", "prestador", "cliente" }, allowSetters = true)
    private Set<Consulta> consultas = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cidades", "usuarios", "especialistas" }, allowSetters = true)
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "usuarios", "especialistas", "estado" }, allowSetters = true)
    private Cidade cidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Usuario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Usuario cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Instant getBirthDate() {
        return this.birthDate;
    }

    public Usuario birthDate(Instant birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public Usuario internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public Set<Avaliacao> getAvaliacaos() {
        return this.avaliacaos;
    }

    public void setAvaliacaos(Set<Avaliacao> avaliacaos) {
        if (this.avaliacaos != null) {
            this.avaliacaos.forEach(i -> i.setAvaliador(null));
        }
        if (avaliacaos != null) {
            avaliacaos.forEach(i -> i.setAvaliador(this));
        }
        this.avaliacaos = avaliacaos;
    }

    public Usuario avaliacaos(Set<Avaliacao> avaliacaos) {
        this.setAvaliacaos(avaliacaos);
        return this;
    }

    public Usuario addAvaliacao(Avaliacao avaliacao) {
        this.avaliacaos.add(avaliacao);
        avaliacao.setAvaliador(this);
        return this;
    }

    public Usuario removeAvaliacao(Avaliacao avaliacao) {
        this.avaliacaos.remove(avaliacao);
        avaliacao.setAvaliador(null);
        return this;
    }

    public Set<Consulta> getConsultas() {
        return this.consultas;
    }

    public void setConsultas(Set<Consulta> consultas) {
        if (this.consultas != null) {
            this.consultas.forEach(i -> i.setCliente(null));
        }
        if (consultas != null) {
            consultas.forEach(i -> i.setCliente(this));
        }
        this.consultas = consultas;
    }

    public Usuario consultas(Set<Consulta> consultas) {
        this.setConsultas(consultas);
        return this;
    }

    public Usuario addConsulta(Consulta consulta) {
        this.consultas.add(consulta);
        consulta.setCliente(this);
        return this;
    }

    public Usuario removeConsulta(Consulta consulta) {
        this.consultas.remove(consulta);
        consulta.setCliente(null);
        return this;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Usuario estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Usuario cidade(Cidade cidade) {
        this.setCidade(cidade);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        return getId() != null && getId().equals(((Usuario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", cpf='" + getCpf() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            "}";
    }
}
