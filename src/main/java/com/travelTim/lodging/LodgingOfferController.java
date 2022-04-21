package com.travelTim.lodging;

import com.travelTim.currency.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/lodging")
public class LodgingOfferController {

    private final LodgingOfferService lodgingOfferService;

    @Autowired
    public LodgingOfferController(LodgingOfferService lodgingOfferService) {
        this.lodgingOfferService = lodgingOfferService;
    }

    @PostMapping("/physical")
    public ResponseEntity<Long> addPhysicalPersonLodgingOffer (
            @RequestBody PhysicalPersonLodgingOfferEntity lodgingOffer){
        Long offerId = this.lodgingOfferService.addPhysicalPersonLodgingOffer(lodgingOffer);
        return new ResponseEntity<>(offerId, HttpStatus.CREATED);
    }

    @PostMapping("/legal")
    public ResponseEntity<Long> addLegalPersonLodgingOffer (
            @RequestBody LegalPersonLodgingOfferEntity lodgingOffer){
        Long offerId = this.lodgingOfferService.addLegalPersonLodgingOffer(lodgingOffer);
        return new ResponseEntity<>(offerId, HttpStatus.CREATED);
    }

    @GetMapping(path = "/physical/{offerId}")
    public ResponseEntity<LodgingOfferEntity> findPhysicalPersonLodgingOfferById(
            @PathVariable("offerId") Long offerId) {
        LodgingOfferEntity offer = this.lodgingOfferService.findPhysicalPersonLodgingOfferById(offerId);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @GetMapping(path = "/legal/{offerId}")
    public ResponseEntity<LegalPersonLodgingOfferBaseDetailsDTO> findLegalPersonLodgingOfferById(
            @PathVariable("offerId") Long offerId){
        LegalPersonLodgingOfferBaseDetailsDTO offer = this.lodgingOfferService.findLegalPersonLodgingOfferById(offerId);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @GetMapping("/{offerId}/price")
    public ResponseEntity<LodgingOfferPriceDTO> getLodgingOfferPrice(
            @PathVariable("offerId") Long offerId,
            @RequestParam(value = "currency") Currency currency){
        LodgingOfferPriceDTO offerPrice = this.lodgingOfferService.getLodgingOfferPrice(offerId, currency);
        return new ResponseEntity<>(offerPrice, HttpStatus.OK);
    }
}
