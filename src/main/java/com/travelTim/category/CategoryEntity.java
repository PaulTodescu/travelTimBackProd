package com.travelTim.category;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelTim.activities.ActivityOfferEntity;
import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.food.FoodOfferEntity;
import com.travelTim.lodging.LodgingOfferEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<LodgingOfferEntity> lodgingOffers = new HashSet<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<FoodOfferEntity> foodOffers = new HashSet<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<AttractionOfferEntity> attractionOffers = new HashSet<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ActivityOfferEntity> activityOffers = new HashSet<>();

    public CategoryEntity() {
    }

    public CategoryEntity(String name) {
        this.name = name;
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
}
