package com.travelTim.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody OfferReservation reservation) {
        this.reservationService.addReservation(userId, offerId, reservation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
