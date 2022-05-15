package com.travelTim.lodging;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelTim.business.BusinessDTO;
import com.travelTim.business.BusinessDTOMapper;
import com.travelTim.business.BusinessEntity;
import com.travelTim.currency.Currency;
import com.travelTim.location.City;
import com.travelTim.offer.OfferStatus;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LodgingOfferBaseDetailsDTO {

    private Long id;
    private BusinessEntity business;
    private String title;
    private String address;
    private City city;
    private String image;
    private LocalDateTime createdAt;
    private Float price;
    private Currency currency;
    private Integer nrRooms;
    private Integer nrSingleBeds;
    private Integer nrDoubleBeds;
    private OfferStatus status;

    public LodgingOfferBaseDetailsDTO() {
    }

    public LodgingOfferBaseDetailsDTO(Long id, BusinessEntity business, String title, String address,
                                      City city, LocalDateTime createdAt, Float price,
                                      Currency currency, Integer nrRooms, Integer nrSingleBeds,
                                      Integer nrDoubleBeds, OfferStatus status) {
        this.id = id;
        this.business = business;
        this.title = title;
        this.address = address;
        this.city = city;
        this.createdAt = createdAt;
        this.price = price;
        this.currency = currency;
        this.nrRooms = nrRooms;
        this.nrSingleBeds = nrSingleBeds;
        this.nrDoubleBeds = nrDoubleBeds;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessDTO getBusiness() {
        if (this.business != null) {
            BusinessDTOMapper mapper = new BusinessDTOMapper();
            return mapper.mapBusinessToDTO(business);
        }
        return null;
    }

    public void setBusiness(BusinessEntity business) {
        this.business = business;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getNrRooms() {
        return nrRooms;
    }

    public void setNrRooms(Integer nrRooms) {
        this.nrRooms = nrRooms;
    }

    public Integer getNrSingleBeds() {
        return nrSingleBeds;
    }

    public void setNrSingleBeds(Integer nrSingleBeds) {
        this.nrSingleBeds = nrSingleBeds;
    }

    public Integer getNrDoubleBeds() {
        return nrDoubleBeds;
    }

    public void setNrDoubleBeds(Integer nrDoubleBeds) {
        this.nrDoubleBeds = nrDoubleBeds;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }
}
