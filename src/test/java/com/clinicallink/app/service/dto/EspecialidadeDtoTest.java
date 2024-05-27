package com.clinicallink.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EspecialidadeDtoTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspecialidadeDto.class);
        EspecialidadeDto especialidadeDto1 = new EspecialidadeDto();
        especialidadeDto1.setId(1L);
        EspecialidadeDto especialidadeDto2 = new EspecialidadeDto();
        assertThat(especialidadeDto1).isNotEqualTo(especialidadeDto2);
        especialidadeDto2.setId(especialidadeDto1.getId());
        assertThat(especialidadeDto1).isEqualTo(especialidadeDto2);
        especialidadeDto2.setId(2L);
        assertThat(especialidadeDto1).isNotEqualTo(especialidadeDto2);
        especialidadeDto1.setId(null);
        assertThat(especialidadeDto1).isNotEqualTo(especialidadeDto2);
    }
}
