package com.demo.service.mapper;

import com.demo.domain.Data;
import com.demo.service.dto.DataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataMapper {

    DataDto toDto(Data entity);

    Data toEntity(DataDto dto);
}
