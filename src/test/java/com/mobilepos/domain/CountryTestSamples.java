package com.mobilepos.domain;

import java.util.UUID;

public class CountryTestSamples {

    public static Country getCountrySample1() {
        return new Country().isoCode("isoCode1").name("name1");
    }

    public static Country getCountrySample2() {
        return new Country().isoCode("isoCode2").name("name2");
    }

    public static Country getCountryRandomSampleGenerator() {
        return new Country().isoCode(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
