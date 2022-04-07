package com.travelTim.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/food")
public class FoodOfferController {

    private final FoodOfferService foodOfferService;

    @Autowired
    public FoodOfferController(FoodOfferService foodOfferService) {
        this.foodOfferService = foodOfferService;
    }

    @PostMapping
    public ResponseEntity<Long> addFoodOffer(@RequestBody FoodOfferEntity foodOffer){
        Long foodOfferId = this.foodOfferService.addFoodOffer(foodOffer);
        return new ResponseEntity<>(foodOfferId, HttpStatus.CREATED);
    }

    @PutMapping("/{foodOfferId}/menu")
    public ResponseEntity<FoodOfferEntity> addFoodMenu(
            @PathVariable("foodOfferId") Long foodOfferId,
            @RequestBody Map<FoodMenuCategory, List<FoodMenuItem>> foodMenu){
        this.foodOfferService.addFoodOfferMenu(foodOfferId, foodMenu);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<FoodOfferEntity> findFoodOfferById(
            @PathVariable("offerId") Long offerId) {
        FoodOfferEntity offer = this.foodOfferService.findFoodOfferById(offerId);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

}
