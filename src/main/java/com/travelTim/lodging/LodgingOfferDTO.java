package com.travelTim.lodging;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelTim.business.BusinessDTOMapper;
import com.travelTim.business.BusinessEntity;
import com.travelTim.business.BusinessForOffersPageDTO;
import com.travelTim.currency.Currency;
import com.travelTim.location.City;
import com.travelTim.offer.OfferStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LodgingOfferDTO {

    private Long id;
    private BusinessEntity business;
    private String title;
    private String address;
    private City city;
    private Integer nrRooms;
    private Integer nrSingleBeds;
    private Integer nrDoubleBeds;
    private Integer floor;
    private String image;
    private LocalDateTime createdAt;
    private Float price;
    private Currency currency;
    private OfferStatus status;
    private Long nrViews;

    public LodgingOfferDTO() {
    }

    public LodgingOfferDTO(Long id, BusinessEntity business, String title, String address, City city,
                           Integer nrRooms, Integer nrSingleBeds, Integer nrDoubleBeds, OfferStatus status,
                           Long nrViews, Integer floor,LocalDateTime createdAt, Float price, Currency currency) {
        this.id = id;
        this.business = business;
        this.title = title;
        this.address = address;
        this.city = city;
        this.nrRooms = nrRooms;
        this.nrSingleBeds = nrSingleBeds;
        this.nrDoubleBeds = nrDoubleBeds;
        this.status = status;
        this.nrViews = nrViews;
        this.floor = floor;
        this.createdAt = createdAt;
        this.price = price;
        this.currency = currency;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessForOffersPageDTO getBusiness() {
        if (business != null) {
            BusinessDTOMapper mapper = new BusinessDTOMapper();
            return mapper.mapBusinessForOffersPageDTO(business);
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getNrLodgingOffers() {
        if (this.business != null) {
            return this.business.getLodgingOffers().stream()
                    .filter(offer -> offer.getStatus() == OfferStatus.active)
                    .collect(Collectors.toSet()).size();
        }
        return null;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public Long getNrViews() {
        return nrViews;
    }

    public void setNrViews(Long nrViews) {
        this.nrViews = nrViews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LodgingOfferDTO that = (LodgingOfferDTO) o;
        if (business != null && that.business != null) {
            return business.equals(that.business);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(business);
    }
}
