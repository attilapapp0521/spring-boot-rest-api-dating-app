package com.example.datingapp.controller;

import com.example.datingapp.dto.MemberDto;
import com.example.datingapp.dto.MemberUpdateDto;
import com.example.datingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

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
        if (memberDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody MemberUpdateDto memberUpdateDto) {
        userService.updateUser(memberUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
