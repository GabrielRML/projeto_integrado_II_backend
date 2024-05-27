package com.clinicallink.app.domain;

import static com.clinicallink.app.domain.AvaliacaoTestSamples.*;
import static com.clinicallink.app.domain.ConsultaTestSamples.*;
import static com.clinicallink.app.domain.EspecialistaTestSamples.*;
import static com.clinicallink.app.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConsultaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consulta.class);
        Consulta consulta1 = getConsultaSample1();
        Consulta consulta2 = new Consulta();
        assertThat(consulta1).isNotEqualTo(consulta2);

        consulta2.setId(consulta1.getId());
        assertThat(consulta1).isEqualTo(consulta2);

        consulta2 = getConsultaSample2();
        assertThat(consulta1).isNotEqualTo(consulta2);
    }

    @Test
    void avaliacaoTest() throws Exception {
        Consulta consulta = getConsultaRandomSampleGenerator();
        Avaliacao avaliacaoBack = getAvaliacaoRandomSampleGenerator();

        consulta.setAvaliacao(avaliacaoBack);
        assertThat(consulta.getAvaliacao()).isEqualTo(avaliacaoBack);

        consulta.avaliacao(null);
        assertThat(consulta.getAvaliacao()).isNull();
    }

    @Test
    void prestadorTest() throws Exception {
        Consulta consulta = getConsultaRandomSampleGenerator();
        Especialista especialistaBack = getEspecialistaRandomSampleGenerator();

        consulta.setPrestador(especialistaBack);
        assertThat(consulta.getPrestador()).isEqualTo(especialistaBack);

        consulta.prestador(null);
        assertThat(consulta.getPrestador()).isNull();
    }

    @Test
    void clienteTest() throws Exception {
        Consulta consulta = getConsultaRandomSampleGenerator();
        Usuario usuarioBack = getUsuarioRandomSampleGenerator();

        consulta.setCliente(usuarioBack);
        assertThat(consulta.getCliente()).isEqualTo(usuarioBack);

        consulta.cliente(null);
        assertThat(consulta.getCliente()).isNull();
    }
}
