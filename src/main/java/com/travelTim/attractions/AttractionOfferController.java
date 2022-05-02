package com.travelTim.attractions;

import com.travelTim.ticket.TicketEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/attraction")
public class AttractionOfferController {

    private final AttractionOfferService attractionOfferService;

    @Autowired
    public AttractionOfferController(AttractionOfferService attractionOfferService) {
        this.attractionOfferService = attractionOfferService;
    }

    @PostMapping()
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

    @DeleteMapping(path = "/{offerId}")
    public ResponseEntity<?> deleteAttractionOffer(@PathVariable("offerId") Long offerId){
        this.attractionOfferService.deleteAttractionOffer(offerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/edit/get/{offerId}")
    public ResponseEntity<AttractionOfferEditDTO> findAttractionOfferForEdit(
            @PathVariable("offerId") Long offerId){
        AttractionOfferEditDTO offer = this.attractionOfferService.findAttractionOfferForEdit(offerId);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @PutMapping(path = "/{offerId}")
    public ResponseEntity<Void> editAttractionOffer(
            @PathVariable("offerId") Long offerId,
            @RequestBody AttractionOfferEditDTO offerToSave){
        this.attractionOfferService.editAttractionOffer(offerId, offerToSave);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
