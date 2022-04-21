package com.travelTim.lodging;

import com.travelTim.currency.Currency;

public class LodgingOfferPriceDTO {
    private Float price;
    private Currency currency;

    public LodgingOfferPriceDTO() {
    }

    public LodgingOfferPriceDTO(Float price, Currency currency) {
        this.price = price;
        this.currency = currency;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
