package com.clinicallink.app.service.dto;

import com.clinicallink.app.domain.enumeration.StatusConsulta;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.clinicallink.app.domain.Consulta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConsultaDto implements Serializable {

    private Long id;

    private Instant date;

    private String reason;

    private String link;

    private StatusConsulta status;

    private AvaliacaoDto avaliacao;

    private EspecialistaDto prestador;

    private UsuarioDto cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }

    public AvaliacaoDto getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(AvaliacaoDto avaliacao) {
        this.avaliacao = avaliacao;
    }

    public EspecialistaDto getPrestador() {
        return prestador;
    }

    public void setPrestador(EspecialistaDto prestador) {
        this.prestador = prestador;
    }

    public UsuarioDto getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioDto cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsultaDto)) {
            return false;
        }

        ConsultaDto consultaDto = (ConsultaDto) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, consultaDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsultaDto{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", reason='" + getReason() + "'" +
            ", link='" + getLink() + "'" +
            ", status='" + getStatus() + "'" +
            ", avaliacao=" + getAvaliacao() +
            ", prestador=" + getPrestador() +
            ", cliente=" + getCliente() +
            "}";
    }
}
