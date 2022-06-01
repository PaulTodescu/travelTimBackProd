package com.travelTim.food;

public class FoodOffersStatistics {
    private Double averageOffersViews;
    private Double averageUserOffersViews;

    public FoodOffersStatistics() {
    }

    public FoodOffersStatistics(Double averageOffersViews, Double averageUserOffersViews) {
        this.averageOffersViews = averageOffersViews;
        this.averageUserOffersViews = averageUserOffersViews;
    }

    public Double getAverageOffersViews() {
        return averageOffersViews;
    }

    public void setAverageOffersViews(Double averageOffersViews) {
        this.averageOffersViews = averageOffersViews;
    }

    public Double getAverageUserOffersViews() {
        return averageUserOffersViews;
    }

    public void setAverageUserOffersViews(Double averageUserOffersViews) {
        this.averageUserOffersViews = averageUserOffersViews;
    }
}
