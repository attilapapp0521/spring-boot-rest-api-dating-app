package com.example.datingapp.service;

import com.example.datingapp.domain.User;
import com.example.datingapp.domain.UserLike;
import com.example.datingapp.dto.LikeDto;
import com.example.datingapp.repository.LikesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.springframework.http.HttpStatus.*;

@Service
@Transactional
public class LikesService {
    private final UserService userService;
    private final LikesRepository likesRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public LikesService(UserService userService, LikesRepository likesRepository) {
        this.userService = userService;
        this.likesRepository = likesRepository;
    }

    public ResponseEntity<Void> addLike(String username) {
        User likedUser = userService.findUserByUsername(username);
        User sourceUser = userService.findUserByUsername(userService.getAuthenticatedUserName());

        if (likedUser == null) {
            return new ResponseEntity<>(NOT_FOUND);
        } else if (sourceUser.getUsername().equals(username)) {
            return new ResponseEntity("You cannot like yourself", BAD_REQUEST);
        } else if (isLiked(sourceUser, likedUser)) {
            return new ResponseEntity("You already like this user", BAD_REQUEST);
        }

        UserLike userLike = new UserLike(sourceUser, likedUser);
        likesRepository.save(userLike);
        return new ResponseEntity<>(CREATED);
    }

    public boolean isLiked(User sourceUser, User likedUser) {

        UserLike userLike = likesRepository.
                isLikedUser(sourceUser.getId(), likedUser.getId());
        return userLike != null;
    }

    public Page<LikeDto> getUsersLikes(String predicate, Pageable pageable) {
        User user = userService.findUserByUsername(userService.getAuthenticatedUserName());

        if (predicate.equals("liked")) {
            return likesRepository.getUserLikes(user.getUsername(), pageable)
                    .map(this::getLikeDto);
        }
        return likesRepository.getUserLikesBy(user.getUsername(), pageable)
                .map(this::getLikeDto);
    }

    private LikeDto getLikeDto(User user) {

        LikeDto likeDto = new LikeDto(user);
        likeDto.setAge(userService.calculateAge(user.getDateOfBirth()));
        likeDto.setPhotoUrl((String) userService.getPhotos(user).getSecond());

        return likeDto;
    }
}
