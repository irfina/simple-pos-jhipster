package com.mobilepos.service.mapper;

import com.mobilepos.domain.Metric;
import com.mobilepos.service.dto.MetricDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Metric} and its DTO {@link MetricDTO}.
 */
@Mapper(componentModel = "spring")
public interface MetricMapper extends EntityMapper<MetricDTO, Metric> {}
