package com.clinicallink.app.domain;

import static com.clinicallink.app.domain.AvaliacaoTestSamples.*;
import static com.clinicallink.app.domain.ConsultaTestSamples.*;
import static com.clinicallink.app.domain.EspecialistaTestSamples.*;
import static com.clinicallink.app.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvaliacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avaliacao.class);
        Avaliacao avaliacao1 = getAvaliacaoSample1();
        Avaliacao avaliacao2 = new Avaliacao();
        assertThat(avaliacao1).isNotEqualTo(avaliacao2);

        avaliacao2.setId(avaliacao1.getId());
        assertThat(avaliacao1).isEqualTo(avaliacao2);

        avaliacao2 = getAvaliacaoSample2();
        assertThat(avaliacao1).isNotEqualTo(avaliacao2);
    }

    @Test
    void consultaTest() throws Exception {
        Avaliacao avaliacao = getAvaliacaoRandomSampleGenerator();
        Consulta consultaBack = getConsultaRandomSampleGenerator();

        avaliacao.setConsulta(consultaBack);
        assertThat(avaliacao.getConsulta()).isEqualTo(consultaBack);
        assertThat(consultaBack.getAvaliacao()).isEqualTo(avaliacao);

        avaliacao.consulta(null);
        assertThat(avaliacao.getConsulta()).isNull();
        assertThat(consultaBack.getAvaliacao()).isNull();
    }

    @Test
    void avaliadoTest() throws Exception {
        Avaliacao avaliacao = getAvaliacaoRandomSampleGenerator();
        Especialista especialistaBack = getEspecialistaRandomSampleGenerator();

        avaliacao.setAvaliado(especialistaBack);
        assertThat(avaliacao.getAvaliado()).isEqualTo(especialistaBack);

        avaliacao.avaliado(null);
        assertThat(avaliacao.getAvaliado()).isNull();
    }

    @Test
    void avaliadorTest() throws Exception {
        Avaliacao avaliacao = getAvaliacaoRandomSampleGenerator();
        Usuario usuarioBack = getUsuarioRandomSampleGenerator();

        avaliacao.setAvaliador(usuarioBack);
        assertThat(avaliacao.getAvaliador()).isEqualTo(usuarioBack);

        avaliacao.avaliador(null);
        assertThat(avaliacao.getAvaliador()).isNull();
    }
}
