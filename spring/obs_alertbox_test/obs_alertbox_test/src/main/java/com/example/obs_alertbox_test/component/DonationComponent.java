package com.example.obs_alertbox_test.component;

import com.example.obs_alertbox_test.TextToSpeech;
import com.example.obs_alertbox_test.data_structure.DonationInfoQueue;
import com.example.obs_alertbox_test.dto.DonationInfoDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Log4j2
@Component
public class DonationComponent {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ServletContext servletContext;

    private HashMap<String, DonationInfoQueue> donationListHashmap;
    private boolean sendDonationFlag = false;

    private static final long DEFAULT_DURATION_TIME = 3000;
    private static final long DURATION_GAP_TIME = 1000;
    private static final long MAX_DURATION_TIME = 10000;

    public DonationComponent() {
        donationListHashmap = new HashMap<>();
    }

    public void inputDonationInfo(DonationInfoDto donationInfo) {
        DonationInfoQueue donationQueue;
        String token = donationInfo.getToken();

        if (donationListHashmap.containsKey(token)) {
            donationQueue = donationListHashmap.get(token);
        } else {
            donationQueue = new DonationInfoQueue();
            donationListHashmap.put(token, donationQueue);
        }

        donationQueue.enqueue(donationInfo);
    }

    public synchronized void processSendingDonation() {
        sendDonationFlag = true;
        for (String key : donationListHashmap.keySet()) {
            DonationInfoQueue queue = donationListHashmap.get(key);
            if (queue.isEmpty()) continue;

            if (queue.getNextSendTime() < System.currentTimeMillis()) {
                queue.setSendTime(System.currentTimeMillis());
                queue.setDurationTime(DEFAULT_DURATION_TIME);
                sendDonation(queue);
            }
        }
        sendDonationFlag = false;
    }

    public boolean isSendingDonation() {
        return sendDonationFlag;
    }

    private long getDurationWithWav(String filePath) throws UnsupportedAudioFileException, IOException {
        File wavFile = new File(filePath);
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavFile);
            AudioFormat format = audioInputStream.getFormat();
            long frames = audioInputStream.getFrameLength();
            double durationInSeconds = (frames + 0.0) / format.getFrameRate();
            return (long) (durationInSeconds * 1000);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Async
    public void sendDonation(DonationInfoQueue donationInfoQueue) {
        DonationInfoDto donationInfo = donationInfoQueue.dequeue();
        log.info("request : " + servletContext.getRealPath("/"));
        String filename = System.currentTimeMillis() + ".wav";
        String fullFilePath = servletContext.getRealPath("/") + filename;
        TextToSpeech tts = new TextToSpeech();
        long durationTime = 0;

        try {
            tts.synthesizeText(donationInfo.getDonationMessage(), fullFilePath);
            durationTime = Math.min(getDurationWithWav(fullFilePath), MAX_DURATION_TIME);
        } catch (IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }

        donationInfoQueue.addDurationTime(durationTime);
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("image_path", donationInfo.getImagePath());
        payload.put("donation_message", donationInfo.getDonationMessage());
        payload.put("donation_sound", "https://assets.mytwip.net/sounds/Coins.mp3");  //TODO 임시로 twip 소리 사용
        payload.put("tts_sound", "http://localhost:8080/audio/" + filename);
        payload.put("duration_time", donationInfoQueue.getDurationTime());
        simpMessagingTemplate.convertAndSend("/donationinfo/" + donationInfo.getToken(), payload);

    }

    @Scheduled(fixedDelay = 100)
    public void sendDonationSchedule() {
        if (isSendingDonation()) return;
        processSendingDonation();
    }
}
