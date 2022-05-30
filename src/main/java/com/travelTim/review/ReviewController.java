package com.travelTim.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ReviewDTO> addReviewForUser(
            @PathVariable("userId") Long userId,
            @RequestBody ReviewEntity review) {
        ReviewDTO savedReview = this.reviewService.addReviewForUser(userId, review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @PostMapping("/business/{businessId}")
    public ResponseEntity<ReviewDTO> addReviewForBusiness(
            @PathVariable("businessId") Long businessId,
            @RequestBody ReviewEntity review) {
        ReviewDTO savedReview = this.reviewService.addReviewForBusiness(businessId, review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsForTargetUser(
            @PathVariable("userId") Long userId) {
        List<ReviewDTO> reviews = this.reviewService.getReviewsForTargetUser(userId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsForBusiness(
            @PathVariable("businessId") Long businessId) {
        List<ReviewDTO> reviews = this.reviewService.getReviewsForBusiness(businessId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<ReviewForUserDTO>> getReviewsForUser() {
        List<ReviewForUserDTO> reviews = this.reviewService.getReviewsForUser();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") Long reviewId){
        this.reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/rating")
    public ResponseEntity<ReviewRatingDTO> getUserRating(
            @PathVariable("userId") Long userId) {
        ReviewRatingDTO rating = this.reviewService.getUserRating(userId);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @GetMapping("/business/{businessId}/rating")
    public ResponseEntity<ReviewRatingDTO> getBusinessRating(
            @PathVariable("businessId") Long businessId) {
        ReviewRatingDTO rating = this.reviewService.getBusinessRating(businessId);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

}
