package com.example.datingapp.domain;

import com.example.datingapp.dto.RegisterDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    private LocalDateTime dateOfBirth;
    private String knownAs;
    private LocalDateTime created;
    private LocalDateTime lastActive;
    private String gender;
    private String introduction;
    private String lookingFor;
    private String interests;
    private String city;
    private String country;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "ROLES")
    private List<Roles> roles = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Photo> photos;
    @OneToMany(mappedBy = "sourceUser", cascade = CascadeType.ALL)
    private List<UserLike> sourceUser;
    @OneToMany(mappedBy = "likedUser", cascade = CascadeType.ALL)
    private List<UserLike> likedUser;
    @OneToMany(mappedBy = "sender")
    private List<Message> messageSent;
    @OneToMany(mappedBy = "recipient")
    private List<Message> messageReceived;

    public User() {
    }

    public User(RegisterDto registerDto, String encodedPassword) {
        this.username = registerDto.getUsername();
        this.dateOfBirth = registerDto.getDateOfBirth();
        this.knownAs = registerDto.getKnownAs();
        this.created = LocalDateTime.now();
        this.gender = registerDto.getGender();
        this.city = registerDto.getCity();
        this.country = registerDto.getCountry();
        this.password = encodedPassword;
        this.roles.add(Roles.ROLE_USER);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public void setInterests(String interest) {
        this.interests = interest;
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<UserLike> getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(List<UserLike> sourceUser) {
        this.sourceUser = sourceUser;
    }

    public List<UserLike> getLikedUser() {
        return likedUser;
    }

    public void setLikedUser(List<UserLike> likedUser) {
        this.likedUser = likedUser;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<Message> getMessageSent() {
        return messageSent;
    }

    public void setMessageSent(List<Message> messageSent) {
        this.messageSent = messageSent;
    }

    public List<Message> getMessageReceived() {
        return messageReceived;
    }

    public void setMessageReceived(List<Message> messageReceived) {
        this.messageReceived = messageReceived;
    }
}
