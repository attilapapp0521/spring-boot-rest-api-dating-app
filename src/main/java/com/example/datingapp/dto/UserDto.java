package com.example.datingapp.dto;

import com.example.datingapp.domain.User;

public class UserDto {
    String username;
    String token;


    public UserDto(User user, String token) {
        this.username = user.getUsername();
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
