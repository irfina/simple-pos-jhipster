package com.mobilepos.domain;

import java.util.UUID;

public class TenantTestSamples {

    public static Tenant getTenantSample1() {
        return new Tenant().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).name("name1");
    }

    public static Tenant getTenantSample2() {
        return new Tenant().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).name("name2");
    }

    public static Tenant getTenantRandomSampleGenerator() {
        return new Tenant().id(UUID.randomUUID()).name(UUID.randomUUID().toString());
    }
}
