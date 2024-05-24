package com.mobilepos.service.mapper;

import com.mobilepos.domain.Metric;
import com.mobilepos.domain.Product;
import com.mobilepos.domain.ProductCategory;
import com.mobilepos.service.dto.MetricDTO;
import com.mobilepos.service.dto.ProductCategoryDTO;
import com.mobilepos.service.dto.ProductDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "defaultMetricInv", source = "defaultMetricInv", qualifiedByName = "metricId")
    @Mapping(target = "defaultMetricSales", source = "defaultMetricSales", qualifiedByName = "metricId")
    @Mapping(target = "defaultMetricPurchase", source = "defaultMetricPurchase", qualifiedByName = "metricId")
    @Mapping(target = "category", source = "category", qualifiedByName = "productCategoryId")
    ProductDTO toDto(Product s);

    @Named("metricId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MetricDTO toDtoMetricId(Metric metric);

    @Named("productCategoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductCategoryDTO toDtoProductCategoryId(ProductCategory productCategory);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
