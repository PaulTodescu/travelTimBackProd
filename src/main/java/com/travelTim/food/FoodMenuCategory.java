package com.travelTim.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelTim.user.UserEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "food_menu_category")
public class FoodMenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "foodMenuCategories")
    @JsonIgnore
    private Set<FoodOfferEntity> foodOffers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "food_menu_category_item",
            joinColumns = { @JoinColumn(name = "food_menu_category_id")},
            inverseJoinColumns = { @JoinColumn(name = "food_menu_item_id")}
    )
    private Set<FoodMenuItem> foodMenuItems = new HashSet<>();

    public FoodMenuCategory(String name) {
        this.name = name;
    }

    public FoodMenuCategory() {
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

    public Set<FoodOfferEntity> getFoodOffers() {
        return foodOffers;
    }

    public void setFoodOffers(Set<FoodOfferEntity> foodOffers) {
        this.foodOffers = foodOffers;
    }

    public Set<FoodMenuItem> getFoodMenuItems() {
        return foodMenuItems;
    }

    public void setFoodMenuItems(Set<FoodMenuItem> foodMenuItems) {
        this.foodMenuItems = foodMenuItems;
    }

    public void removeFoodMenuItem(FoodMenuItem item){
        this.foodMenuItems.remove(item);
        item.getFoodMenuCategories().remove(this);
    }
}
