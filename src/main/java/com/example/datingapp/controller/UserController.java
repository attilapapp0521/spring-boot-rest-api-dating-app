package com.example.datingapp.controller;

import com.example.datingapp.dto.MemberDto;
import com.example.datingapp.dto.MemberUpdateDto;
import com.example.datingapp.dto.PhotoDto;
import com.example.datingapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<MemberDto> getUser(@PathVariable String username) {
        MemberDto memberDto = userService.getUser(username);
        if (memberDto == null) return new ResponseEntity<>(NOT_FOUND);

        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody MemberUpdateDto memberUpdateDto) {
        userService.updateUser(memberUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-photo")
    public ResponseEntity<PhotoDto> addPhoto(@RequestParam("file") MultipartFile multipartFile){
       PhotoDto photoDto = userService.addPhoto(multipartFile);
       logger.info("Attempting to upload new image to cloudinary");
       if(photoDto == null) return new  ResponseEntity("The photoDto is null, everything is wrong!",HttpStatus.BAD_REQUEST);
       return new ResponseEntity<>(photoDto, CREATED);
    }

    @PutMapping("/set-main-photo/{photoId}")
    public ResponseEntity<Void> setMainPhoto(@PathVariable Long photoId){
          return userService.setMainPhoto(photoId);
    }

    @DeleteMapping("/delete-photo/{photoId}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId){
       return userService.deletePhoto(photoId);
    }

}
