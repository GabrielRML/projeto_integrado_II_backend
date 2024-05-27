package com.clinicallink.app.domain;

import static com.clinicallink.app.domain.EspecialidadeEspecialistaTestSamples.*;
import static com.clinicallink.app.domain.EspecialidadeTestSamples.*;
import static com.clinicallink.app.domain.EspecialistaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EspecialidadeEspecialistaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspecialidadeEspecialista.class);
        EspecialidadeEspecialista especialidadeEspecialista1 = getEspecialidadeEspecialistaSample1();
        EspecialidadeEspecialista especialidadeEspecialista2 = new EspecialidadeEspecialista();
        assertThat(especialidadeEspecialista1).isNotEqualTo(especialidadeEspecialista2);

        especialidadeEspecialista2.setId(especialidadeEspecialista1.getId());
        assertThat(especialidadeEspecialista1).isEqualTo(especialidadeEspecialista2);

        especialidadeEspecialista2 = getEspecialidadeEspecialistaSample2();
        assertThat(especialidadeEspecialista1).isNotEqualTo(especialidadeEspecialista2);
    }

    @Test
    void especialidadeTest() throws Exception {
        EspecialidadeEspecialista especialidadeEspecialista = getEspecialidadeEspecialistaRandomSampleGenerator();
        Especialidade especialidadeBack = getEspecialidadeRandomSampleGenerator();

        especialidadeEspecialista.setEspecialidade(especialidadeBack);
        assertThat(especialidadeEspecialista.getEspecialidade()).isEqualTo(especialidadeBack);

        especialidadeEspecialista.especialidade(null);
        assertThat(especialidadeEspecialista.getEspecialidade()).isNull();
    }

    @Test
    void especialistaTest() throws Exception {
        EspecialidadeEspecialista especialidadeEspecialista = getEspecialidadeEspecialistaRandomSampleGenerator();
        Especialista especialistaBack = getEspecialistaRandomSampleGenerator();

        especialidadeEspecialista.setEspecialista(especialistaBack);
        assertThat(especialidadeEspecialista.getEspecialista()).isEqualTo(especialistaBack);

        especialidadeEspecialista.especialista(null);
        assertThat(especialidadeEspecialista.getEspecialista()).isNull();
    }
}
