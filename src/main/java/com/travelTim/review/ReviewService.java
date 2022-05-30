package com.travelTim.review;

import com.travelTim.business.BusinessEntity;
import com.travelTim.business.BusinessService;
import com.travelTim.files.ImageService;
import com.travelTim.user.UserEntity;
import com.travelTim.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {

    private final ReviewDAO reviewDAO;
    private final UserService userService;
    private final BusinessService businessService;
    private final ImageService imageService;
    private final ReviewDTOMapper mapper;

    @Autowired
    public ReviewService(ReviewDAO reviewDAO, UserService userService,
                         BusinessService businessService, ImageService imageService) {
        this.reviewDAO = reviewDAO;
        this.userService = userService;
        this.businessService = businessService;
        this.imageService = imageService;
        this.mapper = new ReviewDTOMapper();
    }

    public ReviewEntity findReviewById(Long reviewId) {
        return this.reviewDAO.findReviewEntityById(reviewId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Review with id: " + reviewId + " was not found")
        );
    }

    public ReviewDTO addReviewForUser(Long userId, ReviewEntity review) {
        UserEntity targetUser = this.userService.findUserById(userId);
        targetUser.getReviews().add(review);
        review.setTargetUser(targetUser);
        UserEntity user = this.userService.findLoggedInUser();
        user.getReviews().add(review);
        review.setOwnerUser(user);
        ReviewDTO savedReview =  mapper.mapReviewToDTO(this.reviewDAO.save(review));
        savedReview.setImage(this.imageService.getUserImage(review.getOwnerUser().getId()));
        return savedReview;
    }

    public ReviewDTO addReviewForBusiness(Long businessId, ReviewEntity review) {
        UserEntity user = this.userService.findLoggedInUser();
        user.getReviews().add(review);
        review.setOwnerUser(user);
        BusinessEntity business = this.businessService.findBusinessById(businessId);
        business.getReviews().add(review);
        review.setBusiness(business);
        ReviewDTO savedReview =  mapper.mapReviewToDTO(this.reviewDAO.save(review));
        savedReview.setImage(this.imageService.getUserImage(review.getOwnerUser().getId()));
        return savedReview;
    }

    public List<ReviewDTO> getReviewsForTargetUser(Long userId) {
        UserEntity user = this.userService.findUserById(userId);
        Set<ReviewDTO> reviews = this.mapper.mapReviewsToDTOs(user.getTargetUserReviews());
        for (ReviewDTO review: reviews) {
            review.setImage(this.imageService.getUserImage(review.getOwnerUser().getId()));
        }
        return reviews.stream()
                .sorted(Comparator.comparing(ReviewDTO::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsForBusiness(Long businessId) {
        BusinessEntity business = this.businessService.findBusinessById(businessId);
        Set<ReviewDTO> reviews = this.mapper.mapReviewsToDTOs(business.getReviews());
        for (ReviewDTO review: reviews) {
            review.setImage(this.imageService.getUserImage(review.getOwnerUser().getId()));
        }
        return reviews.stream()
                .sorted(Comparator.comparing(ReviewDTO::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public List<ReviewForUserDTO> getReviewsForUser() {
        UserEntity user = this.userService.findLoggedInUser();
        Set<ReviewForUserDTO> reviews = this.mapper.mapReviewsToReviewsForUserDTO(user.getReviews());
        return reviews.stream()
                .sorted(Comparator.comparing(ReviewForUserDTO::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public ReviewRatingDTO getUserRating(Long userId) {
        UserEntity user = this.userService.findUserById(userId);
        Double averageRating = user.getTargetUserReviews().stream()
                .filter(review -> review.getRating() != 0)
                .collect(Collectors.averagingDouble(ReviewEntity::getRating));
        Integer nrReviews = user.getTargetUserReviews().size();
        return new ReviewRatingDTO(averageRating, nrReviews);
    }

    public ReviewRatingDTO getBusinessRating(Long businessId) {
        BusinessEntity business = this.businessService.findBusinessById(businessId);
        Double averageRating = business.getReviews().stream()
                .filter(review -> review.getRating() != 0)
                .collect(Collectors.averagingDouble(ReviewEntity::getRating));
        Integer nrReviews = business.getReviews().size();
        return new ReviewRatingDTO(averageRating, nrReviews);
    }

    public void deleteReview(Long reviewId) {
        ReviewEntity review = this.findReviewById(reviewId);
        review.getOwnerUser().getReviews().remove(review);
        review.setOwnerUser(null);
        if (review.getTargetUser() != null) {
            review.getTargetUser().getTargetUserReviews().remove(review);
            review.setTargetUser(null);
        }
        this.reviewDAO.deleteReviewEntityById(reviewId);
    }

}
