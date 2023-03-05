package com.example.oauth2_test.dto;

import com.example.oauth2_test.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SessionUser {
    private String platform;
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.platform = user.getPlatform();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
