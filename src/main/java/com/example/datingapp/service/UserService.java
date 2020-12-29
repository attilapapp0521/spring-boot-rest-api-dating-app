package com.example.datingapp.service;

import com.example.datingapp.domain.User;
import com.example.datingapp.dto.MemberDto;
import com.example.datingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public List<MemberDto> getUsers() {
        return userRepository.findAll().stream().
                map(MemberDto::new).collect(Collectors.toList());
    }

    public MemberDto getUser(String username) {
        User user = findUserByUsername(username);
        if (user != null) {
            return new MemberDto(user);
        }
        return null;
    }
}
