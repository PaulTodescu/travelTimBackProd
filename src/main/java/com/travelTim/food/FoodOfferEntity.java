package com.travelTim.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelTim.business.BusinessEntity;
import com.travelTim.category.CategoryEntity;
import com.travelTim.user.UserContactDTO;
import com.travelTim.user.UserEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "food_offer")
public class FoodOfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(columnDefinition = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "business_id", unique = true, nullable = false)
    private BusinessEntity business;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(columnDefinition = "category_id")
    @JsonIgnore
    private CategoryEntity category;

    @ManyToMany
    @JoinTable(
            name = "food_offer_menu_category",
            joinColumns = { @JoinColumn(name = "food_offer_id")},
            inverseJoinColumns = { @JoinColumn(name = "food_menu_category_id")}
    )
    private Set<FoodMenuCategory> foodMenuCategories = new HashSet<>();


    public FoodOfferEntity() {
    }

    public FoodOfferEntity(BusinessEntity business, String description) {
        this.business = business;
        this.description = description;
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

    public Set<FoodMenuCategory> getFoodMenuCategories() {
        return foodMenuCategories;
    }

    public void setFoodMenuCategories(Set<FoodMenuCategory> foodMenuCategories) {
        this.foodMenuCategories = foodMenuCategories;
    }

    public void addFoodMenuCategory(FoodMenuCategory menuCategory){
        this.foodMenuCategories.add(menuCategory);
    }

    public UserContactDTO getUser() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this.user, UserContactDTO.class);
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

    @PreRemove // remove food menu prior to removing food offer
    public void preRemove(){
        for (Iterator<FoodMenuCategory> iterator1 = this.foodMenuCategories.iterator(); iterator1.hasNext();){
            FoodMenuCategory category = iterator1.next();
            for (Iterator<FoodMenuItem> iterator2 = category.getFoodMenuItems().iterator(); iterator2.hasNext();){
                iterator2.next();
                iterator2.remove();
            }
            iterator1.remove();
        }
    }

}
