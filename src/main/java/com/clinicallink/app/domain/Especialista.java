package com.clinicallink.app.domain;

import com.clinicallink.app.domain.enumeration.SpecialistType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Especialista.
 */
@Entity
@Table(name = "especialista")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Especialista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "identification")
    private String identification;

    @Column(name = "birth_date")
    private Instant birthDate;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "time_session")
    private Integer timeSession;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialist_type")
    private SpecialistType specialistType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User internalUser;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "especialista")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "especialidade", "especialista" }, allowSetters = true)
    private Set<EspecialidadeEspecialista> especialidadeEspecialistas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "avaliado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "consulta", "avaliado", "avaliador" }, allowSetters = true)
    private Set<Avaliacao> avaliacaos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "prestador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "avaliacao", "prestador", "cliente" }, allowSetters = true)
    private Set<Consulta> consultas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supervisorId")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "usuarios", "especialistas", "estado" }, allowSetters = true)
    private Cidade cidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "especialistas" }, allowSetters = true)
    private Universidade universidade;

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
    private Especialista supervisorId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Especialista id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Especialista cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getIdentification() {
        return this.identification;
    }

    public Especialista identification(String identification) {
        this.setIdentification(identification);
        return this;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Instant getBirthDate() {
        return this.birthDate;
    }

    public Especialista birthDate(Instant birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return this.description;
    }

    public Especialista description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return this.price;
    }

    public Especialista price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTimeSession() {
        return this.timeSession;
    }

    public Especialista timeSession(Integer timeSession) {
        this.setTimeSession(timeSession);
        return this;
    }

    public void setTimeSession(Integer timeSession) {
        this.timeSession = timeSession;
    }

    public SpecialistType getSpecialistType() {
        return this.specialistType;
    }

    public Especialista specialistType(SpecialistType specialistType) {
        this.setSpecialistType(specialistType);
        return this;
    }

    public void setSpecialistType(SpecialistType specialistType) {
        this.specialistType = specialistType;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public Especialista internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public Set<EspecialidadeEspecialista> getEspecialidadeEspecialistas() {
        return this.especialidadeEspecialistas;
    }

    public void setEspecialidadeEspecialistas(Set<EspecialidadeEspecialista> especialidadeEspecialistas) {
        if (this.especialidadeEspecialistas != null) {
            this.especialidadeEspecialistas.forEach(i -> i.setEspecialista(null));
        }
        if (especialidadeEspecialistas != null) {
            especialidadeEspecialistas.forEach(i -> i.setEspecialista(this));
        }
        this.especialidadeEspecialistas = especialidadeEspecialistas;
    }

    public Especialista especialidadeEspecialistas(Set<EspecialidadeEspecialista> especialidadeEspecialistas) {
        this.setEspecialidadeEspecialistas(especialidadeEspecialistas);
        return this;
    }

    public Especialista addEspecialidadeEspecialista(EspecialidadeEspecialista especialidadeEspecialista) {
        this.especialidadeEspecialistas.add(especialidadeEspecialista);
        especialidadeEspecialista.setEspecialista(this);
        return this;
    }

    public Especialista removeEspecialidadeEspecialista(EspecialidadeEspecialista especialidadeEspecialista) {
        this.especialidadeEspecialistas.remove(especialidadeEspecialista);
        especialidadeEspecialista.setEspecialista(null);
        return this;
    }

    public Set<Avaliacao> getAvaliacaos() {
        return this.avaliacaos;
    }

    public void setAvaliacaos(Set<Avaliacao> avaliacaos) {
        if (this.avaliacaos != null) {
            this.avaliacaos.forEach(i -> i.setAvaliado(null));
        }
        if (avaliacaos != null) {
            avaliacaos.forEach(i -> i.setAvaliado(this));
        }
        this.avaliacaos = avaliacaos;
    }

    public Especialista avaliacaos(Set<Avaliacao> avaliacaos) {
        this.setAvaliacaos(avaliacaos);
        return this;
    }

    public Especialista addAvaliacao(Avaliacao avaliacao) {
        this.avaliacaos.add(avaliacao);
        avaliacao.setAvaliado(this);
        return this;
    }

    public Especialista removeAvaliacao(Avaliacao avaliacao) {
        this.avaliacaos.remove(avaliacao);
        avaliacao.setAvaliado(null);
        return this;
    }

    public Set<Consulta> getConsultas() {
        return this.consultas;
    }

    public void setConsultas(Set<Consulta> consultas) {
        if (this.consultas != null) {
            this.consultas.forEach(i -> i.setPrestador(null));
        }
        if (consultas != null) {
            consultas.forEach(i -> i.setPrestador(this));
        }
        this.consultas = consultas;
    }

    public Especialista consultas(Set<Consulta> consultas) {
        this.setConsultas(consultas);
        return this;
    }

    public Especialista addConsulta(Consulta consulta) {
        this.consultas.add(consulta);
        consulta.setPrestador(this);
        return this;
    }

    public Especialista removeConsulta(Consulta consulta) {
        this.consultas.remove(consulta);
        consulta.setPrestador(null);
        return this;
    }

    public Set<Especialista> getEspecialistas() {
        return this.especialistas;
    }

    public void setEspecialistas(Set<Especialista> especialistas) {
        if (this.especialistas != null) {
            this.especialistas.forEach(i -> i.setSupervisorId(null));
        }
        if (especialistas != null) {
            especialistas.forEach(i -> i.setSupervisorId(this));
        }
        this.especialistas = especialistas;
    }

    public Especialista especialistas(Set<Especialista> especialistas) {
        this.setEspecialistas(especialistas);
        return this;
    }

    public Especialista addEspecialista(Especialista especialista) {
        this.especialistas.add(especialista);
        especialista.setSupervisorId(this);
        return this;
    }

    public Especialista removeEspecialista(Especialista especialista) {
        this.especialistas.remove(especialista);
        especialista.setSupervisorId(null);
        return this;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Especialista estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Especialista cidade(Cidade cidade) {
        this.setCidade(cidade);
        return this;
    }

    public Universidade getUniversidade() {
        return this.universidade;
    }

    public void setUniversidade(Universidade universidade) {
        this.universidade = universidade;
    }

    public Especialista universidade(Universidade universidade) {
        this.setUniversidade(universidade);
        return this;
    }

    public Especialista getSupervisorId() {
        return this.supervisorId;
    }

    public void setSupervisorId(Especialista especialista) {
        this.supervisorId = especialista;
    }

    public Especialista supervisorId(Especialista especialista) {
        this.setSupervisorId(especialista);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Especialista)) {
            return false;
        }
        return getId() != null && getId().equals(((Especialista) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Especialista{" +
            "id=" + getId() +
            ", cpf='" + getCpf() + "'" +
            ", identification='" + getIdentification() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", timeSession=" + getTimeSession() +
            ", specialistType='" + getSpecialistType() + "'" +
            "}";
    }
}
