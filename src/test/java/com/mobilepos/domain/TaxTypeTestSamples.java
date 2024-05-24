package com.mobilepos.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TaxTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TaxType getTaxTypeSample1() {
        return new TaxType().id(1).code("code1").name("name1").notes("notes1");
    }

    public static TaxType getTaxTypeSample2() {
        return new TaxType().id(2).code("code2").name("name2").notes("notes2");
    }

    public static TaxType getTaxTypeRandomSampleGenerator() {
        return new TaxType()
            .id(intCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString());
    }
}
