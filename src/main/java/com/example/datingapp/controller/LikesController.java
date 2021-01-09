package com.example.datingapp.controller;

import com.example.datingapp.dto.LikeDto;
import com.example.datingapp.extension.HttpExtension;
import com.example.datingapp.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikesController {
    private final LikesService likesService;

    @Autowired
    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<Void> addLike(@PathVariable String username){
        return likesService.addLike(username);
    }

    @GetMapping
    public ResponseEntity<List<LikeDto>> getUserLikes(String predicate, Pageable pageable){

        Page<LikeDto> users = likesService.getUsersLikes(predicate, pageable);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpExtension.addPaginationHeader(httpHeaders, users.getNumber(),
                users.getNumberOfElements(), users.getTotalElements(),
                users.getTotalPages());

        return ResponseEntity.ok().headers(httpHeaders).body(users.getContent());
    }
}
