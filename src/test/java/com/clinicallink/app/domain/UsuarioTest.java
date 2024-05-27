package com.clinicallink.app.domain;

import static com.clinicallink.app.domain.AvaliacaoTestSamples.*;
import static com.clinicallink.app.domain.CidadeTestSamples.*;
import static com.clinicallink.app.domain.ConsultaTestSamples.*;
import static com.clinicallink.app.domain.EstadoTestSamples.*;
import static com.clinicallink.app.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usuario.class);
        Usuario usuario1 = getUsuarioSample1();
        Usuario usuario2 = new Usuario();
        assertThat(usuario1).isNotEqualTo(usuario2);

        usuario2.setId(usuario1.getId());
        assertThat(usuario1).isEqualTo(usuario2);

        usuario2 = getUsuarioSample2();
        assertThat(usuario1).isNotEqualTo(usuario2);
    }

    @Test
    void avaliacaoTest() throws Exception {
        Usuario usuario = getUsuarioRandomSampleGenerator();
        Avaliacao avaliacaoBack = getAvaliacaoRandomSampleGenerator();

        usuario.addAvaliacao(avaliacaoBack);
        assertThat(usuario.getAvaliacaos()).containsOnly(avaliacaoBack);
        assertThat(avaliacaoBack.getAvaliador()).isEqualTo(usuario);

        usuario.removeAvaliacao(avaliacaoBack);
        assertThat(usuario.getAvaliacaos()).doesNotContain(avaliacaoBack);
        assertThat(avaliacaoBack.getAvaliador()).isNull();

        usuario.avaliacaos(new HashSet<>(Set.of(avaliacaoBack)));
        assertThat(usuario.getAvaliacaos()).containsOnly(avaliacaoBack);
        assertThat(avaliacaoBack.getAvaliador()).isEqualTo(usuario);

        usuario.setAvaliacaos(new HashSet<>());
        assertThat(usuario.getAvaliacaos()).doesNotContain(avaliacaoBack);
        assertThat(avaliacaoBack.getAvaliador()).isNull();
    }

    @Test
    void consultaTest() throws Exception {
        Usuario usuario = getUsuarioRandomSampleGenerator();
        Consulta consultaBack = getConsultaRandomSampleGenerator();

        usuario.addConsulta(consultaBack);
        assertThat(usuario.getConsultas()).containsOnly(consultaBack);
        assertThat(consultaBack.getCliente()).isEqualTo(usuario);

        usuario.removeConsulta(consultaBack);
        assertThat(usuario.getConsultas()).doesNotContain(consultaBack);
        assertThat(consultaBack.getCliente()).isNull();

        usuario.consultas(new HashSet<>(Set.of(consultaBack)));
        assertThat(usuario.getConsultas()).containsOnly(consultaBack);
        assertThat(consultaBack.getCliente()).isEqualTo(usuario);

        usuario.setConsultas(new HashSet<>());
        assertThat(usuario.getConsultas()).doesNotContain(consultaBack);
        assertThat(consultaBack.getCliente()).isNull();
    }

    @Test
    void estadoTest() throws Exception {
        Usuario usuario = getUsuarioRandomSampleGenerator();
        Estado estadoBack = getEstadoRandomSampleGenerator();

        usuario.setEstado(estadoBack);
        assertThat(usuario.getEstado()).isEqualTo(estadoBack);

        usuario.estado(null);
        assertThat(usuario.getEstado()).isNull();
    }

    @Test
    void cidadeTest() throws Exception {
        Usuario usuario = getUsuarioRandomSampleGenerator();
        Cidade cidadeBack = getCidadeRandomSampleGenerator();

        usuario.setCidade(cidadeBack);
        assertThat(usuario.getCidade()).isEqualTo(cidadeBack);

        usuario.cidade(null);
        assertThat(usuario.getCidade()).isNull();
    }
}
