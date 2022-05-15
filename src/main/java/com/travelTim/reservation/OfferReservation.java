package com.travelTim.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelTim.currency.Currency;
import com.travelTim.lodging.LodgingOfferEntity;
import com.travelTim.user.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "offer_reservation")
public class OfferReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String arrivalDate;

    private String arrivalTime;

    @Column(nullable = false)
    private String departureDate;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    private String phoneNumber;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(columnDefinition = "user_id")
    @JsonIgnore
    private UserEntity user;

    @OneToOne(mappedBy = "reservation")
    @JsonIgnore
    private LodgingOfferEntity lodgingOffer;

    public OfferReservation() {
    }

    public OfferReservation(String arrivalDate, String arrivalTime, String departureDate, String firstName,
                            String lastName, String email, String phoneNumber, Long totalPrice, Currency currency) {
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.departureDate = departureDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.totalPrice = totalPrice;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LodgingOfferEntity getLodgingOffer() {
        return lodgingOffer;
    }

    public void setLodgingOffer(LodgingOfferEntity lodgingOffer) {
        this.lodgingOffer = lodgingOffer;
    }


}
