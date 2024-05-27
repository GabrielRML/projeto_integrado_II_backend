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

class CidadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cidade.class);
        Cidade cidade1 = getCidadeSample1();
        Cidade cidade2 = new Cidade();
        assertThat(cidade1).isNotEqualTo(cidade2);

        cidade2.setId(cidade1.getId());
        assertThat(cidade1).isEqualTo(cidade2);

        cidade2 = getCidadeSample2();
        assertThat(cidade1).isNotEqualTo(cidade2);
    }

    @Test
    void usuarioTest() throws Exception {
        Cidade cidade = getCidadeRandomSampleGenerator();
        Usuario usuarioBack = getUsuarioRandomSampleGenerator();

        cidade.addUsuario(usuarioBack);
        assertThat(cidade.getUsuarios()).containsOnly(usuarioBack);
        assertThat(usuarioBack.getCidade()).isEqualTo(cidade);

        cidade.removeUsuario(usuarioBack);
        assertThat(cidade.getUsuarios()).doesNotContain(usuarioBack);
        assertThat(usuarioBack.getCidade()).isNull();

        cidade.usuarios(new HashSet<>(Set.of(usuarioBack)));
        assertThat(cidade.getUsuarios()).containsOnly(usuarioBack);
        assertThat(usuarioBack.getCidade()).isEqualTo(cidade);

        cidade.setUsuarios(new HashSet<>());
        assertThat(cidade.getUsuarios()).doesNotContain(usuarioBack);
        assertThat(usuarioBack.getCidade()).isNull();
    }

    @Test
    void especialistaTest() throws Exception {
        Cidade cidade = getCidadeRandomSampleGenerator();
        Especialista especialistaBack = getEspecialistaRandomSampleGenerator();

        cidade.addEspecialista(especialistaBack);
        assertThat(cidade.getEspecialistas()).containsOnly(especialistaBack);
        assertThat(especialistaBack.getCidade()).isEqualTo(cidade);

        cidade.removeEspecialista(especialistaBack);
        assertThat(cidade.getEspecialistas()).doesNotContain(especialistaBack);
        assertThat(especialistaBack.getCidade()).isNull();

        cidade.especialistas(new HashSet<>(Set.of(especialistaBack)));
        assertThat(cidade.getEspecialistas()).containsOnly(especialistaBack);
        assertThat(especialistaBack.getCidade()).isEqualTo(cidade);

        cidade.setEspecialistas(new HashSet<>());
        assertThat(cidade.getEspecialistas()).doesNotContain(especialistaBack);
        assertThat(especialistaBack.getCidade()).isNull();
    }

    @Test
    void estadoTest() throws Exception {
        Cidade cidade = getCidadeRandomSampleGenerator();
        Estado estadoBack = getEstadoRandomSampleGenerator();

        cidade.setEstado(estadoBack);
        assertThat(cidade.getEstado()).isEqualTo(estadoBack);

        cidade.estado(null);
        assertThat(cidade.getEstado()).isNull();
    }
}
