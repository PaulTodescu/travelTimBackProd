package com.travelTim.lodging;

import com.travelTim.business.BusinessEntity;
import com.travelTim.currency.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @GetMapping(path = "/physical/edit/get/{offerId}")
    public ResponseEntity<PhysicalPersonLodgingOfferEditDTO> findPhysicalPersonLodgingOfferForEdit(
            @PathVariable("offerId") Long offerId) {
        PhysicalPersonLodgingOfferEditDTO offer = this.lodgingOfferService
                        .findPhysicalPersonLodgingOfferForEdit(offerId);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @PutMapping(path = "/physical/{offerId}")
    public ResponseEntity<Void> editPhysicalPersonLodgingOffer(
            @RequestBody PhysicalPersonLodgingOfferEditDTO offerToSave,
            @PathVariable("offerId") Long offerId){
        this.lodgingOfferService.editPhysicalPersonLodgingOffer(offerToSave, offerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/legal/{offerId}")
    public ResponseEntity<LegalPersonLodgingOfferBaseDetailsDTO> findLegalPersonLodgingOfferById(
            @PathVariable("offerId") Long offerId){
        LegalPersonLodgingOfferBaseDetailsDTO offer = this.lodgingOfferService.findLegalPersonLodgingOfferById(offerId);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @PutMapping(path = "/legal/{offerId}")
    public ResponseEntity<Void> editLegalPersonLodgingOffer(
            @RequestBody LegalPersonLodgingOfferEditDTO offerToSave,
            @PathVariable("offerId") Long offerId){
        this.lodgingOfferService.editLegalPersonLodgingOffer(offerToSave, offerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/legal/edit/get/{offerId}")
    public ResponseEntity<LegalPersonLodgingOfferEditDTO> findLegalPersonLodgingOfferForEdit(
            @PathVariable("offerId") Long offerId){
        LegalPersonLodgingOfferEditDTO offer = this.lodgingOfferService.findLegalPersonLodgingOfferForEdit(offerId);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{offerId}")
    public ResponseEntity<?> deleteLodgingOffer(@PathVariable("offerId") Long offerId){
        this.lodgingOfferService.deleteLodgingOffer(offerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
