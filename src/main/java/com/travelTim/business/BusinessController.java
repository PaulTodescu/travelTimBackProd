package com.travelTim.business;

import com.travelTim.activities.ActivityOfferForBusinessPageDTO;
import com.travelTim.attractions.AttractionOfferForBusinessPageDTO;
import com.travelTim.food.FoodOfferIdMenuImageDTO;
import com.travelTim.lodging.LegalPersonLodgingOfferDTO;
import com.travelTim.lodging.LegalPersonLodgingOfferDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/business")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping()
    public ResponseEntity<Long> addBusiness(@RequestBody BusinessEntity business){
        Long businessId = this.businessService.addBusiness(business);
        return new ResponseEntity<>(businessId, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{businessId}")
    public ResponseEntity<BusinessEntity> findBusinessById(@PathVariable("businessId") Long businessId){
        BusinessEntity job = this.businessService.findBusinessById(businessId);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @PutMapping(path = "/{businessId}/schedule")
    public ResponseEntity<Void> addBusinessSchedule(
            @PathVariable("businessId") Long businessId,
            @RequestBody Set<BusinessDaySchedule> schedule){
        this.businessService.addSchedule(businessId, schedule);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{businessId}")
    public ResponseEntity<BusinessEntity> editBusiness(
            @RequestBody BusinessEntity business,
            @PathVariable("businessId") Long businessId){
        this.businessService.editBusiness(business, businessId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<BusinessEntity>> getAllBusinesses(){
        List<BusinessEntity> businesses = this.businessService.getAllBusinesses();
        return new ResponseEntity<>(businesses, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{businessId}")
    public ResponseEntity<?> deleteBusiness(@PathVariable("businessId") Long businessId){
        this.businessService.deleteBusiness(businessId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{businessId}/foodOffer/check")
    public ResponseEntity<Boolean> checkIfBusinessHasFoodOffer(@PathVariable("businessId") Long businessId){
        Boolean check = this.businessService.checkIfBusinessHasFoodOffer(businessId);
        return new ResponseEntity<>(check, HttpStatus.OK);
    }

    @GetMapping(path = "/{businessId}/offers/lodging")
    public ResponseEntity<List<LegalPersonLodgingOfferDetailsDTO>> getLodgingOffers(
            @PathVariable("businessId") Long businessId) throws IOException {
        List<LegalPersonLodgingOfferDetailsDTO> offers = this.businessService.getLegalLodgingOffers(businessId);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping(path = "/{businessId}/offers/lodging/ids")
    public ResponseEntity<Set<Long>> getLodgingOffersIDs(
            @PathVariable("businessId") Long businessId) {
        Set<Long> offerIDs = this.businessService.getLodgingOffersIDs(businessId);
        return new ResponseEntity<>(offerIDs, HttpStatus.OK);
    }

    @GetMapping(path = "/{businessId}/offers/lodging/business-page")
    public ResponseEntity<List<LegalPersonLodgingOfferDTO>> getLodgingOffersForBusinessPage(
            @PathVariable("businessId") Long businessId) throws IOException {
        List<LegalPersonLodgingOfferDTO> offers = this.businessService.getLodgingOffersForBusinessPage(businessId);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping(path = "/{businessId}/offers/food")
    public ResponseEntity<FoodOfferIdMenuImageDTO> getFoodOffer(
            @PathVariable("businessId") Long businessId) {
        FoodOfferIdMenuImageDTO offer = this.businessService.getFoodOfferIdMenuImage(businessId);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @GetMapping(path = "/{businessId}/offers/attractions")
    public ResponseEntity<List<AttractionOfferForBusinessPageDTO>> getAttractionOffers(
            @PathVariable("businessId") Long businessId) {
        List<AttractionOfferForBusinessPageDTO> offers = this.businessService.getAttractionOffers(businessId);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping(path = "/{businessId}/offers/activities")
    public ResponseEntity<List<ActivityOfferForBusinessPageDTO>> getActivityOffers(
            @PathVariable("businessId") Long businessId) {
        List<ActivityOfferForBusinessPageDTO> offers = this.businessService.getActivityOffers(businessId);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }


}
