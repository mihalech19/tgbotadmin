package com.mihalech19.tgbotadmin.Controllers;

import com.mihalech19.tgbotadmin.Interfaces.VoiceFileService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListenControllerTest {

static ListenController controller;

MockHttpServletResponse response;
VoiceFileService voiceFileService;

    @BeforeEach
    public void setup() throws Exception {

        response = new MockHttpServletResponse();

        voiceFileService = Mockito.mock(VoiceFileService.class);

        controller = new ListenController(voiceFileService);


    }

    @Test
    void listenMessage() throws Exception {
        String path = getClass().getResource("ListenControllerTest.class").toString();
        when(voiceFileService.getFilePath(anyString())).thenReturn(path);
       controller.ListenMessage("test",response);
       assertEquals(response.getContentType(), "audio/ogg");

    }


    @Test
    void When_UnavailableResource_Then_WeShouldNotGetException() throws Exception{
        when(voiceFileService.getFilePath(anyString())).thenReturn("https://localhost/unaviableresource");
        assertDoesNotThrow(() -> controller.ListenMessage("test",response));

    }

}
