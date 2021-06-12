package com.mihalech19.tgbotadmin.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mihalech19.tgbotadmin.Services.TgVoiceFileService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TgVoiceFileServiceTest {

    private RestTemplate restTemplate;

    String file_path = "https://api.telegram.org/file/botTest/files/file_test";


    @BeforeEach
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonResponse = objectMapper.createObjectNode();
        jsonResponse.put("ok", true);
        jsonResponse
                .set("result", objectMapper.createObjectNode()
                        .put("file_path", "files/file_test"));

        restTemplate = Mockito.mock(RestTemplate.class);

        when(restTemplate.getForObject(
                contains("fileid_good"), any()))
                .thenReturn(jsonResponse.toString());

        when(restTemplate.getForObject(
                contains("fileid_fail"), any()))
                .thenReturn("{\"ok\":false}");


    }

    @Test
    public void Test_getFilePath_method(){

        TgVoiceFileService fileService = new TgVoiceFileService(restTemplate);
        fileService.setBotToken("Test");

       assertEquals(file_path, fileService.getFilePath("fileid_good")) ;
       assertEquals("", fileService.getFilePath("fileid_fail"));

    }

}
