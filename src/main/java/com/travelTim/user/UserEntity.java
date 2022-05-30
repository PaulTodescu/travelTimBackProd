package com.travelTim.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelTim.activities.ActivityOfferEntity;
import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.business.BusinessEntity;
import com.travelTim.favourites.FavouriteOffersEntity;
import com.travelTim.food.FoodOfferEntity;
import com.travelTim.location.LocationEntity;
import com.travelTim.lodging.LodgingOfferEntity;
import com.travelTim.lodging.PhysicalPersonLodgingOfferEntity;
import com.travelTim.reservation.OfferReservationEntity;
import com.travelTim.review.ReviewEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserGender gender;

    @Column(nullable = false)
    private String email;

    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<BusinessEntity> businesses = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<LodgingOfferEntity> lodgingOffers = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<FoodOfferEntity> foodOffers = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<AttractionOfferEntity> attractionOffers = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ActivityOfferEntity> activityOffers = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(columnDefinition = "favourites_id")
    @JsonIgnore
    private FavouriteOffersEntity favourites;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OfferReservationEntity> reservations = new HashSet<>();

    @OneToMany(mappedBy = "ownerUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ReviewEntity> reviews = new HashSet<>();

    @OneToMany(mappedBy = "targetUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ReviewEntity> targetUserReviews = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<LocationEntity> visitedLocations = new HashSet<>();


    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, UserGender gender, String email, String phoneNumber,
                      String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserGender getGender() {
        return gender;
    }

    public void setGender(UserGender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<BusinessEntity> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(Set<BusinessEntity> businesses) {
        this.businesses = businesses;
    }

    public Set<LodgingOfferEntity> getLodgingOffers() {
        return lodgingOffers;
    }

    @JsonIgnore
    public Set<PhysicalPersonLodgingOfferEntity> getPhysicalPersonLodgingOffers(){
        return this.lodgingOffers.stream()
                .filter(offer -> offer instanceof PhysicalPersonLodgingOfferEntity)
                .map(offer -> (PhysicalPersonLodgingOfferEntity) offer)
                .collect(Collectors.toSet());
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

    public FavouriteOffersEntity getFavourites() {
        return favourites;
    }

    public void setFavourites(FavouriteOffersEntity favourites) {
        this.favourites = favourites;
    }

    public Set<OfferReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(Set<OfferReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public Set<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(Set<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public Set<ReviewEntity> getTargetUserReviews() {
        return targetUserReviews;
    }

    public void setTargetUserReviews(Set<ReviewEntity> targetUserReviews) {
        this.targetUserReviews = targetUserReviews;
    }

    public Set<LocationEntity> getVisitedLocations() {
        return visitedLocations;
    }

    public void setVisitedLocations(Set<LocationEntity> visitedLocations) {
        this.visitedLocations = visitedLocations;
    }
}
