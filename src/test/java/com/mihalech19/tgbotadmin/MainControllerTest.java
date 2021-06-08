package com.mihalech19.tgbotadmin;

import com.mihalech19.tgbotadmin.Controllers.MainController;
import com.mihalech19.tgbotadmin.Entities.User;
import com.mihalech19.tgbotadmin.Entities.VoiceMsg;
import com.mihalech19.tgbotadmin.Interfaces.UserRepository;
import com.mihalech19.tgbotadmin.Interfaces.VoiceMsgRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.sql.Timestamp;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@WebMvcTest(MainController.class)
//@ContextConfiguration(classes = {VoiceMsgRepository.class, TestSecurityContextHolder.class})
@SpringBootTest
class MainControllerTest {

    MockMvc mockMvc;

    @Autowired
    VoiceMsgRepository voiceMsgRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .apply(springSecurity())
                .build();

        VoiceMsg voiceMsg = new VoiceMsg();
        voiceMsg.setFileUniqueId("TEST");
        voiceMsg.setFileId("TEST");
        voiceMsg.setSendtime(new Timestamp(System.currentTimeMillis()));
        voiceMsg.setUsername("TEST");
        voiceMsgRepository.save(voiceMsg);


    }


    @Test
    public void givenWac_whenServletContext_thenItProvidesMainController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean(MainController.class));
    }



    @Test
    @WithMockUser(username = "test", password = "test")
    void TestMainPanel() throws Exception {
    mockMvc.perform(get("/Main"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("Main"))
            .andExpect(model().attribute("voiceMsgPage", notNullValue()));

    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void deleteMessage() throws Exception {

        mockMvc.perform(get("/Main/Delete")
                .param("fileUniqueId", "TEST").param("returnPage", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Main/1"))
                .andExpect(flash().attributeExists("message"));
    }
}