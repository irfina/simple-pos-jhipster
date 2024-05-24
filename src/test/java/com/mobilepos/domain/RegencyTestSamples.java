package com.mobilepos.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RegencyTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Regency getRegencySample1() {
        return new Regency().id(1).name("name1");
    }

    public static Regency getRegencySample2() {
        return new Regency().id(2).name("name2");
    }

    public static Regency getRegencyRandomSampleGenerator() {
        return new Regency().id(intCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
