package com.demo.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DataControllerIntegrationTest extends AbstractIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String VALID_CSV = """
            primary_key,name,description,updated_timestamp
            4,forth,test,20230217T132036
            5,fifth,test,
            6,sixth,test,20220217T132036
            """;

    private static final String INVALID_CSV_DUPLICATE_KEY = """
            primary_key,name,description,updated_timestamp
            1,test,test,20240217T132036
            """;

    private static final String INVALID_CSV_NULL_KEY = """
            primary_key,name,description,updated_timestamp
            ,test,test,20240217T132036
            """;

    private static final String INVALID_CSV_BAD_TIMESTAMP_FORMAT = """
            primary_key,name,description,updated_timestamp
            7,seventh,test,2022-02-17 13:20:36
            """;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getDataByIdSuccess() throws Exception {
        mockMvc.perform(get("/data")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void getDataByIdFail() throws Exception {
        mockMvc.perform(get("/data")
                        .param("id", "-1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Data with id [-1] not found"));
    }

    @Test
    public void uploadCsvFileSuccess() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "data.csv",
                MediaType.TEXT_PLAIN_VALUE,
                VALID_CSV.getBytes()
        );

        mockMvc.perform(multipart("/data/upload")
                        .file(file))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void uploadCsvFileDuplicateKey() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "data.csv",
                MediaType.TEXT_PLAIN_VALUE,
                INVALID_CSV_DUPLICATE_KEY.getBytes()
        );

        mockMvc.perform(multipart("/data/upload")
                        .file(file))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message[?(@.property == 'id')].message")
                        .value("Data with id [1] already exists"));
    }

    @Test
    public void uploadCsvFileNullKey() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "data.csv",
                MediaType.TEXT_PLAIN_VALUE,
                INVALID_CSV_NULL_KEY.getBytes()
        );

        mockMvc.perform(multipart("/data/upload")
                        .file(file))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message[?(@.property == 'id')].message")
                        .value("must not be blank"));
    }

    @Test
    public void uploadCsvFileBadTimestampFormat() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "data.csv",
                MediaType.TEXT_PLAIN_VALUE,
                INVALID_CSV_BAD_TIMESTAMP_FORMAT.getBytes()
        );

        mockMvc.perform(multipart("/data/upload")
                        .file(file))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message[?(@.property == 'updatedTimestamp')].message")
                        .value("Wrong timestamp format. Expecting ISO8601."));
    }

    @Test
    public void deleteByIdSuccess() throws Exception {
        mockMvc.perform(delete("/data")
                        .param("id", "2"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteByIdFail() throws Exception {
        mockMvc.perform(delete("/data")
                        .param("id", "-1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Data with id [-1] not found"));
    }
}