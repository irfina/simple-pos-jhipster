package com.mobilepos.service.impl;

import com.mobilepos.domain.Province;
import com.mobilepos.repository.ProvinceRepository;
import com.mobilepos.service.CountryService;
import com.mobilepos.service.ProvinceService;
import com.mobilepos.service.dto.ProvinceCreateDto;
import com.mobilepos.service.dto.ProvinceDto;
import com.mobilepos.service.dto.ProvinceUpdateDto;
import com.mobilepos.service.mapper.ProvinceMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mobilepos.domain.Province}.
 */
@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {

    private final Logger log = LoggerFactory.getLogger(ProvinceServiceImpl.class);

    private final CountryService countryService;
    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;

    public ProvinceServiceImpl(CountryService _countryService, ProvinceRepository _provinceRepository, ProvinceMapper _provinceMapper) {
        countryService = _countryService;
        provinceRepository = _provinceRepository;
        provinceMapper = _provinceMapper;
    }

    @Override
    public ProvinceDto save(ProvinceCreateDto _createDto) {
        log.debug("Request to save Province : {}", _createDto);
        //Province province = provinceMapper.toEntity(_createDto);

        var country = countryService
            .findByIsoCode(_createDto.countryIsoCode())
            .orElseThrow(() -> new EntityNotFoundException("Country with ISO code " + _createDto.countryIsoCode() + " not found"));

        var province = new Province();
        province.setName(_createDto.name());
        province.setCountry(country);
        province = provinceRepository.save(province);

        return provinceMapper.toDto(province);
    }

    @Override
    public ProvinceDto update(ProvinceUpdateDto _updateDto) {
        log.debug("Request to update Province : {}", _updateDto);
        //Province province = provinceMapper.toEntity(_updateDto);

        var country = countryService
            .findByIsoCode(_updateDto.newCountryIsoCode())
            .orElseThrow(() -> new EntityNotFoundException("Country with ISO code " + _updateDto.newCountryIsoCode() + " not found"));

        var province = new Province();
        province.setName(_updateDto.newProvinceName());
        province.setCountry(country);
        province = provinceRepository.save(province);

        return provinceMapper.toDto(province);
    }

    @Override
    public Optional<ProvinceDto> partialUpdate(ProvinceDto provinceDTO) {
        log.debug("Request to partially update Province : {}", provinceDTO);

        return provinceRepository
            .findById(provinceDTO.getId())
            .map(existingProvince -> {
                provinceMapper.partialUpdate(existingProvince, provinceDTO);

                return existingProvince;
            })
            .map(provinceRepository::save)
            .map(provinceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProvinceDto> findAll(Pageable pageable) {
        log.debug("Request to get all Provinces");
        return provinceRepository.findAll(pageable).map(provinceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProvinceDto> findOne(Integer id) {
        log.debug("Request to get Province : {}", id);
        return provinceRepository.findById(id).map(provinceMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete Province : {}", id);
        provinceRepository.deleteById(id);
    }
}
