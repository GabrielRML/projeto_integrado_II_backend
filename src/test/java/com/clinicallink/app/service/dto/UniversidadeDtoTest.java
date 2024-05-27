package com.clinicallink.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UniversidadeDtoTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UniversidadeDto.class);
        UniversidadeDto universidadeDto1 = new UniversidadeDto();
        universidadeDto1.setId(1L);
        UniversidadeDto universidadeDto2 = new UniversidadeDto();
        assertThat(universidadeDto1).isNotEqualTo(universidadeDto2);
        universidadeDto2.setId(universidadeDto1.getId());
        assertThat(universidadeDto1).isEqualTo(universidadeDto2);
        universidadeDto2.setId(2L);
        assertThat(universidadeDto1).isNotEqualTo(universidadeDto2);
        universidadeDto1.setId(null);
        assertThat(universidadeDto1).isNotEqualTo(universidadeDto2);
    }
}
