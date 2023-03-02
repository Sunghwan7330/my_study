package com.example.oauth2_test.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Controller
@Log4j2
public class MainController {
    @GetMapping({"/", "/index"})
    public String getMain(@AuthenticationPrincipal OAuth2User oAuth2User) {
        log.info("로그인 된 유저:  " + oAuth2User);

        return "main";
    }
}
