package com.mihalech19.tgbotadmin.Controllers;

import com.mihalech19.tgbotadmin.Controllers.MainController;
import com.mihalech19.tgbotadmin.Entities.VoiceMsg;
import com.mihalech19.tgbotadmin.Interfaces.VoiceMsgRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MainControllerTests {


    VoiceMsgRepository repository;


    Page<VoiceMsg> testpage;

    MainController controller;



    @BeforeEach
    void setup(){
        repository = Mockito.mock(VoiceMsgRepository.class);
        testpage = Mockito.mock(Page.class);
        when(testpage.getTotalPages()).thenReturn(10);
        when(testpage.getNumber()).thenReturn(5);
        when(repository.findAll(any(Pageable.class))).thenReturn(testpage);
        controller = new MainController(repository);

        }

    @Test
    void Test_Main_Panel(){
        Model model = new ConcurrentModel();

        model.asMap().put("message", "test message");

        String result = controller.MainPanel(model, 5);

        List<Integer> expectedPageNumbers = new ArrayList<>(Arrays.asList(5, 6, 7, 8, 9));

        List<Integer> testPageNumbers = (List<Integer>) model.getAttribute("pageNumbers");

        Page<VoiceMsg> actualPage = (Page<VoiceMsg>) model.getAttribute("voiceMsgPage");

        String actualMessage = (String) model.getAttribute("message");

        assertEquals("Main", result);
        assertEquals(expectedPageNumbers, testPageNumbers);
        assertEquals(5, actualPage.getNumber());
        assertEquals("test message", actualMessage);


    }


    @Test
    void RedirectToLastPage_when_pagenumber_above_then_LastPage(){
        Model model = new ConcurrentModel();
        String result = controller.MainPanel(model, 11);
        assertEquals("redirect:/Main/10", result);

    }

    @Test
    void DeleteMessageTest(){
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        RedirectView resultWithRedirectPage = controller.DeleteMessage("test", "3", redirectAttributes);
        RedirectView resultWithoutRedirectPage = controller.DeleteMessage("test", null , redirectAttributes);
        String expectedMessage = "Deletion successfully completed";
        String message = (String) redirectAttributes.getFlashAttributes().get("message");

        assertEquals("/Main/3", resultWithRedirectPage.getUrl());
        assertEquals("/Main", resultWithoutRedirectPage.getUrl());
        assertEquals(expectedMessage, message);


    }


}
