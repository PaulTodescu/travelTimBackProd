package com.travelTim.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewDAO extends JpaRepository<ReviewEntity, Long> {

    Optional<ReviewEntity> findReviewEntityById(Long id);

    void deleteReviewEntityById(Long id);

}
