package com.demo.service;

import com.demo.service.dto.DataDto;

import java.util.List;

public interface DataService {

    DataDto getById(String id);

    void save(DataDto dataDto);

    void saveAll(List<DataDto> list);

    void deleteById(String id);
}
