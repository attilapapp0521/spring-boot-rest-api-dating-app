package com.example.datingapp.service;

import com.example.datingapp.domain.User;
import com.example.datingapp.dto.LoginDto;
import com.example.datingapp.dto.RegisterDto;
import com.example.datingapp.dto.UserDto;
import com.example.datingapp.jwt.JwtProvider;
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
    private JwtProvider jwtProvider;

    @Autowired
    public AccountService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public boolean userExists(String username) {
        return userRepository.getUserByUsername(username) != null;
    }

    public void register(RegisterDto registerDto) {

        User user = new User(registerDto.getUsername(),
                passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);

    }

    public UserDto login(LoginDto loginDto) {
        User user = userRepository.getUserByUsername(loginDto.getUsername());

        if(user != null && passwordEncoder.matches(loginDto.getPassword(),user.getPassword())){
            String token = jwtProvider.generateToken(loginDto.getUsername());
            return new UserDto(user,token);
        }
        return null;
    }
}
