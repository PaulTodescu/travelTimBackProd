package com.travelTim.review;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReviewDTOMapper {

    private final ModelMapper mapper;

    public ReviewDTOMapper() {
        this.mapper = new ModelMapper();
    }

    public ReviewDTO mapReviewToDTO(ReviewEntity review) {
        return this.mapper.map(review, ReviewDTO.class);
    }

    public Set<ReviewDTO> mapReviewsToDTOs(Set<ReviewEntity> reviews) {
        return reviews.stream().map(this::mapReviewToDTO).collect(Collectors.toSet());
    }

    public ReviewForUserDTO mapReviewToReviewForUserDTO(ReviewEntity review) {
        return this.mapper.map(review, ReviewForUserDTO.class);
    }

    public Set<ReviewForUserDTO> mapReviewsToReviewsForUserDTO(Set<ReviewEntity> reviews) {
        return reviews.stream().map(this::mapReviewToReviewForUserDTO).collect(Collectors.toSet());
    }
}
