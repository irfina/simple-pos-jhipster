package com.mobilepos.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mobilepos.domain.TaxType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaxTypeDTO implements Serializable {

    private Integer id;

    @NotNull
    @Size(max = 128)
    private String code;

    @NotNull
    @Size(max = 128)
    private String name;

    @NotNull
    private Double value;

    @Size(max = 256)
    private String notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxTypeDTO)) {
            return false;
        }

        TaxTypeDTO taxTypeDTO = (TaxTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taxTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxTypeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", value=" + getValue() +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
