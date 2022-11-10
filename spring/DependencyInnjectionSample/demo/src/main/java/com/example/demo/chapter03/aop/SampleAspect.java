package com.example.demo.chapter03.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Aspect
@Component
public class SampleAspect {
    @Before("execution(* com.example.demo.chapter03.used.*Greet.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        // 시작 부분 표시
        System.out.println("==== Before Advice ====");
        // 날짜를 출력
        System.out.println(new SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date()));
        // 메서드 이름 출력
        System.out.println(String.format("메서드 : %s", joinPoint.getSignature().getName()));

    }
    @After("execution(* com.example.demo.chapter03.used.*Greet.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        // 시작 부분 표시
        System.out.println("==== After Advice ====");
        // 날짜를 출력
        System.out.println(new SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date()));
        // 메서드 이름 출력
        System.out.println(String.format("메서드명 : %s", joinPoint.getSignature().getName()));
    }

    @Around("execution(* com.example.demo.chapter03.used.*Greet.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        // 시작 부분 표시
        System.out.println("==== Around Advice ====");
        System.out.println("*** 처리 전 ***");
        // 지정한 클래스의 매서드 실행
        Object result = joinPoint.proceed();
        System.out.println("*** 처리 후 ***");
        // 반환값을 돌려줄 필요가 있는 경우에는 Object 타입의 반환값을 돌려줍니다.
        return result;
    }

}
