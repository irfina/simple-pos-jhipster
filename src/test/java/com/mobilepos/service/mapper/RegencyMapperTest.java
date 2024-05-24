package com.mobilepos.service.mapper;

import static com.mobilepos.domain.RegencyAsserts.*;
import static com.mobilepos.domain.RegencyTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegencyMapperTest {

    private RegencyMapper regencyMapper;

    @BeforeEach
    void setUp() {
        regencyMapper = new RegencyMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRegencySample1();
        var actual = regencyMapper.toEntity(regencyMapper.toDto(expected));
        assertRegencyAllPropertiesEquals(expected, actual);
    }
}
