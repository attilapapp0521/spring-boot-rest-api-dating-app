package com.example.datingapp.controller;

import com.example.datingapp.dto.LoginDto;
import com.example.datingapp.dto.RegisterDto;
import com.example.datingapp.dto.UserDto;
import com.example.datingapp.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDto registerDto) {
        logger.debug( "Registration is in progress...");
        if (userExists(registerDto.getUsername())) {
            logger.warn("Registration is failed. The username is taken");
            return new ResponseEntity("Username is taken", BAD_REQUEST);
        }
        accountService.register(registerDto);
        logger.debug("Registration is successful.");
        return new ResponseEntity<>(CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginDto loginDto) {
        logger.debug("Login is in progress...");
        UserDto userDto = accountService.login(loginDto);
        logger.debug("Login is successful.");
        return new ResponseEntity<>(userDto,OK);
    }

    private boolean userExists(String username) {
        return accountService.userExists(username);
    }

    @GetMapping()
    public ResponseEntity<Void> test(){
        return new  ResponseEntity(OK);
    }


}
