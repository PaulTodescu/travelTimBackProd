package com.travelTim.reservation;

import com.travelTim.email.EmailService;
import com.travelTim.location.City;
import com.travelTim.lodging.LegalPersonLodgingOfferEntity;
import com.travelTim.lodging.LodgingOfferEntity;
import com.travelTim.lodging.LodgingOfferService;
import com.travelTim.lodging.PhysicalPersonLodgingOfferEntity;
import com.travelTim.offer.OfferStatus;
import com.travelTim.user.UserEntity;
import com.travelTim.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class OfferReservationService {

    private final OfferReservationDAO offerReservationDAO;
    private final UserService userService;
    private final LodgingOfferService lodgingService;
    private final EmailService emailService;

    @Autowired
    public OfferReservationService(OfferReservationDAO offerReservationDAO, UserService userService,
                                   LodgingOfferService lodgingService, EmailService emailService) {
        this.offerReservationDAO = offerReservationDAO;
        this.userService = userService;
        this.lodgingService = lodgingService;
        this.emailService = emailService;
    }

    public void addReservation(Long userId, Long offerId, OfferReservationEntity reservation){
        UserEntity user = this.userService.findUserById(userId);
        LodgingOfferEntity offer = this.lodgingService.findLodgingOfferEntityById(offerId);
        if (offer.getStatus() != OfferStatus.active){
            throw  new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Offer is currently reserved, cannot make reservation");
        }
        user.getReservations().add(reservation);
        reservation.setUser(user);
        offer.setReservation(reservation);
        offer.setStatus(OfferStatus.reserved);
        reservation.setLodgingOffer(offer);

        String providerName;
        String providerPhone;
        String reservationPhoneNumber;
        String arrivalTime;

        if (offer.getOfferContact().getPhoneNumber() == null || offer.getOfferContact().getPhoneNumber().length() == 0){
            providerPhone = "Not Provided";
        } else {
            providerPhone = offer.getOfferContact().getPhoneNumber();
        }
        if (reservation.getPhoneNumber() == null || reservation.getPhoneNumber().length() == 0){
            reservationPhoneNumber = "Not Provided";
        } else {
            reservationPhoneNumber = reservation.getPhoneNumber();
        }
        if (reservation.getArrivalTime() == null || reservation.getArrivalTime().length() == 0){
            arrivalTime = "Not Provided";
        } else {
            arrivalTime = reservation.getArrivalTime();
        }

        String title;
        String address;
        City city;

        if (offer instanceof LegalPersonLodgingOfferEntity){
            providerName = ((LegalPersonLodgingOfferEntity) offer).getBusiness().getName();
            title = ((LegalPersonLodgingOfferEntity) offer).getBusiness().getName();
            address = ((LegalPersonLodgingOfferEntity) offer).getBusiness().getAddress();
            city = ((LegalPersonLodgingOfferEntity) offer).getBusiness().getCity();
        } else {
            providerName = offer.getUser().getLastName() + " " + offer.getUser().getFirstName();
            title = ((PhysicalPersonLodgingOfferEntity) offer).getTitle();
            address = ((PhysicalPersonLodgingOfferEntity) offer).getAddress();
            city = ((PhysicalPersonLodgingOfferEntity) offer).getCity();
        }

        try {
            this.emailService.sendEmail(reservation.getEmail(), "TravelTim - Reservation Details",
                    "<div style=\"background-color: #034953; padding: 10px; margin-bottom: 20px; border-radius: 7px\">\n" +
                            "   <h1 style=\"color: #ffffff; font-weight: bold; font-family: Trebuchet MS\">\n" +
                            "       TravelTim\n" +
                            "   </h1>\n" +
                            "</div>  \n" +
                            "<div style=\"text-align: center\">\n" +
                            "   <h2 style=\"margin: 30px 15px; font-family: Helvetica, sans-serif; color: #000000\">\n" +
                            "       Hi " + reservation.getFirstName() + ", thank you for your reservation !\n" +
                            "   </h2>\n" +
                            "</div>\n" +
                            "<div style=\"text-align: center; font-family: Bahnschrift\">\n" +
                            "   <h3 style=\"margin: 15px; color: #666565;\">\n" +
                            "       Your reservation has been made. Please verify your details below and contact the provider for more details or in case of an error.\n" +
                            "   </h3>\n" +
                            "</div>\n" +
                            "<div style=\"margin: 30px auto 0 auto;\">\n" +
                            "   <table style=\"width: 700px; margin: 0 auto; border: none; border-collapse: collapse;\">\n" +
                            "       <tr style=\"border: none; background-color: #e1e2e3;\">\n" +
                            "       <th style=\"padding: 15px 8px; text-align: left; border: none; font-weight: bold; font-family: Bahnschrift; font-size: 18px;\">Reservation Details #</th>\n" +
                            "       <td style=\"padding: 15px 8px; text-align: left; border: none; font-weight: bold; font-family: Bahnschrift; font-size: 16px;\">" + this.getFormattedDate(reservation.getCreatedAt()) + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Arrival Date</th>\n" +
                            "       <td style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">"+ reservation.getArrivalDate() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Arrival Time</th>\n" +
                            "       <td style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + arrivalTime + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Departure Date</th>\n" +
                            "       <td style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + reservation.getDepartureDate() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Name</th>\n" +
                            "       <td style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + reservation.getLastName() + " " + reservation.getFirstName() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Email</th>\n" +
                            "       <td style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + reservation.getEmail() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Phone</th>\n" +
                            "       <td style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + reservationPhoneNumber + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none; background-color: #e1e2e3;\">\n" +
                            "       <th style=\"padding: 15px 8px; text-align: left; border: none; font-weight: bold; font-family: Bahnschrift; font-size: 18px;\">Pricing</th>\n" +
                            "       <td></td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 20px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Total Price</th>\n" +
                            "       <td style=\"padding: 20px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + reservation.getTotalPrice() + " " + reservation.getCurrency() + " (/" + reservation.getNrNights() + " nights)</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none; background-color: #e1e2e3;\">\n" +
                            "       <th style=\"padding: 15px 8px; text-align: left; border: none; font-weight: bold; font-family: Bahnschrift; font-size: 18px;\">Offer</th>\n" +
                            "       <td></td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Nr. Rooms</th>\n" +
                            "       <td style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + offer.getNrRooms() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Nr. Bathrooms</th>\n" +
                            "       <td style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + offer.getNrBathrooms() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Nr. Single Beds</th>\n" +
                            "       <td style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + offer.getNrSingleBeds() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Nr. Double Beds</th>\n" +
                            "       <td style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + offer.getNrDoubleBeds() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Floor</th>\n" +
                            "       <td style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + offer.getFloor() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none; background-color: #e1e2e3;\">\n" +
                            "       <th style=\"padding: 15px 8px; text-align: left; border: none; font-weight: bold; font-family: Bahnschrift; font-size: 18px;\">Address</th>\n" +
                            "       <td></td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Address</th>\n" +
                            "       <td style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + address + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">City</th>\n" +
                            "       <td style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + city + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none; background-color: #e1e2e3;\">\n" +
                            "       <th style=\"padding: 15px 8px; text-align: left; border: none; font-weight: bold; font-family: Bahnschrift; font-size: 18px;\">Provider Contact</th>\n" +
                            "       <td></td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none; background-color: #e1e2e3;\">\n" +
                            "       <th style=\"padding: 15px 8px; text-align: left; border: none; font-weight: bold; font-family: Bahnschrift; font-size: 18px;\">Provider Contact</th>\n" +
                            "       <td></td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Name</th>\n" +
                            "       <td style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + providerName + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Phone</th>\n" +
                            "       <td style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + providerPhone + "</td>\n" +
                            "       </tr>\n" +
                            "   </table>\n" +
                            "</div>");
            this.emailService.sendEmail(offer.getOfferContact().getEmail(), "TravelTim - New Reservation Received",
                    "<div style=\"background-color: #034953; padding: 10px; margin-bottom: 20px; border-radius: 7px\">\n" +
                            "   <h1 style=\"color: #ffffff; font-weight: bold; font-family: Trebuchet MS\">\n" +
                            "       TravelTim\n" +
                            "   </h1>\n" +
                            "</div>  \n" +
                            "<div style=\"text-align: center\">\n" +
                            "   <h2 style=\"margin: 30px 15px; font-family: Helvetica, sans-serif; color: #000000\">\n" +
                            "       Hi " + offer.getUser().getFirstName() + ", you received a new reservation for <span style=\"color: #7c795d;\">" + title + "</span>!\n" +
                            "   </h2>\n" +
                            "</div>\n" +
                            "<div style=\"text-align: center; font-family: Bahnschrift\">\n" +
                            "   <h3 style=\"margin: 15px; color: #666565;\">\n" +
                            "       Please verify the details below and contact the client for more information or in case of an error.\n" +
                            "   </h3>\n" +
                            "</div>\n" +
                            "<div style=\"margin: 30px auto 0 auto;\">\n" +
                            "   <table style=\"width: 700px; margin: 0 auto; border: none; border-collapse: collapse;\">\n" +
                            "       <tr style=\"border: none; background-color: #e1e2e3;\">\n" +
                            "       <th style=\"padding: 15px 8px; text-align: left; border: none; font-weight: bold; font-family: Bahnschrift; font-size: 18px;\">Reservation Details #</th>\n" +
                            "       <td style=\"padding: 15px 8px; text-align: left; border: none; font-weight: bold; font-family: Bahnschrift; font-size: 16px;\">" + this.getFormattedDate(reservation.getCreatedAt()) + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Arrival Date</th>\n" +
                            "       <td style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">"+ reservation.getArrivalDate() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Arrival Time</th>\n" +
                            "       <td style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + arrivalTime + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Departure Date</th>\n" +
                            "       <td style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + reservation.getDepartureDate() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Name</th>\n" +
                            "       <td style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + reservation.getLastName() + " " + reservation.getFirstName() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Email</th>\n" +
                            "       <td style=\"padding: 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + reservation.getEmail() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Phone</th>\n" +
                            "       <td style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + reservationPhoneNumber + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none; background-color: #e1e2e3;\">\n" +
                            "       <th style=\"padding: 15px 8px; text-align: left; border: none; font-weight: bold; font-family: Bahnschrift; font-size: 18px;\">Pricing</th>\n" +
                            "       <td></td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 20px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Total Price</th>\n" +
                            "       <td style=\"padding: 20px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + reservation.getTotalPrice() + " " + reservation.getCurrency() + " (/" + reservation.getNrNights() + " nights)</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none; background-color: #e1e2e3;\">\n" +
                            "       <th style=\"padding: 15px 8px; text-align: left; border: none; font-weight: bold; font-family: Bahnschrift; font-size: 18px;\">Offer</th>\n" +
                            "       <td></td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Nr. Rooms</th>\n" +
                            "       <td style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + offer.getNrRooms() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Nr. Bathrooms</th>\n" +
                            "       <td style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + offer.getNrBathrooms() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Nr. Single Beds</th>\n" +
                            "       <td style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + offer.getNrSingleBeds() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Nr. Double Beds</th>\n" +
                            "       <td style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + offer.getNrDoubleBeds() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Floor</th>\n" +
                            "       <td style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + offer.getFloor() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none; background-color: #e1e2e3;\">\n" +
                            "       <th style=\"padding: 15px 8px; text-align: left; border: none; font-weight: bold; font-family: Bahnschrift; font-size: 18px;\">Address</th>\n" +
                            "       <td></td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Address</th>\n" +
                            "       <td style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + address + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">City</th>\n" +
                            "       <td style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + city + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none; background-color: #e1e2e3;\">\n" +
                            "       <th style=\"padding: 15px 8px; text-align: left; border: none; font-weight: bold; font-family: Bahnschrift; font-size: 18px;\">Provider Contact</th>\n" +
                            "       <td></td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Name</th>\n" +
                            "       <td style=\"padding: 20px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + providerName + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Email</th>\n" +
                            "       <td style=\"padding: 8px 8px 8px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + offer.getOfferContact().getEmail() + "</td>\n" +
                            "       </tr>\n" +
                            "       <tr style=\"border: none;\">\n" +
                            "       <th style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">Phone</th>\n" +
                            "       <td style=\"padding: 8px 8px 20px 8px; text-align: left; border: none; max-width: 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">" + providerPhone + "</td>\n" +
                            "       </tr>\n" +
                            "   </table>\n" +
                            "</div>");
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not send email");
        }
        this.offerReservationDAO.save(reservation);
    }

    public OfferReservationEntity findReservationById(Long reservationId) {
        return this.offerReservationDAO.findOfferReservationEntityById(reservationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Reservation with id: " + reservationId + " was not found")
        );
    }

    public List<OfferReservationDTO> getReservationsForUser() {
        UserEntity user = this.userService.findLoggedInUser();
        Set<OfferReservationEntity> reservations = user.getReservations();
        List<OfferReservationDTO> reservationDTOs = new ArrayList<>();
        for (OfferReservationEntity reservation: reservations) {
            OfferReservationDTO reservationDTO = new OfferReservationDTO();
            reservationDTO.setId(reservation.getId());
            reservationDTO.setDate(this.getFormattedDate(reservation.getCreatedAt()));
            if (reservation.getLodgingOffer() instanceof LegalPersonLodgingOfferEntity) {
                reservationDTO.setTitle(((LegalPersonLodgingOfferEntity) reservation.getLodgingOffer()).getBusiness().getName());
            } else {
                reservationDTO.setTitle(((PhysicalPersonLodgingOfferEntity)reservation.getLodgingOffer()).getTitle());
            }
            reservationDTOs.add(reservationDTO);
        }
        return reservationDTOs.stream()
                .sorted(Comparator.comparing(OfferReservationDTO::getDate).reversed())
                .collect(Collectors.toList());
    }

    public String getFormattedDate(LocalDateTime dateTime) {
        LocalDate formattedDateTime = dateTime.toLocalDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formattedDateTime.format(dateFormatter);
    }

    public OfferReservationDetailsDTO getReservationDetails(Long reservationId) {
        OfferReservationEntity reservation = this.findReservationById(reservationId);
        OfferReservationDTOMapper mapper = new OfferReservationDTOMapper();
        return mapper.mapReservationToDetailsDTO(reservation);
    }

    public void deleteReservation(Long reservationId) {
        OfferReservationEntity reservation = this.findReservationById(reservationId);
        reservation.getLodgingOffer().setReservation(null);
        reservation.setLodgingOffer(null);
        reservation.getUser().getReservations().remove(reservation);
        reservation.setUser(null);
        this.offerReservationDAO.deleteOfferReservationEntityById(reservationId);
    }

}
