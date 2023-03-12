package com.example.kakaopay_test.controller;

import com.example.kakaopay_test.service.KakaopayService;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log
@Controller
public class KakaopayController {
    @Setter(onMethod_ = @Autowired)
    private KakaopayService kakaopay;


    @GetMapping("/kakaopay")
    public void kakaopayGet() {

    }

    @PostMapping("/kakaopay")
    public String kakaopay() {
        log.info("kakaopay post............................................");

        return "redirect:" + kakaopay.kakaopayReady();

    }

    @GetMapping("/kakaopaySuccess")
    public void kakaopaySuccess(@RequestParam("pg_token") String pg_token, Model model) {
        log.info("kakaopaySuccess get............................................");
        log.info("kakaopaySuccess pg_token : " + pg_token);
        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token));
    }

}
