package com.example.datingapp.controller;

import com.example.datingapp.dto.MemberDto;
import com.example.datingapp.dto.MemberUpdateDto;
import com.example.datingapp.dto.PhotoDto;
import com.example.datingapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
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
    public ResponseEntity<Page<MemberDto>> getUsers(Pageable pageable) {
        logger.debug("Users reading of database");
        return new ResponseEntity<>(userService.getUsers(pageable), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<MemberDto> getUser(@PathVariable String username) {
        logger.debug("User reading of database...");
        MemberDto memberDto = userService.getUser(username);
        if (memberDto == null){
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(memberDto, OK);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@Valid @RequestBody MemberUpdateDto memberUpdateDto) {
        logger.debug("User updating...");
        userService.updateUser(memberUpdateDto);
        return new ResponseEntity<>(OK);
    }

    @PostMapping("/add-photo")
    public ResponseEntity<PhotoDto> addPhoto(@RequestParam("file") MultipartFile multipartFile){
        logger.debug("Photo adding of database and cloud...");
       PhotoDto photoDto = userService.addPhoto(multipartFile);
       if(photoDto == null) {
           logger.warn("Photo has not added");
           return new  ResponseEntity(BAD_REQUEST);
       }
       logger.debug("Photo has added");
       return new ResponseEntity<>(photoDto, CREATED);
    }

    @PutMapping("/set-main-photo/{photoId}")
    public ResponseEntity<Void> setMainPhoto( @PathVariable Long photoId){
        logger.debug("Main photo settings... ");
         Boolean isDone = userService.setMainPhoto(photoId);
         if(isDone == null){
            return new ResponseEntity<>(NOT_FOUND);
         }else if(!isDone){
             return new ResponseEntity("This is already your main photo",BAD_REQUEST);
         }

         return new ResponseEntity<>(NO_CONTENT);
    }

    @DeleteMapping("/delete-photo/{photoId}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId){
        logger.debug("Photo deleting of database and cloud... ");
       return userService.deletePhoto(photoId);
    }

}
