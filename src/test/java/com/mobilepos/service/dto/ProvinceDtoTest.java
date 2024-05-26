package com.mobilepos.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mobilepos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProvinceDtoTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProvinceDto.class);
        ProvinceDto provinceDto1 = new ProvinceDto();
        provinceDto1.setId(1);
        ProvinceDto provinceDto2 = new ProvinceDto();
        assertThat(provinceDto1).isNotEqualTo(provinceDto2);
        provinceDto2.setId(provinceDto1.getId());
        assertThat(provinceDto1).isEqualTo(provinceDto2);
        provinceDto2.setId(2);
        assertThat(provinceDto1).isNotEqualTo(provinceDto2);
        provinceDto1.setId(null);
        assertThat(provinceDto1).isNotEqualTo(provinceDto2);
    }
}
