package com.mobilepos.service.impl;

import com.mobilepos.domain.Metric;
import com.mobilepos.repository.MetricRepository;
import com.mobilepos.service.MetricService;
import com.mobilepos.service.dto.MetricDTO;
import com.mobilepos.service.mapper.MetricMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mobilepos.domain.Metric}.
 */
@Service
@Transactional
public class MetricServiceImpl implements MetricService {

    private final Logger log = LoggerFactory.getLogger(MetricServiceImpl.class);

    private final MetricRepository metricRepository;

    private final MetricMapper metricMapper;

    public MetricServiceImpl(MetricRepository metricRepository, MetricMapper metricMapper) {
        this.metricRepository = metricRepository;
        this.metricMapper = metricMapper;
    }

    @Override
    public MetricDTO save(MetricDTO metricDTO) {
        log.debug("Request to save Metric : {}", metricDTO);
        Metric metric = metricMapper.toEntity(metricDTO);
        metric = metricRepository.save(metric);
        return metricMapper.toDto(metric);
    }

    @Override
    public MetricDTO update(MetricDTO metricDTO) {
        log.debug("Request to update Metric : {}", metricDTO);
        Metric metric = metricMapper.toEntity(metricDTO);
        metric = metricRepository.save(metric);
        return metricMapper.toDto(metric);
    }

    @Override
    public Optional<MetricDTO> partialUpdate(MetricDTO metricDTO) {
        log.debug("Request to partially update Metric : {}", metricDTO);

        return metricRepository
            .findById(metricDTO.getId())
            .map(existingMetric -> {
                metricMapper.partialUpdate(existingMetric, metricDTO);

                return existingMetric;
            })
            .map(metricRepository::save)
            .map(metricMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MetricDTO> findAll() {
        log.debug("Request to get all Metrics");
        return metricRepository.findAll().stream().map(metricMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MetricDTO> findOne(Integer id) {
        log.debug("Request to get Metric : {}", id);
        return metricRepository.findById(id).map(metricMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete Metric : {}", id);
        metricRepository.deleteById(id);
    }
}
