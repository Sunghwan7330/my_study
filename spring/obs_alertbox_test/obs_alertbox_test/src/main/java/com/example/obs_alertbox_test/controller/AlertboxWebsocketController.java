package com.example.obs_alertbox_test.controller;

import com.example.obs_alertbox_test.component.DonationComponent;
import com.example.obs_alertbox_test.dto.DonationInfoDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log4j2
@Controller
public class AlertboxWebsocketController {

    @Autowired
    private DonationComponent donationComponent;

    @PostMapping("/alertboxdata/send")
    public @ResponseBody void sendData(@RequestBody DonationInfoDto donationInfo, HttpServletRequest request) {
        donationComponent.inputDonationInfo(donationInfo);
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
        Resource resource = new FileSystemResource(request.getSession().getServletContext().getRealPath("/") + filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}
