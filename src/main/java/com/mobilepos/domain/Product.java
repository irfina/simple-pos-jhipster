package com.mobilepos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 128)
    @Column(name = "sku", length = 128)
    private String sku;

    @NotNull
    @Size(min = 3, max = 256)
    @Column(name = "name", length = 256, nullable = false)
    private String name;

    @Size(max = 128)
    @Column(name = "barcode", length = 128)
    private String barcode;

    @Column(name = "discount_in_percent")
    private Float discountInPercent;

    @Column(name = "min_disc_qty")
    private Integer minDiscQty;

    @NotNull
    @Column(name = "sell_price", nullable = false)
    private Double sellPrice;

    @NotNull
    @Column(name = "is_vat_applied", nullable = false)
    private Boolean isVatApplied;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @NotNull
    @Column(name = "is_stockable", nullable = false)
    private Boolean isStockable;

    @Size(max = 512)
    @Column(name = "notes", length = 512)
    private String notes;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "last_updated_at")
    private Instant lastUpdatedAt;

    @Column(name = "last_updated_by")
    private Integer lastUpdatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private Metric defaultMetricInv;

    @ManyToOne(fetch = FetchType.LAZY)
    private Metric defaultMetricSales;

    @ManyToOne(fetch = FetchType.LAZY)
    private Metric defaultMetricPurchase;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private ProductCategory category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Product id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSku() {
        return this.sku;
    }

    public Product sku(String sku) {
        this.setSku(sku);
        return this;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public Product barcode(String barcode) {
        this.setBarcode(barcode);
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Float getDiscountInPercent() {
        return this.discountInPercent;
    }

    public Product discountInPercent(Float discountInPercent) {
        this.setDiscountInPercent(discountInPercent);
        return this;
    }

    public void setDiscountInPercent(Float discountInPercent) {
        this.discountInPercent = discountInPercent;
    }

    public Integer getMinDiscQty() {
        return this.minDiscQty;
    }

    public Product minDiscQty(Integer minDiscQty) {
        this.setMinDiscQty(minDiscQty);
        return this;
    }

    public void setMinDiscQty(Integer minDiscQty) {
        this.minDiscQty = minDiscQty;
    }

    public Double getSellPrice() {
        return this.sellPrice;
    }

    public Product sellPrice(Double sellPrice) {
        this.setSellPrice(sellPrice);
        return this;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Boolean getIsVatApplied() {
        return this.isVatApplied;
    }

    public Product isVatApplied(Boolean isVatApplied) {
        this.setIsVatApplied(isVatApplied);
        return this;
    }

    public void setIsVatApplied(Boolean isVatApplied) {
        this.isVatApplied = isVatApplied;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Product isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsStockable() {
        return this.isStockable;
    }

    public Product isStockable(Boolean isStockable) {
        this.setIsStockable(isStockable);
        return this;
    }

    public void setIsStockable(Boolean isStockable) {
        this.isStockable = isStockable;
    }

    public String getNotes() {
        return this.notes;
    }

    public Product notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Product createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public Product createdBy(Integer createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastUpdatedAt() {
        return this.lastUpdatedAt;
    }

    public Product lastUpdatedAt(Instant lastUpdatedAt) {
        this.setLastUpdatedAt(lastUpdatedAt);
        return this;
    }

    public void setLastUpdatedAt(Instant lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Integer getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public Product lastUpdatedBy(Integer lastUpdatedBy) {
        this.setLastUpdatedBy(lastUpdatedBy);
        return this;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Metric getDefaultMetricInv() {
        return this.defaultMetricInv;
    }

    public void setDefaultMetricInv(Metric metric) {
        this.defaultMetricInv = metric;
    }

    public Product defaultMetricInv(Metric metric) {
        this.setDefaultMetricInv(metric);
        return this;
    }

    public Metric getDefaultMetricSales() {
        return this.defaultMetricSales;
    }

    public void setDefaultMetricSales(Metric metric) {
        this.defaultMetricSales = metric;
    }

    public Product defaultMetricSales(Metric metric) {
        this.setDefaultMetricSales(metric);
        return this;
    }

    public Metric getDefaultMetricPurchase() {
        return this.defaultMetricPurchase;
    }

    public void setDefaultMetricPurchase(Metric metric) {
        this.defaultMetricPurchase = metric;
    }

    public Product defaultMetricPurchase(Metric metric) {
        this.setDefaultMetricPurchase(metric);
        return this;
    }

    public ProductCategory getCategory() {
        return this.category;
    }

    public void setCategory(ProductCategory productCategory) {
        this.category = productCategory;
    }

    public Product category(ProductCategory productCategory) {
        this.setCategory(productCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return getId() != null && getId().equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
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
            "}";
    }
}
