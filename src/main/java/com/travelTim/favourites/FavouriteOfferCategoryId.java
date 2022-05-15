package com.travelTim.favourites;

import com.travelTim.offer.OfferCategory;

public class FavouriteOfferCategoryId {

    private Long id;
    private OfferCategory category;

    public FavouriteOfferCategoryId() {
    }

    public FavouriteOfferCategoryId(Long id, OfferCategory category) {
        this.id = id;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OfferCategory getCategory() {
        return category;
    }

    public void setCategory(OfferCategory category) {
        this.category = category;
    }
}
