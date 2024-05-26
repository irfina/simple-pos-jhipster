/*
 * ProvinceUpdateDto.java
 *
 * Created on May 26, 2024, 21.46
 */
package com.mobilepos.service.dto;

import static java.util.Objects.requireNonNull;

/**
 * @author Irfin A.
 */
public record ProvinceUpdateDto(String newCountryIsoCode, String newProvinceName) {
    public ProvinceUpdateDto {
        requireNonNull(newCountryIsoCode, "New country ISO code must not be null");
        requireNonNull(newProvinceName, "New province name must not be null");
    }
}
