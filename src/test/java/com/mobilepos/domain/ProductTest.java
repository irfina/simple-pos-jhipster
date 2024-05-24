package com.mobilepos.domain;

import static com.mobilepos.domain.MetricTestSamples.*;
import static com.mobilepos.domain.ProductCategoryTestSamples.*;
import static com.mobilepos.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mobilepos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void defaultMetricInvTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Metric metricBack = getMetricRandomSampleGenerator();

        product.setDefaultMetricInv(metricBack);
        assertThat(product.getDefaultMetricInv()).isEqualTo(metricBack);

        product.defaultMetricInv(null);
        assertThat(product.getDefaultMetricInv()).isNull();
    }

    @Test
    void defaultMetricSalesTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Metric metricBack = getMetricRandomSampleGenerator();

        product.setDefaultMetricSales(metricBack);
        assertThat(product.getDefaultMetricSales()).isEqualTo(metricBack);

        product.defaultMetricSales(null);
        assertThat(product.getDefaultMetricSales()).isNull();
    }

    @Test
    void defaultMetricPurchaseTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Metric metricBack = getMetricRandomSampleGenerator();

        product.setDefaultMetricPurchase(metricBack);
        assertThat(product.getDefaultMetricPurchase()).isEqualTo(metricBack);

        product.defaultMetricPurchase(null);
        assertThat(product.getDefaultMetricPurchase()).isNull();
    }

    @Test
    void categoryTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        ProductCategory productCategoryBack = getProductCategoryRandomSampleGenerator();

        product.setCategory(productCategoryBack);
        assertThat(product.getCategory()).isEqualTo(productCategoryBack);

        product.category(null);
        assertThat(product.getCategory()).isNull();
    }
}
