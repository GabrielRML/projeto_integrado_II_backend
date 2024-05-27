package com.clinicallink.app.domain;

import static com.clinicallink.app.domain.EspecialidadeEspecialistaTestSamples.*;
import static com.clinicallink.app.domain.EspecialidadeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EspecialidadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Especialidade.class);
        Especialidade especialidade1 = getEspecialidadeSample1();
        Especialidade especialidade2 = new Especialidade();
        assertThat(especialidade1).isNotEqualTo(especialidade2);

        especialidade2.setId(especialidade1.getId());
        assertThat(especialidade1).isEqualTo(especialidade2);

        especialidade2 = getEspecialidadeSample2();
        assertThat(especialidade1).isNotEqualTo(especialidade2);
    }

    @Test
    void especialidadeEspecialistaTest() throws Exception {
        Especialidade especialidade = getEspecialidadeRandomSampleGenerator();
        EspecialidadeEspecialista especialidadeEspecialistaBack = getEspecialidadeEspecialistaRandomSampleGenerator();

        especialidade.addEspecialidadeEspecialista(especialidadeEspecialistaBack);
        assertThat(especialidade.getEspecialidadeEspecialistas()).containsOnly(especialidadeEspecialistaBack);
        assertThat(especialidadeEspecialistaBack.getEspecialidade()).isEqualTo(especialidade);

        especialidade.removeEspecialidadeEspecialista(especialidadeEspecialistaBack);
        assertThat(especialidade.getEspecialidadeEspecialistas()).doesNotContain(especialidadeEspecialistaBack);
        assertThat(especialidadeEspecialistaBack.getEspecialidade()).isNull();

        especialidade.especialidadeEspecialistas(new HashSet<>(Set.of(especialidadeEspecialistaBack)));
        assertThat(especialidade.getEspecialidadeEspecialistas()).containsOnly(especialidadeEspecialistaBack);
        assertThat(especialidadeEspecialistaBack.getEspecialidade()).isEqualTo(especialidade);

        especialidade.setEspecialidadeEspecialistas(new HashSet<>());
        assertThat(especialidade.getEspecialidadeEspecialistas()).doesNotContain(especialidadeEspecialistaBack);
        assertThat(especialidadeEspecialistaBack.getEspecialidade()).isNull();
    }
}
