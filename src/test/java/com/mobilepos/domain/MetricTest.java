package com.mobilepos.domain;

import static com.mobilepos.domain.MetricTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mobilepos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetricTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Metric.class);
        Metric metric1 = getMetricSample1();
        Metric metric2 = new Metric();
        assertThat(metric1).isNotEqualTo(metric2);

        metric2.setId(metric1.getId());
        assertThat(metric1).isEqualTo(metric2);

        metric2 = getMetricSample2();
        assertThat(metric1).isNotEqualTo(metric2);
    }
}
