package com.demo.service.impl;

import com.demo.repository.DataRepository;
import com.demo.service.DataService;
import com.demo.service.dto.DataDto;
import com.demo.service.exception.NotFoundException;
import com.demo.service.mapper.DataMapper;
import com.demo.service.validation.impl.DataValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DataServiceImpl implements DataService {

    private final DataRepository dataRepository;

    private final DataMapper dataMapper;

    private final DataValidationService dataValidationService;

    @Override
    public DataDto getById(String id) {
        return dataRepository.findById(id)
                .map(dataMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Data with id [%s] not found".formatted(id)));
    }

    @Override
    public void save(DataDto dataDto) {
        dataValidationService.validate(dataDto);
        dataRepository.save(dataMapper.toEntity(dataDto));
        log.info("Saved data with id [%s]".formatted(dataDto.getId()));
    }

    @Override
    public void saveAll(List<DataDto> list) {
        list.forEach(this::save);
    }

    @Override
    public void deleteById(String id) {
        if (!dataRepository.existsById(id)) {
            throw new NotFoundException("Data with id [%s] not found".formatted(id));
        }
        dataRepository.deleteById(id);
        log.info("Deleted data with id [%s]".formatted(id));
    }
}
