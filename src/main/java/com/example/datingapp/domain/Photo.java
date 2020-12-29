package com.example.datingapp.domain;

import javax.persistence.*;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private Boolean IsMain;
    @ManyToOne
    private User user;

}
