package com.mihalech19.tgbotadmin.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class VoiceFileServiceConfig {

    @Bean
    public RestTemplate getRestTemplate(){return new RestTemplate();}
}
