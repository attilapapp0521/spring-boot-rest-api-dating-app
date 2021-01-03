package com.example.datingapp.dto;

import com.example.datingapp.domain.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class MemberDto {
    private Long id;
    private String username;
    private String photoUrl;
    private Integer age;
    private String knownAs;
    private LocalDateTime created;
    private LocalDateTime lastActive;
    private String gender;
    private String introduction;
    private String lookingFor;
    private String interests;
    private String city;
    private String country;
    private List<PhotoDto> photos;


    public MemberDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.knownAs = user.getKnownAs();
        this.created = user.getCreated();
        this.lastActive = user.getLastActive();
        this.gender = user.getGender();
        this.introduction = user.getIntroduction();
        this.lookingFor = user.getLookingFor();
        this.interests = user.getInterests();
        this.city = user.getCity();
        this.country = user.getCountry();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getKnownAs() {
        return knownAs;
    }

    public void setKnownAs(String knownAs) {
        this.knownAs = knownAs;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(String lookingFor) {
        this.lookingFor = lookingFor;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<PhotoDto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDto> photos) {
        this.photos = photos;
    }

}
