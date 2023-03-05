package com.example.oauth2_test.controller;

import com.example.oauth2_test.dto.OAuthAttributes;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@Log4j2
public class MainController {
    @GetMapping({"/", "/index"})
    public String getMain(@AuthenticationPrincipal OAuth2User oAuth2User, Model model, HttpServletRequest request) {
        log.info("로그인 된 유저:  " + oAuth2User);

        if (oAuth2User == null)
            return "index";

        HttpSession httpSession = request.getSession();
        model.addAttribute("user_info", httpSession.getAttribute("user"));

        return "info";
    }

    @GetMapping("/direct_logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "index";
    }

    // 제대로 로그인이 되었는지 확인하는 요청
    // 제대로 로그인이 되었는지 확인하는 요청
    @GetMapping("/check")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseBody
    public String getCheck() {
        return "Authorized!";
    }
}
