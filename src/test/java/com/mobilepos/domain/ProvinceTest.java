package com.mobilepos.domain;

import static com.mobilepos.domain.CountryTestSamples.*;
import static com.mobilepos.domain.ProvinceTestSamples.*;
import static com.mobilepos.domain.RegencyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mobilepos.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProvinceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Province.class);
        Province province1 = getProvinceSample1();
        Province province2 = new Province();
        assertThat(province1).isNotEqualTo(province2);

        province2.setId(province1.getId());
        assertThat(province1).isEqualTo(province2);

        province2 = getProvinceSample2();
        assertThat(province1).isNotEqualTo(province2);
    }

    @Test
    void regencyTest() throws Exception {
        Province province = getProvinceRandomSampleGenerator();
        Regency regencyBack = getRegencyRandomSampleGenerator();

        province.addRegency(regencyBack);
        assertThat(province.getRegencies()).containsOnly(regencyBack);
        assertThat(regencyBack.getProvince()).isEqualTo(province);

        province.removeRegency(regencyBack);
        assertThat(province.getRegencies()).doesNotContain(regencyBack);
        assertThat(regencyBack.getProvince()).isNull();

        province.regencies(new HashSet<>(Set.of(regencyBack)));
        assertThat(province.getRegencies()).containsOnly(regencyBack);
        assertThat(regencyBack.getProvince()).isEqualTo(province);

        province.setRegencies(new HashSet<>());
        assertThat(province.getRegencies()).doesNotContain(regencyBack);
        assertThat(regencyBack.getProvince()).isNull();
    }

    @Test
    void countryTest() throws Exception {
        Province province = getProvinceRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        province.setCountry(countryBack);
        assertThat(province.getCountry()).isEqualTo(countryBack);

        province.country(null);
        assertThat(province.getCountry()).isNull();
    }
}
