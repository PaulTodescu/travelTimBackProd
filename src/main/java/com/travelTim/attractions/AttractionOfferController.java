package com.travelTim.attractions;

import com.travelTim.food.FoodOfferEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/attraction")
public class AttractionOfferController {

    private final AttractionOfferService attractionOfferService;

    @Autowired
    public AttractionOfferController(AttractionOfferService attractionOfferService) {
        this.attractionOfferService = attractionOfferService;
    }

    @PostMapping
    public ResponseEntity<Long> addAttractionOffer(@RequestBody AttractionOfferEntity attractionOffer){
        Long attractionId = this.attractionOfferService.addAttractionOffer(attractionOffer);
        return new ResponseEntity<>(attractionId, HttpStatus.CREATED);
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<AttractionOfferEntity> findAttractionOfferById(
            @PathVariable("offerId") Long offerId) {
        AttractionOfferEntity offer = this.attractionOfferService.findAttractionOfferById(offerId);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }
}
