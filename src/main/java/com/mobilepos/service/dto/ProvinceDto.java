package com.mobilepos.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mobilepos.domain.Province} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProvinceDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    @Size(max = 256)
    private String name;

    @NotNull
    private CountryDTO country;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProvinceDto)) {
            return false;
        }

        ProvinceDto provinceDTO = (ProvinceDto) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, provinceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProvinceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", country=" + getCountry() +
            "}";
    }
}
