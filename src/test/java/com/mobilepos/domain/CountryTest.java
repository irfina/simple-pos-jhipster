package com.mobilepos.domain;

import static com.mobilepos.domain.CountryTestSamples.*;
import static com.mobilepos.domain.ProvinceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mobilepos.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CountryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = getCountrySample1();
        Country country2 = new Country();
        assertThat(country1).isNotEqualTo(country2);

        country2.setIsoCode(country1.getIsoCode());
        assertThat(country1).isEqualTo(country2);

        country2 = getCountrySample2();
        assertThat(country1).isNotEqualTo(country2);
    }

    @Test
    void provinceTest() throws Exception {
        Country country = getCountryRandomSampleGenerator();
        Province provinceBack = getProvinceRandomSampleGenerator();

        country.addProvince(provinceBack);
        assertThat(country.getProvinces()).containsOnly(provinceBack);
        assertThat(provinceBack.getCountry()).isEqualTo(country);

        country.removeProvince(provinceBack);
        assertThat(country.getProvinces()).doesNotContain(provinceBack);
        assertThat(provinceBack.getCountry()).isNull();

        country.provinces(new HashSet<>(Set.of(provinceBack)));
        assertThat(country.getProvinces()).containsOnly(provinceBack);
        assertThat(provinceBack.getCountry()).isEqualTo(country);

        country.setProvinces(new HashSet<>());
        assertThat(country.getProvinces()).doesNotContain(provinceBack);
        assertThat(provinceBack.getCountry()).isNull();
    }
}
