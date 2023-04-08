package com.example.toss_pg_test.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TossPaymentDto {
    private String mId;
    private String version;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String currency;
    private String method;
    private String status;
    private String requestedAt;
    private String approvedAt;
    private boolean useEscrow;
    private boolean cultureExpense;
    private Card card;
    private String virtualAccount;
    private String transfer;
    private String mobilePhone;
    private String giftCertificate;
    private String foreignEasyPay;
    private String cashReceipt;
    private String discount;
    private String cancels;
    private String secret;
    private String type;
    private String easyPay;
    private String country;
    private String failure;
    private int totalAmount;
    private int balanceAmount;
    private int suppliedAmount;
    private int vat;
    private int taxFreeAmount;
    private int taxExemptionAmount;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Card {
        private int issuerCode;
        private int acquirerCode;
        private String number;
        private int installmentPlanMonths;
        private boolean isInterestFree;
        private String interestPayer;
        private String approveNo;
        private boolean useCardPoint;
        private String cardType;
        private String ownerType;
        private String acquireStatus;
    }
}
