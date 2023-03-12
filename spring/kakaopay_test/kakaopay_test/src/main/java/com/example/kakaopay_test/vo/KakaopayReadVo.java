package com.example.kakaopay_test.vo;

import lombok.Data;

import java.util.Date;

@Data
public class KakaopayReadVo {
    //response
    private String tid, next_redirect_pc_url;
    private Date created_at;
}
