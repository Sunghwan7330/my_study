package com.example.obs_alertbox_test.data_structure;

import com.example.obs_alertbox_test.dto.DonationInfoDto;

import java.util.LinkedList;
import java.util.Queue;

public class DonationInfoQueue {
    //TODO lock 추가 검토
    private long sendTime = 0;
    private long durationTime = 0;
    private Queue<DonationInfoDto> queue;


    public DonationInfoQueue() {
        queue = new LinkedList<>();
    }

    public void enqueue(DonationInfoDto donationInfo) {
        queue.offer(donationInfo);
    }

    public DonationInfoDto peek() {
        return queue.peek();
    }

    public DonationInfoDto dequeue() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public long getNextSendTime() {
        return sendTime + durationTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public long getDurationTime() {
        return durationTime;
    }
    public void setDurationTime(long durationTime) {
        this.durationTime = durationTime;
    }

    public void addDurationTime(long durationTime) {
        this.durationTime += durationTime;
    }
}
