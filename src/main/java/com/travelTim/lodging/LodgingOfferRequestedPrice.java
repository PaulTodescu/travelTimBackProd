package com.travelTim.lodging;

import javax.persistence.*;

@Entity
@Table(name = "requested_lodging_offer_price")
public class LodgingOfferRequestedPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Float price;

    public LodgingOfferRequestedPrice() {
    }

    public LodgingOfferRequestedPrice(Float price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
