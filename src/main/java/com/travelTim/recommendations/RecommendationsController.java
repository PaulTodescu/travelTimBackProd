package com.travelTim.recommendations;

import com.travelTim.activities.ActivityOfferDTO;
import com.travelTim.attractions.AttractionOfferDTO;
import com.travelTim.food.FoodOfferDTO;
import com.travelTim.lodging.LodgingOfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/recommendations")
public class RecommendationsController {

    private final RecommendationsService recommendationsService;

    @Autowired
    public RecommendationsController(RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }

    @GetMapping("/lodging/business-type")
    public ResponseEntity<List<LodgingOfferDTO>> getRecommendedLodgingOffersForBusinesses() throws IOException {
        List<LodgingOfferDTO> offers = this.recommendationsService.mapRecommendedLodgingOffers(
                this.recommendationsService.getRecommendedLodgingOffersForBusinesses());
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/lodging/user-type")
    public ResponseEntity<List<LodgingOfferDTO>> getRecommendedLodgingOffersForUsers() throws IOException {
        List<LodgingOfferDTO> offers = this.recommendationsService.mapRecommendedLodgingOffers(
                this.recommendationsService.getRecommendedLodgingOffersForUsers());
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/food")
    public ResponseEntity<List<FoodOfferDTO>> getRecommendedFoodOffers() {
        List<FoodOfferDTO> offers = this.recommendationsService.getRecommendedFoodOffers();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/attractions/business-type")
    public ResponseEntity<List<AttractionOfferDTO>> getRecommendedAttractionOffersForBusinesses() {
        List<AttractionOfferDTO> offers = this.recommendationsService.mapRecommendedAttractionOffers(
                this.recommendationsService.getRecommendedAttractionOffersForBusinesses()
        );
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/attractions/user-type")
    public ResponseEntity<List<AttractionOfferDTO>> getRecommendedAttractionOffersForUsers() {
        List<AttractionOfferDTO> offers = this.recommendationsService.mapRecommendedAttractionOffers(
                this.recommendationsService.getRecommendedAttractionOffersForUsers()
        );
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/activities/business-type")
    public ResponseEntity<List<ActivityOfferDTO>> getRecommendedActivityOffersForBusinesses() {
        List<ActivityOfferDTO> offers = this.recommendationsService.mapRecommendedActivityOffers(
                this.recommendationsService.getRecommendedActivityOffersForBusinesses()
        );
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/activities/user-type")
    public ResponseEntity<List<ActivityOfferDTO>> getRecommendedActivityOffersForUsers() {
        List<ActivityOfferDTO> offers = this.recommendationsService.mapRecommendedActivityOffers(
                this.recommendationsService.getRecommendedActivityOffersForUsers()
        );
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

}
