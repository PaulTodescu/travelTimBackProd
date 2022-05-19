package com.travelTim.reservation;

import com.travelTim.lodging.LodgingOfferDetailsForReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/reservation")
public class OfferReservationController {

    private final OfferReservationService reservationService;


    @Autowired
    public OfferReservationController(OfferReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(path = "/user/{userId}/offer/{offerId}")
    public ResponseEntity<Void> addReservation(
            @PathVariable("userId") Long userId,
            @PathVariable("offerId") Long offerId,
            @RequestBody OfferReservationEntity reservation) {
        this.reservationService.addReservation(userId, offerId, reservation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/user/all")
    public ResponseEntity<List<OfferReservationDTO>> getReservationsForUser() {
        List<OfferReservationDTO> reservations = this.reservationService.getReservationsForUser();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") Long reservationId){
        this.reservationService.deleteReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{reservationId}/details")
    public ResponseEntity<OfferReservationDetailsDTO> getReservationDetails(
            @PathVariable("reservationId") Long reservationId) {
        OfferReservationDetailsDTO reservation = this.reservationService.getReservationDetails(reservationId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @GetMapping(path = "/{reservationId}/offer/details")
    public ResponseEntity<LodgingOfferDetailsForReservationDTO> getLodgingOfferDetailsForReservation(
            @PathVariable("reservationId") Long reservationId){
        LodgingOfferDetailsForReservationDTO offerDetails = this.reservationService.getLodgingOfferDetailsForReservation(reservationId);
        return new ResponseEntity<>(offerDetails, HttpStatus.OK);
    }

}
