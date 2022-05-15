package com.travelTim.activities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelTim.business.BusinessEntity;
import com.travelTim.category.CategoryEntity;
import com.travelTim.contact.OfferContactEntity;
import com.travelTim.favourites.FavouriteOffersEntity;
import com.travelTim.location.City;
import com.travelTim.offer.OfferStatus;
import com.travelTim.ticket.TicketEntity;
import com.travelTim.user.UserContactDTO;
import com.travelTim.user.UserDTOMapper;
import com.travelTim.user.UserDetailsDTO;
import com.travelTim.user.UserEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "activity_offer")
public class ActivityOfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City city;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(columnDefinition = "user_id")
    private UserEntity user;

    @ManyToOne()
    @JoinColumn(columnDefinition = "business_id")
    private BusinessEntity business;

    @ManyToOne
    @JoinColumn(columnDefinition = "category_id")
    @JsonIgnore
    private CategoryEntity category;

    @ManyToMany
    @JoinTable(
            name = "activity_ticket",
            joinColumns = { @JoinColumn(name = "activity_offer_id")},
            inverseJoinColumns = { @JoinColumn(name = "ticket_id")}
    )
    private Set<TicketEntity> tickets = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "offer_contact_id")
    private OfferContactEntity offerContact;

    @ManyToMany(mappedBy = "activityOffers")
    @JsonIgnore
    private Set<FavouriteOffersEntity> favourites = new HashSet<>();

    public ActivityOfferEntity() {
    }

    public ActivityOfferEntity(String title, String address, City city, String description, Set<TicketEntity> tickets) {
        this.title = title;
        this.address = address;
        this.city = city;
        this.description = description;
        this.tickets = tickets;
    }

    public ActivityOfferEntity(String title, String address, City city, String description, BusinessEntity business, Set<TicketEntity> tickets) {
        this.title = title;
        this.address = address;
        this.city = city;
        this.description = description;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public UserDetailsDTO getUser() {
        UserDTOMapper mapper = new UserDTOMapper();
        return mapper.mapUserToUserDetailsDTO(this.user);
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    public void removeTicket(TicketEntity ticket){
        this.tickets.remove(ticket);
        ticket.getActivityOffers().remove(this);
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OfferContactEntity getOfferContact() {
        return offerContact;
    }

    public void setOfferContact(OfferContactEntity offerContact) {
        this.offerContact = offerContact;
    }

    public Set<FavouriteOffersEntity> getFavourites() {
        return favourites;
    }

    public void setFavourites(Set<FavouriteOffersEntity> favourites) {
        this.favourites = favourites;
    }
}
