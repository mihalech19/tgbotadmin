package com.mihalech19.tgbotadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mihalech19.tgbotadmin.Interfaces.VoiceFileService;
import com.mihalech19.tgbotadmin.Services.TgVoiceFileService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class TgVoiceFileServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private VoiceFileService voiceFileService;

    private MockRestServiceServer mockServer;

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);


    }



    @Test
    public void ExpectToGetFilePath() throws Exception {

        String fileId = "test";
        String requestString = "https://api.telegram.org/botTest/getFile?file_id=" + fileId;

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonResponse = objectMapper.createObjectNode();
        jsonResponse.put("ok", true);
        jsonResponse
                .set("result", objectMapper.createObjectNode()
                        .put("file_path", "files/file_test"));

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(requestString)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(jsonResponse.toString())
                );

        TgVoiceFileService tgVoiceFileService = (TgVoiceFileService) context.getBean(VoiceFileService.class);
        tgVoiceFileService.setBotToken("Test");
        String responseString = tgVoiceFileService.getFilePath("test");
        mockServer.verify();
        Assert.assertTrue(responseString.contains("files/file_test"));

    }







}
