package com.example.datingapp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.datingapp.domain.Photo;
import com.example.datingapp.domain.User;
import com.example.datingapp.dto.MemberDto;
import com.example.datingapp.dto.MemberUpdateDto;
import com.example.datingapp.dto.PhotoDto;
import com.example.datingapp.repository.PhotoRepository;
import com.example.datingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final Cloudinary cloudinary;
    private final PhotoRepository photoRepository;


    @Autowired
    public UserService(UserRepository userRepository, Cloudinary cloudinary, PhotoRepository photoRepository) {
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
        this.photoRepository = photoRepository;
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
            if (photo.getMain() != null && photo.getMain()) {
                mainPhotoUrl = photo.getUrl();
            }
            photoDtoList.add(photoDto);
        }
        memberDto.setPhotoUrl(mainPhotoUrl);
        memberDto.setPhotos(photoDtoList);

        return memberDto;
    }

    public void updateUser(MemberUpdateDto memberUpdateDto) {

        String username = getAuthenticatedUserName();
        User overwroteUser = updateValues(memberUpdateDto,
                userRepository.getUserByUsername(username));

        userRepository.save(overwroteUser);
    }

    public String getAuthenticatedUserName(){
        return  SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private User updateValues(MemberUpdateDto memberUpdateDto, User user) {

        user.setIntroduction(memberUpdateDto.getIntroduction());
        user.setLookingFor((memberUpdateDto.getLookingFor()));
        user.setInterests(memberUpdateDto.getInterests());
        user.setCity(memberUpdateDto.getCity());
        user.setCountry(memberUpdateDto.getCountry());

        return user;
    }

    public PhotoDto addPhoto(MultipartFile multipartFile) {
        try {
            Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
            String[] result = new String[3];
            result[0] = (String) uploadResult.get("public_id");
            result[1] = (String) uploadResult.get("url");
            User user = findUserByUsername(getAuthenticatedUserName());
            Photo photo = new Photo(result[1], result[0], user);
            if(user.getPhotos().isEmpty()){
                photo.setMain(true);
            }
           return new PhotoDto(photoRepository.save(photo));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
