package com.travelTim.reservation;

import com.travelTim.currency.Currency;
import com.travelTim.location.City;
import com.travelTim.lodging.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OfferReservationDetailsDTO {

    private String arrivalDate;
    private String arrivalTime;
    private String departureDate;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Long totalPrice;
    private Currency currency;
    private Integer nrNights;
    private LocalDateTime createdAt;
    private LodgingOfferEntity lodgingOffer;

    public OfferReservationDetailsDTO() {
    }

    public OfferReservationDetailsDTO(String arrivalDate, String arrivalTime, String departureDate,
                                      String firstName, String lastName, String email, String phoneNumber,
                                      Long totalPrice, Currency currency, Integer nrNights,
                                      LocalDateTime createdAt, LodgingOfferEntity lodgingOffer) {
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.departureDate = departureDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.totalPrice = totalPrice;
        this.currency = currency;
        this.nrNights = nrNights;
        this.createdAt = createdAt;
        this.lodgingOffer = lodgingOffer;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getNrNights() {
        return nrNights;
    }

    public void setNrNights(Integer nrNights) {
        this.nrNights = nrNights;
    }

    public String getCreatedAt() {
        LocalDate formattedDateTime = createdAt.toLocalDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formattedDateTime.format(dateFormatter);
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LodgingOfferForReservationDetailsDTO getLodgingOffer() {
        LodgingDTOMapper mapper = new LodgingDTOMapper();
        return mapper.mapLodgingOfferToReservationDetailsDTO(lodgingOffer);
    }

    public void setLodgingOffer(LodgingOfferEntity lodgingOffer) {
        this.lodgingOffer = lodgingOffer;
    }

    public String getProviderName() {
        if (this.lodgingOffer instanceof LegalPersonLodgingOfferEntity) {
            return ((LegalPersonLodgingOfferEntity) this.lodgingOffer).getBusiness().getName();
        } else {
            return ((PhysicalPersonLodgingOfferEntity) this.lodgingOffer).getUser().getLastName() + " " +
                    ((PhysicalPersonLodgingOfferEntity) this.lodgingOffer).getUser().getFirstName();
        }
    }

    public String getProviderEmail() {
        return this.lodgingOffer.getOfferContact().getEmail();
    }

    public String getProviderPhone() {
        return this.lodgingOffer.getOfferContact().getPhoneNumber();
    }

    public String getAddress() {
        if (this.lodgingOffer instanceof LegalPersonLodgingOfferEntity) {
            return ((LegalPersonLodgingOfferEntity) this.lodgingOffer).getBusiness().getAddress();
        } else {
            return ((PhysicalPersonLodgingOfferEntity) this.lodgingOffer).getAddress();
        }
    }

    public City getCity() {
        if (this.lodgingOffer instanceof LegalPersonLodgingOfferEntity) {
            return ((LegalPersonLodgingOfferEntity) this.lodgingOffer).getBusiness().getCity();
        } else {
            return ((PhysicalPersonLodgingOfferEntity) this.lodgingOffer).getCity();
        }
    }
}
