package com.mobilepos.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mobilepos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaxTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxTypeDTO.class);
        TaxTypeDTO taxTypeDTO1 = new TaxTypeDTO();
        taxTypeDTO1.setId(1);
        TaxTypeDTO taxTypeDTO2 = new TaxTypeDTO();
        assertThat(taxTypeDTO1).isNotEqualTo(taxTypeDTO2);
        taxTypeDTO2.setId(taxTypeDTO1.getId());
        assertThat(taxTypeDTO1).isEqualTo(taxTypeDTO2);
        taxTypeDTO2.setId(2);
        assertThat(taxTypeDTO1).isNotEqualTo(taxTypeDTO2);
        taxTypeDTO1.setId(null);
        assertThat(taxTypeDTO1).isNotEqualTo(taxTypeDTO2);
    }
}
