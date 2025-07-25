package com.example.kakaopay_test.vo;

import lombok.Data;

import java.util.Date;

@Data
public class KakaopayApprovalVo {
    //response
    private String aid, tid, cid, sid;
    private String partner_order_id, partner_user_id, payment_method_type;
    private AmountVo amount;
    private CardVo card_info;
    private String item_name, item_code, payload;
    private Integer quantity, tax_free_amount, vat_amount;
    private Date created_at, approved_at;
}
