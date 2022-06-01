package com.travelTim.activities;

public class ActivityOffersStatistics {

    private Double averageOffersViews;
    private Double averageUserOffersViews;
    private Double averageOffersTicketsPrice;
    private Double averageUserOffersTicketsPrice;

    public ActivityOffersStatistics() {
    }

    public ActivityOffersStatistics(Double averageOffersViews, Double averageUserOffersViews,
                                      Double averageOffersTicketsPrice, Double averageUserOffersTicketsPrice) {
        this.averageOffersViews = averageOffersViews;
        this.averageUserOffersViews = averageUserOffersViews;
        this.averageOffersTicketsPrice = averageOffersTicketsPrice;
        this.averageUserOffersTicketsPrice = averageUserOffersTicketsPrice;
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

    public Double getAverageOffersTicketsPrice() {
        return averageOffersTicketsPrice;
    }

    public void setAverageOffersTicketsPrice(Double averageOffersTicketsPrice) {
        this.averageOffersTicketsPrice = averageOffersTicketsPrice;
    }

    public Double getAverageUserOffersTicketsPrice() {
        return averageUserOffersTicketsPrice;
    }

    public void setAverageUserOffersTicketsPrice(Double averageUserOffersTicketsPrice) {
        this.averageUserOffersTicketsPrice = averageUserOffersTicketsPrice;
    }

}
