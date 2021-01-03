package com.example.datingapp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDto {
    @NotNull(message = "The username cannot be null")
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotNull(message = "The password cannot be null" )
    @NotBlank(message = "Password is mandatory")
    private String password;

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
}
