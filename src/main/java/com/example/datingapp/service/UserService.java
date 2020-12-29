package com.example.datingapp.service;

import com.example.datingapp.domain.User;
import com.example.datingapp.dto.MemberDto;
import com.example.datingapp.dto.MemberUpdateDto;
import com.example.datingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    public void updateUser(MemberUpdateDto memberUpdateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);
        User overwrittenUser = UpdateValues(memberUpdateDto,user);

        userRepository.save(overwrittenUser);
    }

    private User UpdateValues(MemberUpdateDto memberUpdateDto, User user) {
//
        user.setIntroduction(memberUpdateDto.getIntroduction());
        user.setLookingFor((memberUpdateDto.getLookingFor()));
        user.setInterests(memberUpdateDto.getInterests());
        user.setCity(memberUpdateDto.getCity());
        user.setCountry(memberUpdateDto.getCountry());

        return user;
    }
}
