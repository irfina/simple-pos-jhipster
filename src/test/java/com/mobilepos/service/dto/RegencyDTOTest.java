package com.mobilepos.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mobilepos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegencyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegencyDTO.class);
        RegencyDTO regencyDTO1 = new RegencyDTO();
        regencyDTO1.setId(1);
        RegencyDTO regencyDTO2 = new RegencyDTO();
        assertThat(regencyDTO1).isNotEqualTo(regencyDTO2);
        regencyDTO2.setId(regencyDTO1.getId());
        assertThat(regencyDTO1).isEqualTo(regencyDTO2);
        regencyDTO2.setId(2);
        assertThat(regencyDTO1).isNotEqualTo(regencyDTO2);
        regencyDTO1.setId(null);
        assertThat(regencyDTO1).isNotEqualTo(regencyDTO2);
    }
}
