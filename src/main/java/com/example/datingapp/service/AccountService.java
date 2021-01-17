package com.example.datingapp.service;

import com.example.datingapp.domain.User;
import com.example.datingapp.dto.LoginDto;
import com.example.datingapp.dto.RegisterDto;
import com.example.datingapp.dto.UserDto;
import com.example.datingapp.helpers.Pair;
import com.example.datingapp.jwt.JwtProvider;
import com.example.datingapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@Service
@Transactional
public class AccountService {
    private final UserRepository userRepository;
    private UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public AccountService(UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;

    }

    public boolean userExists(String username) {
        return userRepository.getUserByUsername(username) != null;
    }

    public void register(RegisterDto registerDto) {
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        User user = new User(registerDto, encodedPassword);

        user.setLastActive(LocalDateTime.now());
        saveUser(user);
        logger.debug("New user (username: " + user.getUsername() + ") saved in database.");

    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public ResponseEntity<UserDto> login(LoginDto loginDto) {
        User user = userRepository.getUserByUsername(loginDto.getUsername());
        if (user == null) {
            logger.warn("Username failed");
            return new ResponseEntity("Invalid username", BAD_REQUEST);
        } else if (loginDto.getPassword() == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            logger.warn("Invalid password");
            return new ResponseEntity("Invalid password", BAD_REQUEST);
        }

        String token = jwtProvider.generateToken(user);
        user.setLastActive(LocalDateTime.now());
        UserDto userDto = new UserDto(user, token);
        userDto.setPhotoUrl((String) userService.getPhotos(user).getSecond());
        logger.debug("Login is successful.");

        return new ResponseEntity<>(userDto, OK);
    }



}
