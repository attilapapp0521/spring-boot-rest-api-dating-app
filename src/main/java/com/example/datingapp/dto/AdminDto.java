package com.example.datingapp.dto;

import com.example.datingapp.domain.User;
import java.util.List;

public class AdminDto {
    private Long id;
    private String username;
    private List<String> roles;

    public AdminDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List getRoles() {
        return roles;
    }

    public void setRoles(List roles) {
        this.roles = roles;
    }
}
