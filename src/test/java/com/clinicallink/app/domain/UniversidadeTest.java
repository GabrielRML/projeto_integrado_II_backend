package com.clinicallink.app.domain;

import static com.clinicallink.app.domain.EspecialistaTestSamples.*;
import static com.clinicallink.app.domain.UniversidadeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UniversidadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Universidade.class);
        Universidade universidade1 = getUniversidadeSample1();
        Universidade universidade2 = new Universidade();
        assertThat(universidade1).isNotEqualTo(universidade2);

        universidade2.setId(universidade1.getId());
        assertThat(universidade1).isEqualTo(universidade2);

        universidade2 = getUniversidadeSample2();
        assertThat(universidade1).isNotEqualTo(universidade2);
    }

    @Test
    void especialistaTest() throws Exception {
        Universidade universidade = getUniversidadeRandomSampleGenerator();
        Especialista especialistaBack = getEspecialistaRandomSampleGenerator();

        universidade.addEspecialista(especialistaBack);
        assertThat(universidade.getEspecialistas()).containsOnly(especialistaBack);
        assertThat(especialistaBack.getUniversidade()).isEqualTo(universidade);

        universidade.removeEspecialista(especialistaBack);
        assertThat(universidade.getEspecialistas()).doesNotContain(especialistaBack);
        assertThat(especialistaBack.getUniversidade()).isNull();

        universidade.especialistas(new HashSet<>(Set.of(especialistaBack)));
        assertThat(universidade.getEspecialistas()).containsOnly(especialistaBack);
        assertThat(especialistaBack.getUniversidade()).isEqualTo(universidade);

        universidade.setEspecialistas(new HashSet<>());
        assertThat(universidade.getEspecialistas()).doesNotContain(especialistaBack);
        assertThat(especialistaBack.getUniversidade()).isNull();
    }
}
