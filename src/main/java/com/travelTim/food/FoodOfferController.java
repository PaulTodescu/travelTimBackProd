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

    @PutMapping(path = "/{foodOfferId}/menu")
    public ResponseEntity<Void> addFoodMenu(
            @PathVariable("foodOfferId") Long foodOfferId,
            @RequestBody Map<FoodMenuCategory, List<FoodMenuItem>> foodMenu){
        this.foodOfferService.addFoodOfferMenu(foodOfferId, foodMenu);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/{offerId}")
    public ResponseEntity<Void> editFoodOffer(
            @PathVariable("offerId") Long offerId,
            @RequestBody FoodOfferEditDTO offerToSave){
        this.foodOfferService.editFoodOffer(offerToSave, offerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{offerId}")
    public ResponseEntity<FoodOfferEntity> findFoodOfferById(
            @PathVariable("offerId") Long offerId) {
        FoodOfferEntity offer = this.foodOfferService.findFoodOfferById(offerId);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{offerId}")
    public ResponseEntity<Void> deleteFoodOffer(@PathVariable("offerId") Long offerId){
        this.foodOfferService.deleteFoodOffer(offerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/edit/get/{offerId}")
    public ResponseEntity<FoodOfferEditDTO> findFoodOfferForEdit(
            @PathVariable("offerId") Long offerId){
        FoodOfferEditDTO offer = this.foodOfferService.findFoodOfferForEdit(offerId);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

}
