package com.example.toss_pg_test.controllaer;

import com.example.toss_pg_test.dto.TossPaymentDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
public class TossPGController {
    @GetMapping("/toss_pg_test")
    public String payTestPage() {
        return "toss_pg_test";
    }

    @GetMapping("/toss_payment/success")
    public String tossPaytmentSuccess(HttpServletRequest request, Model model) {
        String orderId = request.getParameter("orderId");
        String paymentKey = request.getParameter("paymentKey");
        String amount = request.getParameter("amount");
        log.info("orderId : " + orderId + ", paymentKey : " + paymentKey + ", amount : " + amount);
        
        // 결제 승인 요청
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("dGVzdF9za196WExrS0V5cE5BcldtbzUwblgzbG1lYXhZRzVSOg==");

        String jsonBody = "{\"paymentKey\": \"" + paymentKey + "\",\n" +
                "    \"orderId\": \"" + orderId + "\",\n" +
                "    \"amount\": " + amount + "}";

        HttpEntity<String> patRequest = new HttpEntity<>(jsonBody, headers);

        String url = "https://api.tosspayments.com/v1/payments/confirm";
        String response = restTemplate.postForObject(url, patRequest, String.class);
        log.info(response);

        // 결과 dto로 매핑
        ObjectMapper mapper = new ObjectMapper();
        TossPaymentDto dto;
        try {
            dto = mapper.readValue(response, TossPaymentDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info(dto);

        model.addAttribute("tossPaymentDto", dto);

        return "success";
    }

    @GetMapping("/toss_payment/fail")
    public String tossPaytmentFail() {
        return "toss_pg_test";
    }

}
