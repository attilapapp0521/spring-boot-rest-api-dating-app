package com.example.datingapp.service;

import com.example.datingapp.domain.Photo;
import com.example.datingapp.domain.User;
import com.example.datingapp.dto.MemberDto;
import com.example.datingapp.dto.MemberUpdateDto;
import com.example.datingapp.dto.PhotoDto;
import com.example.datingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public List<MemberDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();

        for (User user : users) {
            MemberDto memberDto = new MemberDto(user);
            memberDto.setAge(calculateAge(user.getDateOfBirth()));
            memberDtoList.add(getPhotos(memberDto, user));
        }

        return memberDtoList;
    }

    public MemberDto getUser(String username) {
        User user = findUserByUsername(username);
        if (user != null) {
            MemberDto memberDto = new MemberDto(user);
            memberDto.setAge(calculateAge(user.getDateOfBirth()));
            return getPhotos(memberDto, user);
        }

        return null;
    }

    private int calculateAge(LocalDateTime dateOfBirth) {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - dateOfBirth.getYear();
        if (dateOfBirth.toLocalDate().isAfter(today.minusYears(age))) age--;

        return age;
    }

    private MemberDto getPhotos(MemberDto memberDto, User user) {
        List<PhotoDto> photoDtoList = new ArrayList<>();
        String mainPhotoUrl = "";
        for (Photo photo : user.getPhotos()) {
            PhotoDto photoDto = new PhotoDto(photo);
            if (photo.getMain()) {
                mainPhotoUrl = photo.getUrl();
            }
            photoDtoList.add(photoDto);
        }
        memberDto.setPhotoUrl(mainPhotoUrl);
        memberDto.setPhotos(photoDtoList);

        return memberDto;
    }

    public void updateUser(MemberUpdateDto memberUpdateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User overwroteUser = updateValues(memberUpdateDto,
                userRepository.getUserByUsername(username));

        userRepository.save(overwroteUser);
    }

    private User updateValues(MemberUpdateDto memberUpdateDto, User user) {

        user.setIntroduction(memberUpdateDto.getIntroduction());
        user.setLookingFor((memberUpdateDto.getLookingFor()));
        user.setInterests(memberUpdateDto.getInterests());
        user.setCity(memberUpdateDto.getCity());
        user.setCountry(memberUpdateDto.getCountry());

        return user;
    }
}
