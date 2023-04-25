package com.example.obs_alertbox_test.controller;

import com.example.obs_alertbox_test.TextToSpeech;
import com.example.obs_alertbox_test.dto.DonationInfoDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Log4j2
@Controller
public class AlertboxWebsocketController {

    private ResourceLoader resourceLoader;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/alertboxdata/send")
    public @ResponseBody void sendData (@RequestBody DonationInfoDto donationInfo, HttpServletRequest request){
        String filename = System.currentTimeMillis() + ".mp3";
        TextToSpeech tts = new TextToSpeech();
        try {
            tts.synthesizeText(donationInfo.getDonationMessage(), request.getSession().getServletContext().getRealPath("/") + filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info(request.getSession().getServletContext().getRealPath("/"));

        log.info(donationInfo.getToken());
        log.info(donationInfo.getImagePath());
        log.info(donationInfo.getDonationMessage());
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("image_path", donationInfo.getImagePath());
        payload.put("donation_message", donationInfo.getDonationMessage());
        payload.put("donation_sound", "https://assets.mytwip.net/sounds/Coins.mp3");  //TODO 임시로 twip 소리 사용
        payload.put("tts_sound", "http://localhost:8080/audio/" + filename);  //TODO 임시로 twip 소리 사용
        simpMessagingTemplate.convertAndSend("/donationinfo/" + donationInfo.getToken(), payload);
    }

    @GetMapping("/alertbox/{token}")
    public String alertbox(@PathVariable String token) {
        //TODO check token
        return "alertbox";
    }

    @GetMapping("/donation_test")
    public String donation_test() {
        return "donation_test";
    }

    @GetMapping("/audio/{filename:.+}")
    public ResponseEntity<Resource> serveAudio(@PathVariable String filename, HttpServletRequest request) throws IOException {
        /*
        log.info(filename);
        log.info(request.getSession().getServletContext().getRealPath("/") + filename);
        if(resourceLoader == )
        Resource resource = resourceLoader.getResource(request.getSession().getServletContext().getRealPath("/") + filename);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + filename);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        */
        //Resource resource = new FileSystemResource("src/main/resources/static/audio/" + filename);
        Resource resource = new FileSystemResource(request.getSession().getServletContext().getRealPath("/") + filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}
