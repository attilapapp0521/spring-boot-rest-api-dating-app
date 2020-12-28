package com.example.datingapp.dto;

import com.example.datingapp.domain.User;

public class UserDto {
    String username;

    public UserDto(User user) {
        this.username = user.getUsername();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
