package com.example.ipgeofinder.controller;

import com.example.ipgeofinder.service.FileProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IpDataControllerTest {

    private IpDataController controller;

    private FileProcessor processor;

    private MockMvc mockMvc;

    @BeforeEach
    public void SetUp() {
        processor = mock(FileProcessor.class);
        controller = new IpDataController(processor);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void uploadCsvSuccess() throws Exception {
        byte[] fileContent = "value1,value2\nvalue3,value4".getBytes();
        MockMultipartFile file = new MockMultipartFile("csvFile", "test.csv",
                MediaType.TEXT_PLAIN_VALUE, fileContent);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/data/upload-csv")
                        .file(file))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertEquals("Archivo CSV procesado y guardado correctamente", response);

        verify(processor, times(1)).process(any());

    }

    @Test
    public void uploadCsvFail() throws Exception {
        byte[] fileContent = "value1,value2\nvalue3,value4".getBytes();
        MockMultipartFile file = new MockMultipartFile("csvFile", "test.csv",
                MediaType.TEXT_PLAIN_VALUE, fileContent);

        doThrow(new RuntimeException("invalid CSV")).when(processor).process(any());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/data/upload-csv")
                        .file(file))
                .andExpect(status().is5xxServerError())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        assertEquals("Ocurrió un error al procesar el archivo CSV: invalid CSV", response);

        verify(processor, times(1)).process(any());

    }
}
