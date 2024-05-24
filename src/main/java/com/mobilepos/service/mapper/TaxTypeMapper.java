package com.mobilepos.service.mapper;

import com.mobilepos.domain.TaxType;
import com.mobilepos.service.dto.TaxTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaxType} and its DTO {@link TaxTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaxTypeMapper extends EntityMapper<TaxTypeDTO, TaxType> {}
