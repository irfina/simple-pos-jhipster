package com.mobilepos.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mobilepos.domain.Regency} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegencyDTO implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    @Size(max = 256)
    private String name;

    @NotNull
    private ProvinceDTO province;

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

    public ProvinceDTO getProvince() {
        return province;
    }

    public void setProvince(ProvinceDTO province) {
        this.province = province;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegencyDTO)) {
            return false;
        }

        RegencyDTO regencyDTO = (RegencyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, regencyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegencyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", province=" + getProvince() +
            "}";
    }
}
