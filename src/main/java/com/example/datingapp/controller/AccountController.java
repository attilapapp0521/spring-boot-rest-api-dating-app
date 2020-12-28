package com.example.datingapp.controller;

import com.example.datingapp.dto.RegisterDto;
import com.example.datingapp.dto.UserDto;
import com.example.datingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto registerDto){
        if(userExists(registerDto.getUsername()))
            return new ResponseEntity("Username is taken",HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(accountService.register(registerDto), HttpStatus.CREATED);
    }

    private boolean userExists(String username) {
       return accountService.userExists(username);
    }


}
