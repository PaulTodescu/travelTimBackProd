package com.travelTim.attractions;

import com.travelTim.business.BusinessEntity;
import com.travelTim.location.City;
import com.travelTim.ticket.TicketEntity;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AttractionOfferEditDTO {
    private Long id;
    private String title;
    private String address;
    private String description;
    private City city;
    private BusinessEntity business;
    private Set<TicketEntity> tickets;

    public AttractionOfferEditDTO() {
    }

    public AttractionOfferEditDTO(Long id, String title, String address,
                                  String description, City city, BusinessEntity business,
                                  Set<TicketEntity> tickets) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.description = description;
        this.city = city;
        this.business = business;
        this.tickets = tickets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public BusinessEntity getBusiness() {
        return business;
    }

    public void setBusiness(BusinessEntity business) {
        this.business = business;
    }

    public Set<TicketEntity> getTickets() {
        return tickets.stream()
                .sorted(Comparator.comparing(TicketEntity::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void setTickets(Set<TicketEntity> tickets) {
        this.tickets = tickets;
    }
}
