package com.clinicallink.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EspecialidadeEspecialistaDtoTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspecialidadeEspecialistaDto.class);
        EspecialidadeEspecialistaDto especialidadeEspecialistaDto1 = new EspecialidadeEspecialistaDto();
        especialidadeEspecialistaDto1.setId(1L);
        EspecialidadeEspecialistaDto especialidadeEspecialistaDto2 = new EspecialidadeEspecialistaDto();
        assertThat(especialidadeEspecialistaDto1).isNotEqualTo(especialidadeEspecialistaDto2);
        especialidadeEspecialistaDto2.setId(especialidadeEspecialistaDto1.getId());
        assertThat(especialidadeEspecialistaDto1).isEqualTo(especialidadeEspecialistaDto2);
        especialidadeEspecialistaDto2.setId(2L);
        assertThat(especialidadeEspecialistaDto1).isNotEqualTo(especialidadeEspecialistaDto2);
        especialidadeEspecialistaDto1.setId(null);
        assertThat(especialidadeEspecialistaDto1).isNotEqualTo(especialidadeEspecialistaDto2);
    }
}
