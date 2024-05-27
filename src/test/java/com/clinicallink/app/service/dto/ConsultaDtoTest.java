package com.clinicallink.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConsultaDtoTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultaDto.class);
        ConsultaDto consultaDto1 = new ConsultaDto();
        consultaDto1.setId(1L);
        ConsultaDto consultaDto2 = new ConsultaDto();
        assertThat(consultaDto1).isNotEqualTo(consultaDto2);
        consultaDto2.setId(consultaDto1.getId());
        assertThat(consultaDto1).isEqualTo(consultaDto2);
        consultaDto2.setId(2L);
        assertThat(consultaDto1).isNotEqualTo(consultaDto2);
        consultaDto1.setId(null);
        assertThat(consultaDto1).isNotEqualTo(consultaDto2);
    }
}
