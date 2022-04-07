package com.travelTim.food;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelTim.lodging.LodgingOfferEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "food_menu_item")
public class FoodMenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Double weight;

    @Column(nullable = false)
    private Double price;

    @ManyToMany(mappedBy = "foodMenuItems")
    @JsonIgnore
    private Set<FoodMenuCategory> foodMenuCategories = new HashSet<>();

    public FoodMenuItem() {
    }

    public FoodMenuItem(String name, Double weight, Double price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<FoodMenuCategory> getFoodMenuCategories() {
        return foodMenuCategories;
    }

    public void setFoodMenuCategories(Set<FoodMenuCategory> foodMenuCategories) {
        this.foodMenuCategories = foodMenuCategories;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }
}
