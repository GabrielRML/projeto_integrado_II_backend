package com.clinicallink.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.clinicallink.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvaliacaoDtoTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvaliacaoDto.class);
        AvaliacaoDto avaliacaoDto1 = new AvaliacaoDto();
        avaliacaoDto1.setId(1L);
        AvaliacaoDto avaliacaoDto2 = new AvaliacaoDto();
        assertThat(avaliacaoDto1).isNotEqualTo(avaliacaoDto2);
        avaliacaoDto2.setId(avaliacaoDto1.getId());
        assertThat(avaliacaoDto1).isEqualTo(avaliacaoDto2);
        avaliacaoDto2.setId(2L);
        assertThat(avaliacaoDto1).isNotEqualTo(avaliacaoDto2);
        avaliacaoDto1.setId(null);
        assertThat(avaliacaoDto1).isNotEqualTo(avaliacaoDto2);
    }
}
