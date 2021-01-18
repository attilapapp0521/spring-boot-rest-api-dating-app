package com.example.datingapp.helpers;

import com.example.datingapp.domain.User;
import com.example.datingapp.dto.RegisterDto;
import com.example.datingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private AccountService accountService;

    @Autowired
    public CommandLineAppStartupRunner(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
       if(!accountService.userExists("admin")){
           RegisterDto registerDto = new RegisterDto();

           registerDto.setUsername("admin");
           registerDto.setPassword("Admin");
           registerDto.setCity("No info");
           registerDto.setGender("male");
           registerDto.setCountry("No info");
           registerDto.setKnownAs("Admin");
           registerDto.setDateOfBirth(LocalDateTime.now());

           accountService.register(registerDto);
       }



    }
}
