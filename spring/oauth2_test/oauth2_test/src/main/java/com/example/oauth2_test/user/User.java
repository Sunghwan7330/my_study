package com.example.oauth2_test.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String platform;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;
    private String picture;
    private String role = "ROLE_USER";

    public User(String platform, String name, String email, String picture) {
        this.platform = platform;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public User update(String platform, String name, String picture) {
        this.platform = platform;
        this.name = name;
        this.picture = picture;

        return this;
    }
}