package com.clinicallink.app.domain;

import static com.clinicallink.app.domain.AvaliacaoTestSamples.*;
import static com.clinicallink.app.domain.CidadeTestSamples.*;
import static com.clinicallink.app.domain.ConsultaTestSamples.*;
import static com.clinicallink.app.domain.EspecialidadeEspecialistaTestSamples.*;
import static com.clinicallink.app.domain.EspecialistaTestSamples.*;
import static com.clinicallink.app.domain.EspecialistaTestSamples.*;
import static com.clinicallink.app.domain.EstadoTestSamples.*;
import static com.clinicallink.app.domain.UniversidadeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EspecialistaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Especialista.class);
        Especialista especialista1 = getEspecialistaSample1();
        Especialista especialista2 = new Especialista();
        assertThat(especialista1).isNotEqualTo(especialista2);

        especialista2.setId(especialista1.getId());
        assertThat(especialista1).isEqualTo(especialista2);

        especialista2 = getEspecialistaSample2();
        assertThat(especialista1).isNotEqualTo(especialista2);
    }

    @Test
    void especialidadeEspecialistaTest() throws Exception {
        Especialista especialista = getEspecialistaRandomSampleGenerator();
        EspecialidadeEspecialista especialidadeEspecialistaBack = getEspecialidadeEspecialistaRandomSampleGenerator();

        especialista.addEspecialidadeEspecialista(especialidadeEspecialistaBack);
        assertThat(especialista.getEspecialidadeEspecialistas()).containsOnly(especialidadeEspecialistaBack);
        assertThat(especialidadeEspecialistaBack.getEspecialista()).isEqualTo(especialista);

        especialista.removeEspecialidadeEspecialista(especialidadeEspecialistaBack);
        assertThat(especialista.getEspecialidadeEspecialistas()).doesNotContain(especialidadeEspecialistaBack);
        assertThat(especialidadeEspecialistaBack.getEspecialista()).isNull();

        especialista.especialidadeEspecialistas(new HashSet<>(Set.of(especialidadeEspecialistaBack)));
        assertThat(especialista.getEspecialidadeEspecialistas()).containsOnly(especialidadeEspecialistaBack);
        assertThat(especialidadeEspecialistaBack.getEspecialista()).isEqualTo(especialista);

        especialista.setEspecialidadeEspecialistas(new HashSet<>());
        assertThat(especialista.getEspecialidadeEspecialistas()).doesNotContain(especialidadeEspecialistaBack);
        assertThat(especialidadeEspecialistaBack.getEspecialista()).isNull();
    }

    @Test
    void avaliacaoTest() throws Exception {
        Especialista especialista = getEspecialistaRandomSampleGenerator();
        Avaliacao avaliacaoBack = getAvaliacaoRandomSampleGenerator();

        especialista.addAvaliacao(avaliacaoBack);
        assertThat(especialista.getAvaliacaos()).containsOnly(avaliacaoBack);
        assertThat(avaliacaoBack.getAvaliado()).isEqualTo(especialista);

        especialista.removeAvaliacao(avaliacaoBack);
        assertThat(especialista.getAvaliacaos()).doesNotContain(avaliacaoBack);
        assertThat(avaliacaoBack.getAvaliado()).isNull();

        especialista.avaliacaos(new HashSet<>(Set.of(avaliacaoBack)));
        assertThat(especialista.getAvaliacaos()).containsOnly(avaliacaoBack);
        assertThat(avaliacaoBack.getAvaliado()).isEqualTo(especialista);

        especialista.setAvaliacaos(new HashSet<>());
        assertThat(especialista.getAvaliacaos()).doesNotContain(avaliacaoBack);
        assertThat(avaliacaoBack.getAvaliado()).isNull();
    }

    @Test
    void consultaTest() throws Exception {
        Especialista especialista = getEspecialistaRandomSampleGenerator();
        Consulta consultaBack = getConsultaRandomSampleGenerator();

        especialista.addConsulta(consultaBack);
        assertThat(especialista.getConsultas()).containsOnly(consultaBack);
        assertThat(consultaBack.getPrestador()).isEqualTo(especialista);

        especialista.removeConsulta(consultaBack);
        assertThat(especialista.getConsultas()).doesNotContain(consultaBack);
        assertThat(consultaBack.getPrestador()).isNull();

        especialista.consultas(new HashSet<>(Set.of(consultaBack)));
        assertThat(especialista.getConsultas()).containsOnly(consultaBack);
        assertThat(consultaBack.getPrestador()).isEqualTo(especialista);

        especialista.setConsultas(new HashSet<>());
        assertThat(especialista.getConsultas()).doesNotContain(consultaBack);
        assertThat(consultaBack.getPrestador()).isNull();
    }

    @Test
    void especialistaTest() throws Exception {
        Especialista especialista = getEspecialistaRandomSampleGenerator();
        Especialista especialistaBack = getEspecialistaRandomSampleGenerator();

        especialista.addEspecialista(especialistaBack);
        assertThat(especialista.getEspecialistas()).containsOnly(especialistaBack);
        assertThat(especialistaBack.getSupervisorId()).isEqualTo(especialista);

        especialista.removeEspecialista(especialistaBack);
        assertThat(especialista.getEspecialistas()).doesNotContain(especialistaBack);
        assertThat(especialistaBack.getSupervisorId()).isNull();

        especialista.especialistas(new HashSet<>(Set.of(especialistaBack)));
        assertThat(especialista.getEspecialistas()).containsOnly(especialistaBack);
        assertThat(especialistaBack.getSupervisorId()).isEqualTo(especialista);

        especialista.setEspecialistas(new HashSet<>());
        assertThat(especialista.getEspecialistas()).doesNotContain(especialistaBack);
        assertThat(especialistaBack.getSupervisorId()).isNull();
    }

    @Test
    void estadoTest() throws Exception {
        Especialista especialista = getEspecialistaRandomSampleGenerator();
        Estado estadoBack = getEstadoRandomSampleGenerator();

        especialista.setEstado(estadoBack);
        assertThat(especialista.getEstado()).isEqualTo(estadoBack);

        especialista.estado(null);
        assertThat(especialista.getEstado()).isNull();
    }

    @Test
    void cidadeTest() throws Exception {
        Especialista especialista = getEspecialistaRandomSampleGenerator();
        Cidade cidadeBack = getCidadeRandomSampleGenerator();

        especialista.setCidade(cidadeBack);
        assertThat(especialista.getCidade()).isEqualTo(cidadeBack);

        especialista.cidade(null);
        assertThat(especialista.getCidade()).isNull();
    }

    @Test
    void universidadeTest() throws Exception {
        Especialista especialista = getEspecialistaRandomSampleGenerator();
        Universidade universidadeBack = getUniversidadeRandomSampleGenerator();

        especialista.setUniversidade(universidadeBack);
        assertThat(especialista.getUniversidade()).isEqualTo(universidadeBack);

        especialista.universidade(null);
        assertThat(especialista.getUniversidade()).isNull();
    }

    @Test
    void supervisorIdTest() throws Exception {
        Especialista especialista = getEspecialistaRandomSampleGenerator();
        Especialista especialistaBack = getEspecialistaRandomSampleGenerator();

        especialista.setSupervisorId(especialistaBack);
        assertThat(especialista.getSupervisorId()).isEqualTo(especialistaBack);

        especialista.supervisorId(null);
        assertThat(especialista.getSupervisorId()).isNull();
    }
}
