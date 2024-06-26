package com.mobilepos.service.mapper;

import com.mobilepos.domain.Province;
import com.mobilepos.domain.Regency;
import com.mobilepos.service.dto.ProvinceDto;
import com.mobilepos.service.dto.RegencyDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Regency} and its DTO {@link RegencyDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegencyMapper extends EntityMapper<RegencyDTO, Regency> {
    @Mapping(target = "province", source = "province", qualifiedByName = "provinceId")
    RegencyDTO toDto(Regency s);

    @Named("provinceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProvinceDto toDtoProvinceId(Province province);
}
