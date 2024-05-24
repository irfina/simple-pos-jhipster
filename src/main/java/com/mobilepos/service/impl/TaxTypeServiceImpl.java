package com.mobilepos.service.impl;

import com.mobilepos.domain.TaxType;
import com.mobilepos.repository.TaxTypeRepository;
import com.mobilepos.service.TaxTypeService;
import com.mobilepos.service.dto.TaxTypeDTO;
import com.mobilepos.service.mapper.TaxTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mobilepos.domain.TaxType}.
 */
@Service
@Transactional
public class TaxTypeServiceImpl implements TaxTypeService {

    private final Logger log = LoggerFactory.getLogger(TaxTypeServiceImpl.class);

    private final TaxTypeRepository taxTypeRepository;

    private final TaxTypeMapper taxTypeMapper;

    public TaxTypeServiceImpl(TaxTypeRepository taxTypeRepository, TaxTypeMapper taxTypeMapper) {
        this.taxTypeRepository = taxTypeRepository;
        this.taxTypeMapper = taxTypeMapper;
    }

    @Override
    public TaxTypeDTO save(TaxTypeDTO taxTypeDTO) {
        log.debug("Request to save TaxType : {}", taxTypeDTO);
        TaxType taxType = taxTypeMapper.toEntity(taxTypeDTO);
        taxType = taxTypeRepository.save(taxType);
        return taxTypeMapper.toDto(taxType);
    }

    @Override
    public TaxTypeDTO update(TaxTypeDTO taxTypeDTO) {
        log.debug("Request to update TaxType : {}", taxTypeDTO);
        TaxType taxType = taxTypeMapper.toEntity(taxTypeDTO);
        taxType = taxTypeRepository.save(taxType);
        return taxTypeMapper.toDto(taxType);
    }

    @Override
    public Optional<TaxTypeDTO> partialUpdate(TaxTypeDTO taxTypeDTO) {
        log.debug("Request to partially update TaxType : {}", taxTypeDTO);

        return taxTypeRepository
            .findById(taxTypeDTO.getId())
            .map(existingTaxType -> {
                taxTypeMapper.partialUpdate(existingTaxType, taxTypeDTO);

                return existingTaxType;
            })
            .map(taxTypeRepository::save)
            .map(taxTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaxTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaxTypes");
        return taxTypeRepository.findAll(pageable).map(taxTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaxTypeDTO> findOne(Integer id) {
        log.debug("Request to get TaxType : {}", id);
        return taxTypeRepository.findById(id).map(taxTypeMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete TaxType : {}", id);
        taxTypeRepository.deleteById(id);
    }
}
