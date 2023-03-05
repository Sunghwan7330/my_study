package com.example.oauth2_test.controller;

import com.example.oauth2_test.dto.OAuthAttributes;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Iterator;
import java.util.Map;

@Controller
@Log4j2
public class MainController {
    @GetMapping({"/", "/index", "info"})
    public String getMain(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        log.info("로그인 된 유저:  " + oAuth2User);

        if (oAuth2User == null)
            return "index";

        OAuthAttributes attributes = OAuthAttributes.of("twitch", "", oAuth2User.getAttributes());
        model.addAttribute("user_info", attributes);

        return "info";
    }

    // 제대로 로그인이 되었는지 확인하는 요청
    @GetMapping("/check")
    public String getCheck() {
        return "Authorized!";
    }
}
