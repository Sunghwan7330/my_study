package com.example.kakaopay_test.vo;

import lombok.Data;

@Data
public class AmountVo {
    private Integer total, tax_free, vat, point, discount;
}
