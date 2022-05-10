package com.travelTim.favourites;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(path = "/favourites")
public class FavouriteOffersController {

    private final FavouriteOffersService favouriteOffersService;

    @Autowired
    public FavouriteOffersController(FavouriteOffersService favouriteOffersService) {
        this.favouriteOffersService = favouriteOffersService;
    }

    @PutMapping(path = "/offer/{offerId}/user/{userId}/add")
    public ResponseEntity<Void> addOfferToFavourites(
            @PathVariable("offerId") Long offerId,
            @PathVariable("userId") Long userId,
            @RequestParam(value = "offerCategory") OfferCategory offerCategory) {
        this.favouriteOffersService.addOfferToFavourites(userId, offerId, offerCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/offer/{offerId}/user/{userId}/remove")
    public ResponseEntity<Void> removeOfferFromFavourites(
            @PathVariable("offerId") Long offerId,
            @RequestParam("offerCategory") OfferCategory offerCategory,
            @PathVariable("userId") Long userId) {
        this.favouriteOffersService.removeOfferFromFavourites(userId, offerId, offerCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<FavouriteOffer>> getFavouriteOffersForUser(
            @PathVariable("userId") Long userId) {
        List<FavouriteOffer> favouriteOffers =
                this.favouriteOffersService.getFavouriteOffersForUser(userId);
        return new ResponseEntity<>(favouriteOffers, HttpStatus.OK);
    }

    @GetMapping(path = "/user/{userId}/category-id")
    public ResponseEntity<Set<FavouriteOfferCategoryId>> getFavouriteOffersCategoryIdForUser(
            @PathVariable("userId") Long userId) {
        Set<FavouriteOfferCategoryId> favouriteOffers =
                this.favouriteOffersService.getFavouriteOffersCategoryIdForUser(userId);
        return new ResponseEntity<>(favouriteOffers, HttpStatus.OK);
    }

}
