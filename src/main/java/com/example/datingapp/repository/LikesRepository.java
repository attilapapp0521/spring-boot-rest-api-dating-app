package com.example.datingapp.repository;

import com.example.datingapp.domain.User;
import com.example.datingapp.domain.UserLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface LikesRepository extends JpaRepository<UserLike, Long> {

    @Query("SELECT l.likedUser FROM UserLike l WHERE l.sourceUser.username = :username " +
            "ORDER BY l.likedUser.username" )
    Page<User> getUserLikes(@Param("username") String username,
                                Pageable pageable);

    @Query("SELECT l.sourceUser FROM UserLike l WHERE l.likedUser.username = :username " +
            "ORDER BY l.sourceUser.username")
    Page<User> getUserLikesBy(@Param("username") String username,
                              Pageable pageable);

    @Query("SELECT l FROM UserLike l WHERE l.sourceUser.id = :sourceId" +
            " AND l.likedUser.id = :likedId")
    UserLike isLikedUser(@Param("sourceId") Long sourceId,
                        @Param("likedId") Long likedId);
}
