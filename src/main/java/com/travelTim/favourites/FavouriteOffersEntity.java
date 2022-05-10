package com.travelTim.favourites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelTim.activities.ActivityOfferEntity;
import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.food.FoodOfferEntity;
import com.travelTim.lodging.LodgingOfferEntity;
import com.travelTim.user.UserEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "favourites")
public class FavouriteOffersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name="favourite_lodging_offers",
            joinColumns = @JoinColumn( name="favourites_id"),
            inverseJoinColumns = @JoinColumn( name="lodging_offer_id")
    )
    private Set<LodgingOfferEntity> lodgingOffers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name="favourite_food_offers",
            joinColumns = @JoinColumn( name="favourites_id"),
            inverseJoinColumns = @JoinColumn( name="food_offer_id")
    )
    private Set<FoodOfferEntity> foodOffers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name="favourite_attraction_offers",
            joinColumns = @JoinColumn( name="favourites_id"),
            inverseJoinColumns = @JoinColumn( name="attraction_offer_id")
    )
    private Set<AttractionOfferEntity> attractionOffers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name="favourite_activity_offers",
            joinColumns = @JoinColumn( name="favourites_id"),
            inverseJoinColumns = @JoinColumn( name="activity_offer_id")
    )
    private Set<ActivityOfferEntity> activityOffers = new HashSet<>();

    @OneToOne(mappedBy = "favourites")
    @JsonIgnore
    private UserEntity user;

    public FavouriteOffersEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<LodgingOfferEntity> getLodgingOffers() {
        return lodgingOffers;
    }

    public void setLodgingOffers(Set<LodgingOfferEntity> lodgingOffers) {
        this.lodgingOffers = lodgingOffers;
    }

    public Set<FoodOfferEntity> getFoodOffers() {
        return foodOffers;
    }

    public void setFoodOffers(Set<FoodOfferEntity> foodOffers) {
        this.foodOffers = foodOffers;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
