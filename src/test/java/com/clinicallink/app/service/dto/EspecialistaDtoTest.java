package com.clinicallink.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EspecialistaDtoTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspecialistaDto.class);
        EspecialistaDto especialistaDto1 = new EspecialistaDto();
        especialistaDto1.setId(1L);
        EspecialistaDto especialistaDto2 = new EspecialistaDto();
        assertThat(especialistaDto1).isNotEqualTo(especialistaDto2);
        especialistaDto2.setId(especialistaDto1.getId());
        assertThat(especialistaDto1).isEqualTo(especialistaDto2);
        especialistaDto2.setId(2L);
        assertThat(especialistaDto1).isNotEqualTo(especialistaDto2);
        especialistaDto1.setId(null);
        assertThat(especialistaDto1).isNotEqualTo(especialistaDto2);
    }
}
