package com.travelTim.reservation;

import com.travelTim.lodging.LodgingOfferEntity;
import com.travelTim.lodging.LodgingOfferService;
import com.travelTim.offer.OfferStatus;
import com.travelTim.user.UserEntity;
import com.travelTim.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OfferReservationService {

    private final OfferReservationDAO offerReservationDAO;
    private final UserService userService;
    private final LodgingOfferService lodgingService;

    @Autowired
    public OfferReservationService(OfferReservationDAO offerReservationDAO, UserService userService, LodgingOfferService lodgingService) {
        this.offerReservationDAO = offerReservationDAO;
        this.userService = userService;
        this.lodgingService = lodgingService;
    }

    public void addReservation(Long userId, Long offerId, OfferReservation reservation){
        UserEntity user = this.userService.findUserById(userId);
        LodgingOfferEntity offer = this.lodgingService.findLodgingOfferEntityById(offerId);
        if (!user.getReservations().contains(reservation)) {
            user.getReservations().add(reservation);
            reservation.setUser(user);
        }
        offer.setReservation(reservation);
        offer.setStatus(OfferStatus.reserved);
        reservation.setLodgingOffer(offer);
        this.offerReservationDAO.save(reservation);
    }


}
