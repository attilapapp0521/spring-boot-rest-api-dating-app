package com.example.datingapp.repository;

import com.example.datingapp.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT p FROM Photo p WHERE p.id = :id")
    Photo getById(@Param("id") Long id);
}
