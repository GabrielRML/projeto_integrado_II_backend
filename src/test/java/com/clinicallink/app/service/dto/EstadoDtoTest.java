package com.clinicallink.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstadoDtoTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoDto.class);
        EstadoDto estadoDto1 = new EstadoDto();
        estadoDto1.setId(1L);
        EstadoDto estadoDto2 = new EstadoDto();
        assertThat(estadoDto1).isNotEqualTo(estadoDto2);
        estadoDto2.setId(estadoDto1.getId());
        assertThat(estadoDto1).isEqualTo(estadoDto2);
        estadoDto2.setId(2L);
        assertThat(estadoDto1).isNotEqualTo(estadoDto2);
        estadoDto1.setId(null);
        assertThat(estadoDto1).isNotEqualTo(estadoDto2);
    }
}
