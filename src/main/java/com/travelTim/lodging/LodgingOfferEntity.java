package com.travelTim.lodging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelTim.category.CategoryEntity;
import com.travelTim.currency.Currency;
import com.travelTim.user.UserContactDTO;
import com.travelTim.user.UserDTOMapper;
import com.travelTim.user.UserDetailsDTO;
import com.travelTim.user.UserEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "lodging_offer")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class LodgingOfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer nrRooms;

    @Column(nullable = false)
    private Integer nrBathrooms;

    @Column(nullable = false)
    private Integer nrSingleBeds;

    @Column(nullable = false)
    private Integer nrDoubleBeds;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Float price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(columnDefinition = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(columnDefinition = "category_id")
    @JsonIgnore
    private CategoryEntity category;

    @ManyToMany
    @JoinTable(
            name = "lodging_utilities",
            joinColumns = { @JoinColumn(name = "lodging_offer_id") },
            inverseJoinColumns = { @JoinColumn(name = "lodging_utility_id") }

    )
    private Set<LodgingOfferUtilityEntity> utilities = new HashSet<>();

    public LodgingOfferEntity() {
    }

    public LodgingOfferEntity(Integer nrRooms, Integer nrBathrooms, Integer nrSingleBeds,
                              Integer nrDoubleBeds, Integer floor, Float price, com.travelTim.currency.Currency currency,
                              String description, Set<LodgingOfferUtilityEntity> utilities) {
        this.nrRooms = nrRooms;
        this.nrBathrooms = nrBathrooms;
        this.nrSingleBeds = nrSingleBeds;
        this.nrDoubleBeds = nrDoubleBeds;
        this.floor = floor;
        this.price = price;
        this.currency = currency;
        this.description = description;
        this.utilities = utilities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNrRooms() {
        return nrRooms;
    }

    public void setNrRooms(Integer nrRooms) {
        this.nrRooms = nrRooms;
    }

    public Integer getNrBathrooms() {
        return nrBathrooms;
    }

    public void setNrBathrooms(Integer nrBathrooms) {
        this.nrBathrooms = nrBathrooms;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public com.travelTim.currency.Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<LodgingOfferUtilityEntity> getUtilities() {
        return utilities;
    }

    public void setUtilities(Set<LodgingOfferUtilityEntity> utilities) {
        this.utilities = utilities;
    }

    public void addUtility(LodgingOfferUtilityEntity utility) {
        this.utilities.add(utility);
    }

    public UserDetailsDTO getUser() {
        UserDTOMapper mapper = new UserDTOMapper();
        return  mapper.mapUserToUserDetailsDTO(this.user);
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
