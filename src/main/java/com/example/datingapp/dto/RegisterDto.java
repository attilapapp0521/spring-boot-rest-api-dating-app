package com.example.datingapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class RegisterDto {
    @NotBlank(message = "Username is mandatory")
    String username;
    @NotBlank(message = "KnownAs is mandatory")
    String knownAs;
    @NotBlank(message = "Gender is mandatory")
    String gender;
    @NotNull(message = "Date of birth is mandatory" )
    LocalDateTime dateOfBirth;
    @NotBlank(message = "City is mandatory")
    String city;
    @NotBlank(message = "Country is mandatory")
    String country;
    @NotBlank(message = "Password is mandatory")
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
