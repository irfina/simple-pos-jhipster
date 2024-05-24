package com.mobilepos.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.mobilepos.domain.Product} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductDTO implements Serializable {

    @NotNull
    private UUID id;

    @Size(max = 128)
    private String sku;

    @NotNull
    @Size(min = 3, max = 256)
    private String name;

    @Size(max = 128)
    private String barcode;

    private Float discountInPercent;

    private Integer minDiscQty;

    @NotNull
    private Double sellPrice;

    @NotNull
    private Boolean isVatApplied;

    @NotNull
    private Boolean isActive;

    @NotNull
    private Boolean isStockable;

    @Size(max = 512)
    private String notes;

    private Instant createdAt;

    private Integer createdBy;

    private Instant lastUpdatedAt;

    private Integer lastUpdatedBy;

    private MetricDTO defaultMetricInv;

    private MetricDTO defaultMetricSales;

    private MetricDTO defaultMetricPurchase;

    @NotNull
    private ProductCategoryDTO category;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Float getDiscountInPercent() {
        return discountInPercent;
    }

    public void setDiscountInPercent(Float discountInPercent) {
        this.discountInPercent = discountInPercent;
    }

    public Integer getMinDiscQty() {
        return minDiscQty;
    }

    public void setMinDiscQty(Integer minDiscQty) {
        this.minDiscQty = minDiscQty;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Boolean getIsVatApplied() {
        return isVatApplied;
    }

    public void setIsVatApplied(Boolean isVatApplied) {
        this.isVatApplied = isVatApplied;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsStockable() {
        return isStockable;
    }

    public void setIsStockable(Boolean isStockable) {
        this.isStockable = isStockable;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Instant lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public MetricDTO getDefaultMetricInv() {
        return defaultMetricInv;
    }

    public void setDefaultMetricInv(MetricDTO defaultMetricInv) {
        this.defaultMetricInv = defaultMetricInv;
    }

    public MetricDTO getDefaultMetricSales() {
        return defaultMetricSales;
    }

    public void setDefaultMetricSales(MetricDTO defaultMetricSales) {
        this.defaultMetricSales = defaultMetricSales;
    }

    public MetricDTO getDefaultMetricPurchase() {
        return defaultMetricPurchase;
    }

    public void setDefaultMetricPurchase(MetricDTO defaultMetricPurchase) {
        this.defaultMetricPurchase = defaultMetricPurchase;
    }

    public ProductCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(ProductCategoryDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
            "id='" + getId() + "'" +
            ", sku='" + getSku() + "'" +
            ", name='" + getName() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", discountInPercent=" + getDiscountInPercent() +
            ", minDiscQty=" + getMinDiscQty() +
            ", sellPrice=" + getSellPrice() +
            ", isVatApplied='" + getIsVatApplied() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isStockable='" + getIsStockable() + "'" +
            ", notes='" + getNotes() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", lastUpdatedAt='" + getLastUpdatedAt() + "'" +
            ", lastUpdatedBy=" + getLastUpdatedBy() +
            ", defaultMetricInv=" + getDefaultMetricInv() +
            ", defaultMetricSales=" + getDefaultMetricSales() +
            ", defaultMetricPurchase=" + getDefaultMetricPurchase() +
            ", category=" + getCategory() +
            "}";
    }
}
