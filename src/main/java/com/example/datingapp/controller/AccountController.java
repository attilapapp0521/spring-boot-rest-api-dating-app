package com.example.datingapp.controller;

import com.example.datingapp.dto.LoginDto;
import com.example.datingapp.dto.RegisterDto;
import com.example.datingapp.dto.UserDto;
import com.example.datingapp.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDto registerDto) {
        logger.debug( "Registration is in progress...");
        if (userExists(registerDto.getUsername())) {
            logger.warn("Registration is failed. The username is taken");
            return new ResponseEntity("Username is taken", HttpStatus.BAD_REQUEST);
        }
        accountService.register(registerDto);
        logger.debug("Registration is successful.");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginDto loginDto) {
        logger.debug("Login is in progress...");
        UserDto userDto = accountService.login(loginDto);
//        if (userDto == null) {
//            logger.warn("Login is failed. Invalid username or password");
//            return new ResponseEntity(
//                    "Invalid username or password", HttpStatus.UNAUTHORIZED);
//        }
        logger.debug("Login is successful.");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    private boolean userExists(String username) {
        return accountService.userExists(username);
    }


}
