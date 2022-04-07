package com.travelTim.attractions;

import com.travelTim.category.CategoryEntity;
import com.travelTim.category.CategoryService;
import com.travelTim.category.CategoryType;
import com.travelTim.food.FoodOfferEntity;
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
import java.util.Set;

@Service
@Transactional
public class AttractionOfferService {

    private final AttractionOfferDAO attractionOfferDAO;
    private final TicketDAO ticketDAO;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public AttractionOfferService(AttractionOfferDAO attractionOfferDAO, TicketDAO ticketDAO, UserService userService, CategoryService categoryService) {
        this.attractionOfferDAO = attractionOfferDAO;
        this.ticketDAO = ticketDAO;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public Long addAttractionOffer(AttractionOfferEntity attractionOffer){
        UserEntity loggedInUser = this.userService.findLoggedInUser();
        attractionOffer.setUser(loggedInUser);
        Set<TicketEntity> tickets = new HashSet<>();
        for (TicketEntity ticket: attractionOffer.getTickets()){
            TicketEntity ticketToSave;
            if (this.ticketDAO.findTicketEntityByNameAndPrice(ticket.getName(), ticket.getPrice()).isEmpty()){
                ticketToSave = this.ticketDAO.save(ticket);
            } else {
                ticketToSave = this.ticketDAO.findTicketEntityByNameAndPrice(ticket.getName(), ticket.getPrice()).get();
            }
            tickets.add(ticketToSave);
        }
        attractionOffer.setTickets(tickets);
        CategoryEntity category = this.categoryService.findCategoryByName(CategoryType.attractions);
        attractionOffer.setCategory(category);
        return this.attractionOfferDAO.save(attractionOffer).getId();
    }

    public AttractionOfferEntity findAttractionOfferById(Long offerId){
        return this.attractionOfferDAO.findAttractionsOfferEntityById(offerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Attraction offer with id: " + offerId + " was not found")
        );
    }
}
