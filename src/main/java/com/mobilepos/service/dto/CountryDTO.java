package com.mobilepos.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mobilepos.domain.Country} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountryDTO implements Serializable {

    @NotNull
    @Size(max = 5)
    private String isoCode;

    @NotNull
    private String name;

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountryDTO)) {
            return false;
        }

        CountryDTO countryDTO = (CountryDTO) o;
        if (this.isoCode == null) {
            return false;
        }
        return Objects.equals(this.isoCode, countryDTO.isoCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.isoCode);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryDTO{" +
            "isoCode='" + getIsoCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
