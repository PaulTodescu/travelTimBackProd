package com.travelTim.lodging;

import com.travelTim.business.BusinessEntity;
import com.travelTim.user.UserContactDTO;


public class LegalPersonLodgingOfferBaseDetailsDTO {

    private Long id;
    private String description;
    private UserContactDTO user;
    private BusinessEntity business;

    public LegalPersonLodgingOfferBaseDetailsDTO() {
    }

    public LegalPersonLodgingOfferBaseDetailsDTO(Long id, String description, UserContactDTO user, BusinessEntity business) {
        this.id = id;
        this.description = description;
        this.user = user;
        this.business = business;
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

    public UserContactDTO getUser() {
        return user;
    }

    public void setUser(UserContactDTO user) {
        this.user = user;
    }

    public BusinessEntity getBusiness() {
        return business;
    }

    public void setBusiness(BusinessEntity business) {
        this.business = business;
    }
}
