package com.demo.service.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DataDto {

    @NotBlank
    @CsvBindByName(column = "primary_key")
    private String id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String description;

    @CsvDate
    @CsvBindByName(column = "updated_timestamp")
    private LocalDateTime updatedTimestamp;
}
