package com.mobilepos.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mobilepos.domain.Metric} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetricDTO implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @Size(max = 256)
    private String notes;

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
        if (!(o instanceof MetricDTO)) {
            return false;
        }

        MetricDTO metricDTO = (MetricDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metricDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetricDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
