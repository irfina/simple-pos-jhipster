package com.mobilepos.service.mapper;

import com.mobilepos.domain.Country;
import com.mobilepos.domain.Province;
import com.mobilepos.service.dto.CountryDTO;
import com.mobilepos.service.dto.ProvinceDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Province} and its DTO {@link ProvinceDto}.
 */
@Mapper(componentModel = "spring")
public interface ProvinceMapper extends EntityMapper<ProvinceDto, Province> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryIsoCode")
    ProvinceDto toDto(Province s);

    @Named("countryIsoCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "isoCode", source = "isoCode")
    CountryDTO toDtoCountryIsoCode(Country country);
}
