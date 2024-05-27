package com.clinicallink.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioDtoTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioDto.class);
        UsuarioDto usuarioDto1 = new UsuarioDto();
        usuarioDto1.setId(1L);
        UsuarioDto usuarioDto2 = new UsuarioDto();
        assertThat(usuarioDto1).isNotEqualTo(usuarioDto2);
        usuarioDto2.setId(usuarioDto1.getId());
        assertThat(usuarioDto1).isEqualTo(usuarioDto2);
        usuarioDto2.setId(2L);
        assertThat(usuarioDto1).isNotEqualTo(usuarioDto2);
        usuarioDto1.setId(null);
        assertThat(usuarioDto1).isNotEqualTo(usuarioDto2);
    }
}
