package com.mobilepos.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mobilepos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryDTO.class);
        CountryDTO countryDTO1 = new CountryDTO();
        countryDTO1.setIsoCode("id1");
        CountryDTO countryDTO2 = new CountryDTO();
        assertThat(countryDTO1).isNotEqualTo(countryDTO2);
        countryDTO2.setIsoCode(countryDTO1.getIsoCode());
        assertThat(countryDTO1).isEqualTo(countryDTO2);
        countryDTO2.setIsoCode("id2");
        assertThat(countryDTO1).isNotEqualTo(countryDTO2);
        countryDTO1.setIsoCode(null);
        assertThat(countryDTO1).isNotEqualTo(countryDTO2);
    }
}
