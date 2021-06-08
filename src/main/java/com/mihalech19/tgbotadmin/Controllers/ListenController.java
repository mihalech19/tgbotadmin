package com.mihalech19.tgbotadmin.Controllers;

import com.mihalech19.tgbotadmin.Interfaces.VoiceFileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Controller
public class ListenController {

    private final VoiceFileService tgVoiceFileService;

    @Autowired
    public ListenController(VoiceFileService tgVoiceFileService) {
        this.tgVoiceFileService = tgVoiceFileService;
    }

    @RequestMapping(value = "/Listen")
    public void ListenMessage(@RequestParam(name = "fileId", required = true) String fileId, HttpServletResponse response) {

        try {
            URL fileUri = new URL(tgVoiceFileService.getFilePath(fileId));

            InputStream is = fileUri.openStream();
            response.setContentType("audio/ogg");
            response.setHeader("Content-Disposition", "attachment; filename=audio.ogg");
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            System.err.println("Error writing file to output stream.");
            throw new RuntimeException("IOError writing file to output stream");
        }
    }
}
