package com.example.datingapp.dto;

import com.example.datingapp.domain.User;

public class UserDto {
    private String username;
    private String token;
    private String photoUrl;
    private String knownAs;
    private String gender;


    public UserDto(User user, String token) {
        this.username = user.getUsername();
        this.token = token;
        this.knownAs = user.getKnownAs();
        this.gender = user.getGender();

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getKnownAs() {
        return knownAs;
    }

    public void setKnownAs(String knownAs) {
        this.knownAs = knownAs;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
