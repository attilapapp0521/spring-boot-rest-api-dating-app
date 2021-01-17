package com.example.datingapp.dto;

import com.example.datingapp.domain.Photo;

public class PhotoDto {
    private Long id;
    private String url;
    private Boolean isMain;

    public PhotoDto(Photo photo) {
        this.id = photo.getId();
        this.url = photo.getUrl();
        this.isMain = photo.isMain();
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
}
