package com.travelTim.review;

public class ReviewRatingDTO {

    private Double rating;
    private Integer nrReviews;

    public ReviewRatingDTO() {
    }

    public ReviewRatingDTO(Double rating, Integer nrReviews) {
        this.rating = rating;
        this.nrReviews = nrReviews;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getNrReviews() {
        return nrReviews;
    }

    public void setNrReviews(Integer nr_reviews) {
        this.nrReviews = nr_reviews;
    }
}
