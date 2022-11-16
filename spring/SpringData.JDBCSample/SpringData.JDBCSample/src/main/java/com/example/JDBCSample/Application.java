package com.example.JDBCSample;

import com.example.JDBCSample.demo.entity.Member;
import com.example.JDBCSample.demo.repository.MemberCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 스프링 부트 기동 클래스
 */
@SpringBootApplication(scanBasePackages = {"com.example.demo.entity", "com.example.demo.repository"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args).getBean(Application.class).execute();
	}

	@Autowired
	MemberCrudRepository repository;

	/**
	 * 등록과 전체 취득 처리
	 */
	private void execute() {
		// 등록
		executeInsert();
		// 전체 취득
		executeSelect();
	}

	/**
	 * 등록
	 */
	private void executeInsert() {
		// 엔티티 생성 (id는 자동으로 부여되기 때문에 null을 설정)
		Member member = new Member(null, "이순신");

		// 리포지톹리를 이용해 등록을 수행하고 결과를 취득
		member = repository.save(member);

		// 결과를 표시
		System.out.println("등록 데이터 : " + member);
	}

	/**
	 * 전체 취득
	 */
	private void executeSelect() {
		System.out.println("--- 전체 데이터를 취득합니다. ---");

		// 리포지토리를 이용해 전체 데이터를 취득
		Iterable<Member> members = repository.findAll();
		for (Member member : members) {
			System.out.println(member);
		}
	}

}
