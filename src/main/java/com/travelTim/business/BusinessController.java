package com.travelTim.business;

import com.travelTim.currency.Currency;
import com.travelTim.lodging.LegalPersonLodgingOfferDetailsDTO;
import com.travelTim.lodging.LegalPersonLodgingOfferEntity;
import com.travelTim.lodging.LodgingOfferEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
            @PathVariable("businessId") Long businessId,
            @RequestParam(value = "currency") Currency currency) throws IOException {
        List<LegalPersonLodgingOfferDetailsDTO> offers = this.businessService.getLodgingOffers(businessId, currency);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

}
