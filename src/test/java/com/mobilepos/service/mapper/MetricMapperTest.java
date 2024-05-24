package com.mobilepos.service.mapper;

import static com.mobilepos.domain.MetricAsserts.*;
import static com.mobilepos.domain.MetricTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetricMapperTest {

    private MetricMapper metricMapper;

    @BeforeEach
    void setUp() {
        metricMapper = new MetricMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMetricSample1();
        var actual = metricMapper.toEntity(metricMapper.toDto(expected));
        assertMetricAllPropertiesEquals(expected, actual);
    }
}
