package com.mobilepos.service.mapper;

import static com.mobilepos.domain.TaxTypeAsserts.*;
import static com.mobilepos.domain.TaxTypeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaxTypeMapperTest {

    private TaxTypeMapper taxTypeMapper;

    @BeforeEach
    void setUp() {
        taxTypeMapper = new TaxTypeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTaxTypeSample1();
        var actual = taxTypeMapper.toEntity(taxTypeMapper.toDto(expected));
        assertTaxTypeAllPropertiesEquals(expected, actual);
    }
}
