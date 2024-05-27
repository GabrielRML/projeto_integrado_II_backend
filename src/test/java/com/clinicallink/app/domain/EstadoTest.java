package com.clinicallink.app.domain;

import static com.clinicallink.app.domain.CidadeTestSamples.*;
import static com.clinicallink.app.domain.EspecialistaTestSamples.*;
import static com.clinicallink.app.domain.EstadoTestSamples.*;
import static com.clinicallink.app.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EstadoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estado.class);
        Estado estado1 = getEstadoSample1();
        Estado estado2 = new Estado();
        assertThat(estado1).isNotEqualTo(estado2);

        estado2.setId(estado1.getId());
        assertThat(estado1).isEqualTo(estado2);

        estado2 = getEstadoSample2();
        assertThat(estado1).isNotEqualTo(estado2);
    }

    @Test
    void cidadeTest() throws Exception {
        Estado estado = getEstadoRandomSampleGenerator();
        Cidade cidadeBack = getCidadeRandomSampleGenerator();

        estado.addCidade(cidadeBack);
        assertThat(estado.getCidades()).containsOnly(cidadeBack);
        assertThat(cidadeBack.getEstado()).isEqualTo(estado);

        estado.removeCidade(cidadeBack);
        assertThat(estado.getCidades()).doesNotContain(cidadeBack);
        assertThat(cidadeBack.getEstado()).isNull();

        estado.cidades(new HashSet<>(Set.of(cidadeBack)));
        assertThat(estado.getCidades()).containsOnly(cidadeBack);
        assertThat(cidadeBack.getEstado()).isEqualTo(estado);

        estado.setCidades(new HashSet<>());
        assertThat(estado.getCidades()).doesNotContain(cidadeBack);
        assertThat(cidadeBack.getEstado()).isNull();
    }

    @Test
    void usuarioTest() throws Exception {
        Estado estado = getEstadoRandomSampleGenerator();
        Usuario usuarioBack = getUsuarioRandomSampleGenerator();

        estado.addUsuario(usuarioBack);
        assertThat(estado.getUsuarios()).containsOnly(usuarioBack);
        assertThat(usuarioBack.getEstado()).isEqualTo(estado);

        estado.removeUsuario(usuarioBack);
        assertThat(estado.getUsuarios()).doesNotContain(usuarioBack);
        assertThat(usuarioBack.getEstado()).isNull();

        estado.usuarios(new HashSet<>(Set.of(usuarioBack)));
        assertThat(estado.getUsuarios()).containsOnly(usuarioBack);
        assertThat(usuarioBack.getEstado()).isEqualTo(estado);

        estado.setUsuarios(new HashSet<>());
        assertThat(estado.getUsuarios()).doesNotContain(usuarioBack);
        assertThat(usuarioBack.getEstado()).isNull();
    }

    @Test
    void especialistaTest() throws Exception {
        Estado estado = getEstadoRandomSampleGenerator();
        Especialista especialistaBack = getEspecialistaRandomSampleGenerator();

        estado.addEspecialista(especialistaBack);
        assertThat(estado.getEspecialistas()).containsOnly(especialistaBack);
        assertThat(especialistaBack.getEstado()).isEqualTo(estado);

        estado.removeEspecialista(especialistaBack);
        assertThat(estado.getEspecialistas()).doesNotContain(especialistaBack);
        assertThat(especialistaBack.getEstado()).isNull();

        estado.especialistas(new HashSet<>(Set.of(especialistaBack)));
        assertThat(estado.getEspecialistas()).containsOnly(especialistaBack);
        assertThat(especialistaBack.getEstado()).isEqualTo(estado);

        estado.setEspecialistas(new HashSet<>());
        assertThat(estado.getEspecialistas()).doesNotContain(especialistaBack);
        assertThat(especialistaBack.getEstado()).isNull();
    }
}
