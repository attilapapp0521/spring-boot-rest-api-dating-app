package com.example.datingapp.service;

import com.example.datingapp.domain.Role;
import com.example.datingapp.domain.User;
import com.example.datingapp.dto.LoginDto;
import com.example.datingapp.dto.RegisterDto;
import com.example.datingapp.dto.UserDto;
import com.example.datingapp.jwt.JwtProvider;
import com.example.datingapp.repository.RoleRepository;
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
    private RoleRepository roleRepository;

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

    }

    public UserDto login(LoginDto loginDto) {
        createRoles();
        User user = userRepository.getUserByUsername(loginDto.getUsername());
        if(user != null && passwordEncoder.matches(loginDto.getPassword(),user.getPassword())){
            String token = jwtProvider.generateToken(loginDto.getUsername());
            return new UserDto(user,token);
        }
        return null;
    }
    private void createRoles(){
        if (roleRepository.findAll().isEmpty()){
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleRepository.save(roleAdmin);

            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepository.save(roleUser);
        }
    }
}
