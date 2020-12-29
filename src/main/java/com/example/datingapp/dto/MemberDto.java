package com.example.datingapp.dto;

import com.example.datingapp.domain.User;

public class MemberDto {
    private String username;

    public MemberDto(User user) {
        this.username = user.getUsername();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
