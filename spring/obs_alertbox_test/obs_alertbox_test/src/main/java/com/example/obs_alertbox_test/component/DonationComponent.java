package com.example.obs_alertbox_test.component;

import com.example.obs_alertbox_test.TextToSpeech;
import com.example.obs_alertbox_test.dto.DonationInfoDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

@Log4j2
@Component
public class DonationComponent {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ServletContext servletContext;

    private HashMap<String, Queue<DonationInfoDto>> donationListHashmap;
    private boolean sendDonationFlag = false;

    public DonationComponent() {
        donationListHashmap = new HashMap<>();
    }

    public void inputDonationInfo(DonationInfoDto donationInfo) {
        Queue<DonationInfoDto> donationQueue;
        String token = donationInfo.getToken();

        if (donationListHashmap.containsKey(token)) {
            donationQueue = donationListHashmap.get(token);
        } else {
            donationQueue = new LinkedList<DonationInfoDto>();
            donationListHashmap.put(token, donationQueue);
        }

        donationQueue.offer(donationInfo);
    }

    public synchronized void processSendingDonation() {
        sendDonationFlag = true;
        for (String key : donationListHashmap.keySet()) {
            log.info("token : " + key);
            Queue<DonationInfoDto> queue = donationListHashmap.get(key);
            if (queue.isEmpty()) continue;

            log.info(queue.peek());
            sendDonation(queue.poll());
        }
        sendDonationFlag = false;
    }

    public boolean isSendingDonation() {
        return sendDonationFlag;
    }

    @Async
    public void sendDonation(DonationInfoDto donationInfo) {
        log.info("request : " + servletContext.getRealPath("/"));
        String filename = System.currentTimeMillis() + ".mp3";
        TextToSpeech tts = new TextToSpeech();
        try {
            tts.synthesizeText(donationInfo.getDonationMessage(), servletContext.getRealPath("/") + filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("image_path", donationInfo.getImagePath());
        payload.put("donation_message", donationInfo.getDonationMessage());
        payload.put("donation_sound", "https://assets.mytwip.net/sounds/Coins.mp3");  //TODO 임시로 twip 소리 사용
        payload.put("tts_sound", "http://localhost:8080/audio/" + filename);
        simpMessagingTemplate.convertAndSend("/donationinfo/" + donationInfo.getToken(), payload);
    }

    @Scheduled(fixedDelay = 1000)
    public void sendDonationSchedule() {
        if (isSendingDonation()) return;
        processSendingDonation();
    }
}
