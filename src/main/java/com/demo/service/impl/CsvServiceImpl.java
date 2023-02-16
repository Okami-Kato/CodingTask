package com.demo.service.impl;

import com.demo.service.CsvService;
import com.demo.service.dto.DataDto;
import com.demo.service.exception.ValidationException;
import com.demo.service.exception.ValidationException.Violation;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvServiceImpl implements CsvService {

    @Override
    @SneakyThrows
    public List<DataDto> readFile(MultipartFile file) {
        List<DataDto> dataList;
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<DataDto> csvToBean = new CsvToBeanBuilder<DataDto>(reader)
                    .withType(DataDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            dataList = csvToBean.parse();
        } catch (RuntimeException ex) {
            if (ex.getCause() instanceof CsvDataTypeMismatchException) {
                throw new ValidationException(
                        List.of(new Violation("updatedTimestamp", "Wrong timestamp format. Expecting ISO8601."))
                );
            } else {
                throw ex;
            }
        }
        return dataList;
    }
}
