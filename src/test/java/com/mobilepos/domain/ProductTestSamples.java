package com.mobilepos.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .sku("sku1")
            .name("name1")
            .barcode("barcode1")
            .minDiscQty(1)
            .notes("notes1")
            .createdBy(1)
            .lastUpdatedBy(1);
    }

    public static Product getProductSample2() {
        return new Product()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .sku("sku2")
            .name("name2")
            .barcode("barcode2")
            .minDiscQty(2)
            .notes("notes2")
            .createdBy(2)
            .lastUpdatedBy(2);
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(UUID.randomUUID())
            .sku(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .barcode(UUID.randomUUID().toString())
            .minDiscQty(intCount.incrementAndGet())
            .notes(UUID.randomUUID().toString())
            .createdBy(intCount.incrementAndGet())
            .lastUpdatedBy(intCount.incrementAndGet());
    }
}
