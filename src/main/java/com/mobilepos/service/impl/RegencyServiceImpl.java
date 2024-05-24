package com.mobilepos.service.impl;

import com.mobilepos.domain.Regency;
import com.mobilepos.repository.RegencyRepository;
import com.mobilepos.service.RegencyService;
import com.mobilepos.service.dto.RegencyDTO;
import com.mobilepos.service.mapper.RegencyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mobilepos.domain.Regency}.
 */
@Service
@Transactional
public class RegencyServiceImpl implements RegencyService {

    private final Logger log = LoggerFactory.getLogger(RegencyServiceImpl.class);

    private final RegencyRepository regencyRepository;

    private final RegencyMapper regencyMapper;

    public RegencyServiceImpl(RegencyRepository regencyRepository, RegencyMapper regencyMapper) {
        this.regencyRepository = regencyRepository;
        this.regencyMapper = regencyMapper;
    }

    @Override
    public RegencyDTO save(RegencyDTO regencyDTO) {
        log.debug("Request to save Regency : {}", regencyDTO);
        Regency regency = regencyMapper.toEntity(regencyDTO);
        regency = regencyRepository.save(regency);
        return regencyMapper.toDto(regency);
    }

    @Override
    public RegencyDTO update(RegencyDTO regencyDTO) {
        log.debug("Request to update Regency : {}", regencyDTO);
        Regency regency = regencyMapper.toEntity(regencyDTO);
        regency = regencyRepository.save(regency);
        return regencyMapper.toDto(regency);
    }

    @Override
    public Optional<RegencyDTO> partialUpdate(RegencyDTO regencyDTO) {
        log.debug("Request to partially update Regency : {}", regencyDTO);

        return regencyRepository
            .findById(regencyDTO.getId())
            .map(existingRegency -> {
                regencyMapper.partialUpdate(existingRegency, regencyDTO);

                return existingRegency;
            })
            .map(regencyRepository::save)
            .map(regencyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegencyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Regencies");
        return regencyRepository.findAll(pageable).map(regencyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegencyDTO> findOne(Integer id) {
        log.debug("Request to get Regency : {}", id);
        return regencyRepository.findById(id).map(regencyMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete Regency : {}", id);
        regencyRepository.deleteById(id);
    }
}
