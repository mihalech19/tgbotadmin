package com.mihalech19.tgbotadmin.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihalech19.tgbotadmin.Interfaces.VoiceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@ConfigurationProperties(prefix = "tgbotadmin")
@Service
public class TgVoiceFileService implements VoiceFileService {

private String botToken;

private RestTemplate restTemplate;

@Autowired
public TgVoiceFileService(RestTemplate restTemplate){
    this.restTemplate = restTemplate;
}


    @Override
    public String getFilePath(String fileId) {

        String getFileURI = "https://api.telegram.org/bot" + botToken + "/getFile?file_id=" + fileId;
        String jsonResponse = restTemplate.getForObject(getFileURI, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(jsonResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        StringBuffer fullFilePath = new StringBuffer("https://api.telegram.org/file/bot" + botToken + "/");

        if (jsonNode.get("ok").asBoolean()) {
            fullFilePath.append(jsonNode.path("result").get("file_path").asText());
            return fullFilePath.toString();
        }
        return "";

    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

}
