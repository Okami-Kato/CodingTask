package com.demo.service;

import com.demo.service.dto.DataDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CsvService {

    List<DataDto> readFile(MultipartFile file);
}
