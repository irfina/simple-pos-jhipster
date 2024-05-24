package com.mobilepos.domain;

import static com.mobilepos.domain.ProvinceTestSamples.*;
import static com.mobilepos.domain.RegencyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mobilepos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegencyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regency.class);
        Regency regency1 = getRegencySample1();
        Regency regency2 = new Regency();
        assertThat(regency1).isNotEqualTo(regency2);

        regency2.setId(regency1.getId());
        assertThat(regency1).isEqualTo(regency2);

        regency2 = getRegencySample2();
        assertThat(regency1).isNotEqualTo(regency2);
    }

    @Test
    void provinceTest() throws Exception {
        Regency regency = getRegencyRandomSampleGenerator();
        Province provinceBack = getProvinceRandomSampleGenerator();

        regency.setProvince(provinceBack);
        assertThat(regency.getProvince()).isEqualTo(provinceBack);

        regency.province(null);
        assertThat(regency.getProvince()).isNull();
    }
}
