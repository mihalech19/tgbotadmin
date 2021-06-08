package com.mihalech19.tgbotadmin;

import com.mihalech19.tgbotadmin.Interfaces.VoiceFileService;
import com.mihalech19.tgbotadmin.Services.TgVoiceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.beans.BeanProperty;

@TestConfiguration
public class SpringTestConfig {

    public RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate(){
        return restTemplate;
    }

    public SpringTestConfig(){
        restTemplate = new RestTemplate();

    }
    @Bean
    public TgVoiceFileService voiceFileService(){return new TgVoiceFileService(restTemplate);}

}

