package com.example.datingapp.service;

import com.example.datingapp.domain.Role;
import com.example.datingapp.domain.User;
import com.example.datingapp.dto.LoginDto;
import com.example.datingapp.dto.RegisterDto;
import com.example.datingapp.dto.UserDto;
import com.example.datingapp.jwt.JwtProvider;
import com.example.datingapp.repository.RoleRepository;
import com.example.datingapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class AccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public AccountService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.roleRepository = roleRepository;
    }

    public boolean userExists(String username) {
        return userRepository.getUserByUsername(username) != null;
    }

    public void register(RegisterDto registerDto) {
        createRoles();
        Role roleUser = roleRepository.findByName("ROLE_USER");
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        User user = new User(registerDto, encodedPassword, roleUser);

        userRepository.save(user);
        logger.debug("New user (username: " + user.getUsername() + ") saved in database.");
    }

    public UserDto login(LoginDto loginDto) {
        User user = userRepository.getUserByUsername(loginDto.getUsername());
        if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            String token = jwtProvider.generateToken(loginDto.getUsername());
            loggedEntry(loginDto.getUsername());
            return new UserDto(user, token);
        }

        return null;
    }

    private void loggedEntry(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user != null) {
            user.setLastActive(LocalDateTime.now());
        }

    }

    private void createRoles() {
        if (roleRepository.findAll().isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleRepository.save(roleAdmin);

            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepository.save(roleUser);

            logger.debug("Roles is created");
        }
    }
}
