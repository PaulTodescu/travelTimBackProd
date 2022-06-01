package com.travelTim.lodging;

public class LodgingOffersStatistics {

    private Double averageOffersViews;
    private Double averageUserOffersViews;
    private Double averageOffersPrice;
    private Double averageUserOffersPrice;
    private Double averageRequestedOffersPrice;

    public LodgingOffersStatistics() {
    }

    public LodgingOffersStatistics(Double averageOffersViews, Double averageUserOffersViews,
                                   Double averageOffersPrice, Double averageUserOffersPrice,
                                   Double averageRequestedOffersPrice) {
        this.averageOffersViews = averageOffersViews;
        this.averageUserOffersViews = averageUserOffersViews;
        this.averageOffersPrice = averageOffersPrice;
        this.averageUserOffersPrice = averageUserOffersPrice;
        this.averageRequestedOffersPrice = averageRequestedOffersPrice;
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

    public Double getAverageOffersPrice() {
        return averageOffersPrice;
    }

    public void setAverageOffersPrice(Double averageOffersPrice) {
        this.averageOffersPrice = averageOffersPrice;
    }

    public Double getAverageUserOffersPrice() {
        return averageUserOffersPrice;
    }

    public void setAverageUserOffersPrice(Double averageUserOffersPrice) {
        this.averageUserOffersPrice = averageUserOffersPrice;
    }

    public Double getAverageRequestedOffersPrice() {
        return averageRequestedOffersPrice;
    }

    public void setAverageRequestedOffersPrice(Double averageRequestedOffersPrice) {
        this.averageRequestedOffersPrice = averageRequestedOffersPrice;
    }
}
