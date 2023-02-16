package com.demo.controller;

import com.demo.service.CsvService;
import com.demo.service.DataService;
import com.demo.service.dto.DataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    private final CsvService csvService;

    @GetMapping
    public DataDto getDataById(@RequestParam String id) {
        return dataService.getById(id);
    }

    @PostMapping(path = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadCsvFile(@RequestParam MultipartFile file) {
        dataService.saveAll(csvService.readFile(file));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestParam String id) {
        dataService.deleteById(id);
    }
}
