package com.mobilepos.service.mapper;

import com.mobilepos.domain.Country;
import com.mobilepos.domain.Province;
import com.mobilepos.service.dto.CountryDTO;
import com.mobilepos.service.dto.ProvinceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Province} and its DTO {@link ProvinceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProvinceMapper extends EntityMapper<ProvinceDTO, Province> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryIsoCode")
    ProvinceDTO toDto(Province s);

    @Named("countryIsoCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "isoCode", source = "isoCode")
    CountryDTO toDtoCountryIsoCode(Country country);
}
