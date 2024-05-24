package com.mobilepos.domain;

import static com.mobilepos.domain.TaxTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mobilepos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaxTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxType.class);
        TaxType taxType1 = getTaxTypeSample1();
        TaxType taxType2 = new TaxType();
        assertThat(taxType1).isNotEqualTo(taxType2);

        taxType2.setId(taxType1.getId());
        assertThat(taxType1).isEqualTo(taxType2);

        taxType2 = getTaxTypeSample2();
        assertThat(taxType1).isNotEqualTo(taxType2);
    }
}
