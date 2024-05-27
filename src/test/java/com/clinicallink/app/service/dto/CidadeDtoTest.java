package com.clinicallink.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CidadeDtoTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CidadeDto.class);
        CidadeDto cidadeDto1 = new CidadeDto();
        cidadeDto1.setId(1L);
        CidadeDto cidadeDto2 = new CidadeDto();
        assertThat(cidadeDto1).isNotEqualTo(cidadeDto2);
        cidadeDto2.setId(cidadeDto1.getId());
        assertThat(cidadeDto1).isEqualTo(cidadeDto2);
        cidadeDto2.setId(2L);
        assertThat(cidadeDto1).isNotEqualTo(cidadeDto2);
        cidadeDto1.setId(null);
        assertThat(cidadeDto1).isNotEqualTo(cidadeDto2);
    }
}
