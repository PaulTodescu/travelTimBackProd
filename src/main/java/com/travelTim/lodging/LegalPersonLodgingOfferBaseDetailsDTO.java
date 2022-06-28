package com.travelTim.lodging;

import com.travelTim.business.BusinessEntity;

public class LegalPersonLodgingOfferBaseDetailsDTO {

    private Long id;
    private String description;
    private BusinessEntity business;
    private Long nrViews;

    public LegalPersonLodgingOfferBaseDetailsDTO() {
    }

    public LegalPersonLodgingOfferBaseDetailsDTO(Long id, String description, BusinessEntity business, Long nrViews) {
        this.id = id;
        this.description = description;
        this.business = business;
        this.nrViews = nrViews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BusinessEntity getBusiness() {
        return business;
    }

    public void setBusiness(BusinessEntity business) {
        this.business = business;
    }

    public Long getNrViews() {
        return nrViews;
    }

    public void setNrViews(Long nrViews) {
        this.nrViews = nrViews;
    }
}
