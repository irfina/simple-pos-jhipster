package com.mobilepos.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MetricTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Metric getMetricSample1() {
        return new Metric().id(1).name("name1").notes("notes1");
    }

    public static Metric getMetricSample2() {
        return new Metric().id(2).name("name2").notes("notes2");
    }

    public static Metric getMetricRandomSampleGenerator() {
        return new Metric().id(intCount.incrementAndGet()).name(UUID.randomUUID().toString()).notes(UUID.randomUUID().toString());
    }
}
