package com.travelTim.attractions;

import com.travelTim.category.CategoryEntity;
import com.travelTim.category.CategoryService;
import com.travelTim.category.CategoryType;
import com.travelTim.files.ImageUtils;
import com.travelTim.lodging.LodgingOfferUtilityEntity;
import com.travelTim.ticket.TicketDAO;
import com.travelTim.ticket.TicketEntity;
import com.travelTim.user.UserEntity;
import com.travelTim.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
@Transactional
public class AttractionOfferService {

    private final AttractionOfferDAO attractionOfferDAO;
    private final TicketDAO ticketDAO;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ImageUtils imageUtils;

    @Autowired
    public AttractionOfferService(AttractionOfferDAO attractionOfferDAO, TicketDAO ticketDAO,
                                  UserService userService, CategoryService categoryService,
                                  ImageUtils imageUtils) {
        this.attractionOfferDAO = attractionOfferDAO;
        this.ticketDAO = ticketDAO;
        this.userService = userService;
        this.categoryService = categoryService;
        this.imageUtils = imageUtils;
    }

    public Long addAttractionOffer(AttractionOfferEntity attractionOffer){
        UserEntity loggedInUser = this.userService.findLoggedInUser();
        attractionOffer.setUser(loggedInUser);
        CategoryEntity category = this.categoryService.findCategoryByName(CategoryType.attractions);
        attractionOffer.setCategory(category);
        this.addAttractionOfferTickets(attractionOffer, attractionOffer.getTickets());
        return this.attractionOfferDAO.save(attractionOffer).getId();
    }

    public void addAttractionOfferTickets(AttractionOfferEntity offer,
                                          Set<TicketEntity> ticketsToAdd){
        Set<TicketEntity> tickets = new HashSet<>();
        for (TicketEntity ticket: ticketsToAdd){
            TicketEntity ticketToSave;
            if (this.ticketDAO.findTicketEntityByNameAndPrice(ticket.getName(), ticket.getPrice()).isEmpty()){
                ticketToSave = this.ticketDAO.save(ticket);
            } else {
                ticketToSave = this.ticketDAO.findTicketEntityByNameAndPrice(ticket.getName(), ticket.getPrice()).get();
            }
            tickets.add(ticketToSave);
        }
        offer.setTickets(tickets);
        this.attractionOfferDAO.save(offer);
    }

    public AttractionOfferEntity findAttractionOfferById(Long offerId){
        return this.attractionOfferDAO.findAttractionsOfferEntityById(offerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Attraction offer with id: " + offerId + " was not found")
        );
    }

    public AttractionOfferEditDTO findAttractionOfferForEdit(Long offerId){
        AttractionOfferEntity offer = this.findAttractionOfferById(offerId);
        AttractionDTOMapper mapper = new AttractionDTOMapper();
        return mapper.mapAttractionOfferToEditDTO(offer);
    }

    public void editAttractionOffer(Long offerId, AttractionOfferEditDTO offerToSave){
        AttractionOfferEntity offer = this.findAttractionOfferById(offerId);
        offer.setBusiness(offerToSave.getBusiness());
        offer.setTitle(offerToSave.getTitle());
        offer.setAddress(offerToSave.getAddress());
        offer.setCity(offerToSave.getCity());
        offer.setDescription(offerToSave.getDescription());
        this.addAttractionOfferTickets(offer, offerToSave.getTickets());
        this.attractionOfferDAO.save(offer);
    }

    public void deleteAttractionOffer(Long offerId){
        AttractionOfferEntity offer = this.findAttractionOfferById(offerId);
        this.deleteOfferTickets(offer.getId());
        this.imageUtils.deleteOfferImages("attractions", offerId);
        this.attractionOfferDAO.deleteAttractionOfferEntityById(offerId);
    }

    public void deleteOfferTickets(Long offerId){
        AttractionOfferEntity offer = this.findAttractionOfferById(offerId);
        for (Iterator<TicketEntity> iterator = offer.getTickets().iterator(); iterator.hasNext();){
            TicketEntity ticket = iterator.next();
            ticket.getAttractionOffers().remove(offer);
            if (ticket.getAttractionOffers().size() == 0 && ticket.getActivityOffers().size() == 0){
                this.ticketDAO.deleteTicketEntityById(ticket.getId());
            }
            iterator.remove();
        }
    }
}
