package com.example.datingapp.service;

import com.example.datingapp.domain.User;
import com.example.datingapp.dto.RegisterDto;
import com.example.datingapp.dto.UserDto;
import com.example.datingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExists(String username) {
        return userRepository.getUserByUsername(username) != null;
    }

    public UserDto register(RegisterDto registerDto) {

        User user = new User(registerDto.getUsername(),
                passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);
        return new UserDto(user);
    }
}
