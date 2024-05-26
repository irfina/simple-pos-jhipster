/*
 * ProvinceCreateDto.java
 *
 * Created on May 26, 2024, 21.45
 */
package com.mobilepos.service.dto;

import static java.util.Objects.requireNonNull;

/**
 * @author Irfin A.
 */
public record ProvinceCreateDto(String countryIsoCode, String name) {
    public ProvinceCreateDto {
        requireNonNull(countryIsoCode, "Country ISO code must not be null");
        requireNonNull(name, "Province name must not be null");
    }
}
