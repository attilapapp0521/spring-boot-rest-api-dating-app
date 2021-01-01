package com.example.datingapp.domain;

import javax.persistence.*;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private Boolean isMain;
    private String publicId;
    @ManyToOne
    private User user;

    public Photo() {
    }

    public Photo(String url, String publicId, User user) {
        this.url = url;
        this.publicId = publicId;
        this.user = user;
        this.isMain = false;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getMain() {
        return isMain;
    }

    public void setMain(Boolean main) {
        isMain = main;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
