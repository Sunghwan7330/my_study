package com.example.demo.chapter03.used;

import org.springframework.stereotype.Component;

/**
 * Greet 구현 클래스
 * 저녁 인사 하기
 */
@Component
public class EveningGreet implements Greet{
    @Override
    public void greeting() {
        System.out.println("------------------");
        System.out.println("좋은 저녁입니다.");
        System.out.println("------------------");
    }
}
