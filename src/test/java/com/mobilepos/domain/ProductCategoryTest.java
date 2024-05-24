package com.mobilepos.domain;

import static com.mobilepos.domain.ProductCategoryTestSamples.*;
import static com.mobilepos.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mobilepos.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProductCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCategory.class);
        ProductCategory productCategory1 = getProductCategorySample1();
        ProductCategory productCategory2 = new ProductCategory();
        assertThat(productCategory1).isNotEqualTo(productCategory2);

        productCategory2.setId(productCategory1.getId());
        assertThat(productCategory1).isEqualTo(productCategory2);

        productCategory2 = getProductCategorySample2();
        assertThat(productCategory1).isNotEqualTo(productCategory2);
    }

    @Test
    void productTest() throws Exception {
        ProductCategory productCategory = getProductCategoryRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        productCategory.addProduct(productBack);
        assertThat(productCategory.getProducts()).containsOnly(productBack);
        assertThat(productBack.getCategory()).isEqualTo(productCategory);

        productCategory.removeProduct(productBack);
        assertThat(productCategory.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getCategory()).isNull();

        productCategory.products(new HashSet<>(Set.of(productBack)));
        assertThat(productCategory.getProducts()).containsOnly(productBack);
        assertThat(productBack.getCategory()).isEqualTo(productCategory);

        productCategory.setProducts(new HashSet<>());
        assertThat(productCategory.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getCategory()).isNull();
    }
}
