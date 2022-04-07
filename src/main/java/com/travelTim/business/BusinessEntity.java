package com.travelTim.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelTim.activities.ActivityOfferEntity;
import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.food.FoodOfferEntity;
import com.travelTim.location.City;
import com.travelTim.lodging.LegalPersonLodgingOfferEntity;
import com.travelTim.user.UserEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "business")
public class BusinessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private City city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String cui;

    @ManyToOne
    @JoinColumn(columnDefinition = "user_id")
    @JsonIgnore
    private UserEntity user;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<LegalPersonLodgingOfferEntity> lodgingOffers = new HashSet<>();

    @OneToOne(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private FoodOfferEntity foodOffer;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<AttractionOfferEntity> attractionOffers = new HashSet<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ActivityOfferEntity> activityOffers = new HashSet<>();

    public BusinessEntity() {
    }

    public BusinessEntity(String name, City city, String address, String cui, UserEntity user) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.cui = cui;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public Set<LegalPersonLodgingOfferEntity> getLodgingOffers() {
        return lodgingOffers;
    }

    public void setLodgingOffers(Set<LegalPersonLodgingOfferEntity> lodgingOffers) {
        this.lodgingOffers = lodgingOffers;
    }

    public FoodOfferEntity getFoodOffer() {
        return foodOffer;
    }

    public void setFoodOffer(FoodOfferEntity foodOffer) {
        this.foodOffer = foodOffer;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Set<AttractionOfferEntity> getAttractionOffers() {
        return attractionOffers;
    }

    public void setAttractionOffers(Set<AttractionOfferEntity> attractionOffers) {
        this.attractionOffers = attractionOffers;
    }

    public Set<ActivityOfferEntity> getActivityOffers() {
        return activityOffers;
    }

    public void setActivityOffers(Set<ActivityOfferEntity> activityOffers) {
        this.activityOffers = activityOffers;
    }

}
