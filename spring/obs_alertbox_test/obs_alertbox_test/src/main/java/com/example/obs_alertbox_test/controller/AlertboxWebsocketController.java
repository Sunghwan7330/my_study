package com.example.obs_alertbox_test.controller;

import com.example.obs_alertbox_test.dto.DonationInfoDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Log4j2
@Controller
public class AlertboxWebsocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/alertbox/senddata")
    public @ResponseBody void sendData (@RequestBody DonationInfoDto donationInfo){
        log.info(donationInfo.getToken());
        log.info(donationInfo.getImagePath());
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("image_path", donationInfo.getImagePath());
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
}
