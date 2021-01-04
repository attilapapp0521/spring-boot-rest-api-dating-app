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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

import static org.springframework.http.HttpStatus.*;


@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final Cloudinary cloudinary;
    private final PhotoRepository photoRepository;
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    public UserService(UserRepository userRepository, Cloudinary cloudinary, PhotoRepository photoRepository) {
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
        this.photoRepository = photoRepository;
    }

    public User findUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            logger.warn("User was not found.");
        }
        return user;
    }

    public Page<MemberDto> getUsers(Pageable pageable) {
       return userRepository.findAll(pageable)
               .map(this::getMemberDto);
    }

    public MemberDto getUser(String username) {
        User user = findUserByUsername(username);
        return getMemberDto(user);
    }

    public MemberDto getMemberDto(User user) {
        if (user != null) {
            MemberDto memberDto = new MemberDto(user);
            memberDto.setAge(calculateAge(user.getDateOfBirth()));
            logger.debug("User was found");
            return getPhotos(memberDto, user);
        }

        return null;
    }

    private int calculateAge(LocalDateTime dateOfBirth) {
        int age = 0;
        if (dateOfBirth != null) {
            LocalDate today = LocalDate.now();
            age = today.getYear() - dateOfBirth.getYear();
            if (dateOfBirth.toLocalDate().isAfter(today.minusYears(age))) age--;
        }


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

        String username = getAuthenticatedUserName();
        User overwriteUser = updateValues(memberUpdateDto,
                userRepository.getUserByUsername(username));
        logger.debug("User has updated");
        userRepository.save(overwriteUser);
    }

    public String getAuthenticatedUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
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
            if (user.getPhotos().isEmpty()) {
                photo.setMain(true);
            }
            return new PhotoDto(photoRepository.save(photo));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean setMainPhoto(Long photoId) {
        Photo photo = photoRepository.getById(photoId);
        if (photo == null) {
            logger.warn("Failed: photo was not found");
            return null;
        }
        User user = photo.getUser();
        for (Photo newPhoto : user.getPhotos()) {
            if (newPhoto.getMain()) {
                if (newPhoto.getId().equals(photoId)) {
                    logger.warn("Failed: This is the main photo");
                    return false;
                }
                newPhoto.setMain(false);
                photo.setMain(true);
                break;
            }
        }
        userRepository.save(user);
        logger.debug("Main photo is set");
        return true;
    }

    public ResponseEntity<Void> deletePhoto(Long photoId) {
        Photo photo = photoRepository.getById(photoId);

        if (photo == null) {
            logger.warn("Failed: not found photo");
            return new ResponseEntity<>(NOT_FOUND);
        } else if (photo.getMain()) {
            logger.warn("Failed: main photo deleting");
            return new ResponseEntity("You cannot delete your main photo",
                    BAD_REQUEST);
        }
        try {

            cloudinary.uploader().destroy(photo.getPublicId(), ObjectUtils.emptyMap());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(BAD_REQUEST);
        }
        photoRepository.delete(photo);
        logger.debug("Photo has deleted");
        return new ResponseEntity<>(OK);
    }
}
