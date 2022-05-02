package com.travelTim.activities;

import com.travelTim.attractions.AttractionDTOMapper;
import com.travelTim.attractions.AttractionOfferEditDTO;
import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.category.CategoryEntity;
import com.travelTim.category.CategoryService;
import com.travelTim.category.CategoryType;
import com.travelTim.files.ImageUtils;
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
public class ActivityOfferService {

    private final ActivityOfferDAO activityOfferDAO;
    private final UserService userService;
    private final TicketDAO ticketDAO;
    private final CategoryService categoryService;
    private final ImageUtils imageUtils;

    @Autowired
    public ActivityOfferService(ActivityOfferDAO activityOfferDAO, UserService userService,
                                TicketDAO ticketDAO, CategoryService categoryService, ImageUtils imageUtils) {
        this.activityOfferDAO = activityOfferDAO;
        this.userService = userService;
        this.ticketDAO = ticketDAO;
        this.categoryService = categoryService;
        this.imageUtils = imageUtils;
    }

    public Long addActivityOffer(ActivityOfferEntity activityOffer) {
        UserEntity loggedInUser = this.userService.findLoggedInUser();
        activityOffer.setUser(loggedInUser);
        CategoryEntity category = this.categoryService.findCategoryByName(CategoryType.activities);
        activityOffer.setCategory(category);
        this.addActivityOfferTickets(activityOffer, activityOffer.getTickets());
        return this.activityOfferDAO.save(activityOffer).getId();
    }

    public void addActivityOfferTickets(ActivityOfferEntity offer,
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
        this.activityOfferDAO.save(offer);
    }

    public ActivityOfferEntity findActivityOfferById(Long offerId) {
        return this.activityOfferDAO.findActivityOfferEntityById(offerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Activity offer with id: " + offerId + " was not found")
        );
    }

    public ActivityOfferEditDTO findActivityOfferForEdit(Long offerId){
        ActivityOfferEntity offer = this.findActivityOfferById(offerId);
        ActivityDTOMapper mapper = new ActivityDTOMapper();
        return mapper.mapActivityOfferToEditDTO(offer);
    }

    public void editActivityOffer(Long offerId, ActivityOfferEditDTO offerToSave){
        ActivityOfferEntity offer = this.findActivityOfferById(offerId);
        offer.setBusiness(offerToSave.getBusiness());
        offer.setTitle(offerToSave.getTitle());
        offer.setAddress(offerToSave.getAddress());
        offer.setCity(offerToSave.getCity());
        offer.setDescription(offerToSave.getDescription());
        this.addActivityOfferTickets(offer, offerToSave.getTickets());
        this.activityOfferDAO.save(offer);
    }

    public void deleteActivityOffer(Long offerId){
        ActivityOfferEntity offer = this.findActivityOfferById(offerId);
        for (Iterator<TicketEntity> iterator = offer.getTickets().iterator(); iterator.hasNext();){
            TicketEntity ticket = iterator.next();
            ticket.getActivityOffers().remove(offer);
            if (ticket.getActivityOffers().size() == 0 && ticket.getAttractionOffers().size() == 0){
                this.ticketDAO.deleteTicketEntityById(ticket.getId());
            }
            iterator.remove();
        }
        this.imageUtils.deleteOfferImages("activities", offerId);
        this.activityOfferDAO.deleteActivityOfferEntityById(offerId);
    }
}
