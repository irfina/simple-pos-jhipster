package com.mobilepos.domain;

import java.util.UUID;

public class ProductCategoryTestSamples {

    public static ProductCategory getProductCategorySample1() {
        return new ProductCategory()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .code("code1")
            .name("name1")
            .notes("notes1");
    }

    public static ProductCategory getProductCategorySample2() {
        return new ProductCategory()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .code("code2")
            .name("name2")
            .notes("notes2");
    }

    public static ProductCategory getProductCategoryRandomSampleGenerator() {
        return new ProductCategory()
            .id(UUID.randomUUID())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString());
    }
}
