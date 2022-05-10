package com.travelTim.attractions;

import com.travelTim.ticket.TicketEntity;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AttractionOfferForBusinessPageDTO {

    private Long id;
    private String title;
    private Set<TicketEntity> tickets;
    private String image;

    public AttractionOfferForBusinessPageDTO() {
    }

    public AttractionOfferForBusinessPageDTO(Long id, String title,
                                             Set<TicketEntity> tickets) {
        this.id = id;
        this.title = title;
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

    public Set<TicketEntity> getTickets() {
        return tickets.stream()
                .sorted(Comparator.comparing(TicketEntity::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void setTickets(Set<TicketEntity> tickets) {
        this.tickets = tickets;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
