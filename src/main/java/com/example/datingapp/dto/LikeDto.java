package com.example.datingapp.dto;

import com.example.datingapp.domain.User;
import com.example.datingapp.domain.UserLike;

public class LikeDto {
    private long id;
    private String username;
    private int age;
    private String knownAs;
    private String photoUrl;
    private String city;

    public LikeDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.knownAs = user.getKnownAs();
        this.city = user.getCity();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getKnownAs() {
        return knownAs;
    }

    public void setKnownAs(String knownAs) {
        this.knownAs = knownAs;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
