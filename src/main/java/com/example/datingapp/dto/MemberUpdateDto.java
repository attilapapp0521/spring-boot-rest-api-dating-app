package com.example.datingapp.dto;

import javax.validation.constraints.NotBlank;

public class MemberUpdateDto {
    private String introduction;
    private String lookingFor;
    private String interests;
    @NotBlank(message = "The city field cannot be empty")
    private String city;
    @NotBlank(message = "The country field cannot be empty")
    private String country;

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
}
