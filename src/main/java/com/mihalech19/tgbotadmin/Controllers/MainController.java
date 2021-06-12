package com.mihalech19.tgbotadmin.Controllers;

import com.mihalech19.tgbotadmin.Entities.VoiceMsg;
import com.mihalech19.tgbotadmin.Interfaces.VoiceMsgRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private final VoiceMsgRepository voiceMsgRepository;

    @Autowired
    public MainController(VoiceMsgRepository voiceMsgRepository) {
        this.voiceMsgRepository = voiceMsgRepository;
    }

    @GetMapping(value = {"", "/", "/Main", "/Main/{page}"})
    public String MainPanel(Model model, @PathVariable(required = false) Integer page) {

        Pageable pageable = PageRequest
                .of((page != null && page > 0) ? page - 1 : 0, 10, Sort.by("sendtime").descending());

        Page<VoiceMsg> voiceMsgPage = voiceMsgRepository.findAll(pageable);

        if(voiceMsgPage.getTotalPages() < ((page != null) ? page : 1))
            return "redirect:/Main/" + voiceMsgPage.getTotalPages();

        List<Integer> pageNumbers = GetPageNumbers(voiceMsgPage);

        if(pageNumbers != null)
        model.addAttribute("pageNumbers", pageNumbers);

        model.addAttribute("voiceMsgPage", voiceMsgPage);

        model.addAttribute("message", model.asMap().get("message"));

        return "Main";

    }

    @RequestMapping(value = "/Main/Delete")
    public RedirectView DeleteMessage(
            @RequestParam(name = "fileUniqueId") String fileUniqueId,
            @RequestParam(name = "returnPage" , required = false) String returnPage,
            RedirectAttributes redirectAttrs) {
        String redirectString = (returnPage != null) ? "/Main/" + returnPage : "/Main";
        RedirectView redirectView = new RedirectView(redirectString);
        voiceMsgRepository.deleteById(fileUniqueId);
        redirectAttrs.addFlashAttribute("message", "Deletion successfully completed");
        log.info("Message with id " + fileUniqueId + " was deleted");
        return redirectView;

    }

    private List<Integer> GetPageNumbers(Page<VoiceMsg> voiceMsgPage){
        int totalPages = voiceMsgPage.getTotalPages();
        int pageNumber = (voiceMsgPage.getNumber() > 0 ) ? voiceMsgPage.getNumber() : 1;
        if (totalPages > 0) {
            return IntStream
                    .rangeClosed(pageNumber, Math.min(pageNumber + 4, totalPages))
                    .boxed()
                    .collect(Collectors.toList());
        }
        return null;
    }

}


