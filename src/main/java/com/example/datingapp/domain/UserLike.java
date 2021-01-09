package com.example.datingapp.domain;

import javax.persistence.*;

@Entity
public class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User sourceUser;
    @ManyToOne
    private User likedUser;

    public UserLike() {
    }

    public UserLike(User sourceUser, User likedUser) {
        this.sourceUser = sourceUser;
        this.likedUser = likedUser;
    }

    public User getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(User sourceUser) {
        this.sourceUser = sourceUser;
    }

    public User getLikedUser() {
        return likedUser;
    }

    public void setLikedUser(User likedUser) {
        this.likedUser = likedUser;
    }
}
