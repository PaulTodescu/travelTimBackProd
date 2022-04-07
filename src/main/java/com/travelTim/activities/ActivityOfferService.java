package com.travelTim.activities;

import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.category.CategoryEntity;
import com.travelTim.category.CategoryService;
import com.travelTim.category.CategoryType;
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
public class ActivityOfferService {

    private final ActivityOfferDAO activityOfferDAO;
    private final UserService userService;
    private final TicketDAO ticketDAO;
    private final CategoryService categoryService;

    @Autowired
    public ActivityOfferService(ActivityOfferDAO activityOfferDAO, UserService userService, TicketDAO ticketDAO, CategoryService categoryService) {
        this.activityOfferDAO = activityOfferDAO;
        this.userService = userService;
        this.ticketDAO = ticketDAO;
        this.categoryService = categoryService;
    }

    public Long addActivityOffer(ActivityOfferEntity activityOffer) {
        UserEntity loggedInUser = this.userService.findLoggedInUser();
        activityOffer.setUser(loggedInUser);
        Set<TicketEntity> tickets = new HashSet<>();
        for (TicketEntity ticket: activityOffer.getTickets()){
            TicketEntity ticketToSave;
            if (this.ticketDAO.findTicketEntityByNameAndPrice(ticket.getName(), ticket.getPrice()).isEmpty()){
                ticketToSave = this.ticketDAO.save(ticket);
            } else {
                ticketToSave = this.ticketDAO.findTicketEntityByNameAndPrice(ticket.getName(), ticket.getPrice()).get();
            }
            tickets.add(ticketToSave);
        }
        activityOffer.setTickets(tickets);
        CategoryEntity category = this.categoryService.findCategoryByName(CategoryType.activities);
        activityOffer.setCategory(category);
        return this.activityOfferDAO.save(activityOffer).getId();
    }

    public ActivityOfferEntity findActivityOfferById(Long offerId) {
        return this.activityOfferDAO.findActivityOfferEntityById(offerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Activity offer with id: " + offerId + " was not found")
        );
    }
}
