package com.travelTim.favourites;

import com.travelTim.activities.ActivityOfferEntity;
import com.travelTim.activities.ActivityOfferService;
import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.attractions.AttractionOfferService;
import com.travelTim.business.BusinessEntity;
import com.travelTim.files.ImageService;
import com.travelTim.food.FoodOfferEntity;
import com.travelTim.food.FoodOfferService;
import com.travelTim.lodging.LegalPersonLodgingOfferEntity;
import com.travelTim.lodging.LodgingOfferEntity;
import com.travelTim.lodging.LodgingOfferService;
import com.travelTim.lodging.PhysicalPersonLodgingOfferEntity;
import com.travelTim.user.UserEntity;
import com.travelTim.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FavouriteOffersService {

    private final FavouriteOffersDAO favouriteOffersDAO;
    private final UserService userService;
    private final ImageService imageService;
    private final LodgingOfferService lodgingOfferService;
    private final FoodOfferService foodOfferService;
    private final AttractionOfferService attractionOfferService;
    private final ActivityOfferService activityOfferService;

    @Autowired
    public FavouriteOffersService(FavouriteOffersDAO favouriteOffersDAO, UserService userService,
                                  ImageService imageService, LodgingOfferService lodgingOfferService,
                                  FoodOfferService foodOfferService,
                                  AttractionOfferService attractionOfferService,
                                  ActivityOfferService activityOfferService) {
        this.favouriteOffersDAO = favouriteOffersDAO;
        this.userService = userService;
        this.imageService = imageService;
        this.lodgingOfferService = lodgingOfferService;
        this.foodOfferService = foodOfferService;
        this.attractionOfferService = attractionOfferService;
        this.activityOfferService = activityOfferService;
    }

    public void addOfferToFavourites(Long userId, Long offerId, OfferCategory category) {
        switch (category){
            case lodging:
                LodgingOfferEntity lodgingOffer =
                        this.lodgingOfferService.findLodgingOfferEntityById(offerId);
                this.addLodgingOfferToFavourites(userId, lodgingOffer);
                break;
            case food:
                FoodOfferEntity foodOffer = this.foodOfferService.findFoodOfferById(offerId);
                this.addFoodOfferToFavourites(userId, foodOffer);
                break;
            case attractions:
                AttractionOfferEntity attractionOffer =
                        this.attractionOfferService.findAttractionOfferById(offerId);
                this.addAttractionOfferToFavourites(userId, attractionOffer);
                break;
            case activities:
                ActivityOfferEntity activityOffer =
                        this.activityOfferService.findActivityOfferById(offerId);
                this.addActivityOfferToFavourites(userId, activityOffer);
                break;
        }
    }

    public void addLodgingOfferToFavourites(Long userId, LodgingOfferEntity offer){
        UserEntity user = this.userService.findUserById(userId);
        FavouriteOffersEntity favourites = user.getFavourites();
        favourites.getLodgingOffers().add(offer);
        this.favouriteOffersDAO.save(favourites);
    }

    public void addFoodOfferToFavourites(Long userId, FoodOfferEntity offer){
        UserEntity user = this.userService.findUserById(userId);
        FavouriteOffersEntity favourites = user.getFavourites();
        favourites.getFoodOffers().add(offer);
        this.favouriteOffersDAO.save(favourites);
    }

    public void addAttractionOfferToFavourites(Long userId, AttractionOfferEntity offer){
        UserEntity user = this.userService.findUserById(userId);
        FavouriteOffersEntity favourites = user.getFavourites();
        favourites.getAttractionOffers().add(offer);
        this.favouriteOffersDAO.save(favourites);
    }

    public void addActivityOfferToFavourites(Long userId, ActivityOfferEntity offer){
        UserEntity user = this.userService.findUserById(userId);
        FavouriteOffersEntity favourites = user.getFavourites();
        favourites.getActivityOffers().add(offer);
        this.favouriteOffersDAO.save(favourites);
    }

    public void removeOfferFromFavourites(Long userId, Long offerId, OfferCategory category) {
        switch (category){
            case lodging:
                LodgingOfferEntity lodgingOffer =
                        this.lodgingOfferService.findLodgingOfferEntityById(offerId);
                this.removeLodgingOfferFromFavourites(userId, lodgingOffer);
                break;
            case food:
                FoodOfferEntity foodOffer = this.foodOfferService.findFoodOfferById(offerId);
                this.removeFoodOfferFromFavourites(userId, foodOffer);
                break;
            case attractions:
                AttractionOfferEntity attractionOffer =
                        this.attractionOfferService.findAttractionOfferById(offerId);
                this.removeAttractionOfferFromFavourites(userId, attractionOffer);
                break;
            case activities:
                ActivityOfferEntity activityOffer =
                        this.activityOfferService.findActivityOfferById(offerId);
                this.removeActivityOfferFromFavourites(userId, activityOffer);
                break;
        }
    }

    public void removeLodgingOfferFromFavourites(Long userId, LodgingOfferEntity offer){
        UserEntity user = this.userService.findUserById(userId);
        FavouriteOffersEntity favourites = user.getFavourites();
        if (favourites.getLodgingOffers() != null) {
            // remove all offers from the business from favourites
            if (offer instanceof LegalPersonLodgingOfferEntity) {
                Set<LegalPersonLodgingOfferEntity> legalOffers =
                        ((LegalPersonLodgingOfferEntity) offer).getBusiness().getLodgingOffers();
                for (LegalPersonLodgingOfferEntity legalOffer : legalOffers) {
                    favourites.getLodgingOffers().remove(legalOffer);
                }
            } else {
                favourites.getLodgingOffers().remove(offer);
            }
            this.favouriteOffersDAO.save(favourites);
        }
    }

    public void removeFoodOfferFromFavourites(Long userId, FoodOfferEntity offer){
        UserEntity user = this.userService.findUserById(userId);
        FavouriteOffersEntity favourites = user.getFavourites();
        if (favourites.getFoodOffers() != null) {
            favourites.getFoodOffers().remove(offer);
            this.favouriteOffersDAO.save(favourites);
        }
    }

    public void removeAttractionOfferFromFavourites(Long userId, AttractionOfferEntity offer){
        UserEntity user = this.userService.findUserById(userId);
        FavouriteOffersEntity favourites = user.getFavourites();
        if (favourites.getAttractionOffers() != null) {
            favourites.getAttractionOffers().remove(offer);
            this.favouriteOffersDAO.save(favourites);
        }
    }

    public void removeActivityOfferFromFavourites(Long userId, ActivityOfferEntity offer){
        UserEntity user = this.userService.findUserById(userId);
        FavouriteOffersEntity favourites = user.getFavourites();
        if (favourites.getActivityOffers() != null) {
            favourites.getActivityOffers().remove(offer);
            this.favouriteOffersDAO.save(favourites);
        }
    }

    public Set<FavouriteOfferCategoryId> getFavouriteOffersCategoryIdForUser(Long userId){
        UserEntity user = this.userService.findUserById(userId);
        Set<FavouriteOfferCategoryId> favouriteOffers = new HashSet<>();
        for (LodgingOfferEntity lodgingOffer: user.getFavourites().getLodgingOffers()){
            FavouriteOfferCategoryId favouriteOffer =
                    new FavouriteOfferCategoryId(lodgingOffer.getId(), OfferCategory.lodging);
            favouriteOffers.add(favouriteOffer);
        }
        for (FoodOfferEntity foodOffer: user.getFavourites().getFoodOffers()){
            FavouriteOfferCategoryId favouriteOffer =
                    new FavouriteOfferCategoryId(foodOffer.getId(), OfferCategory.food);
            favouriteOffers.add(favouriteOffer);
        }
        for (AttractionOfferEntity attractionOffer: user.getFavourites().getAttractionOffers()){
            FavouriteOfferCategoryId favouriteOffer =
                    new FavouriteOfferCategoryId(attractionOffer.getId(), OfferCategory.attractions);
            favouriteOffers.add(favouriteOffer);
        }
        for (ActivityOfferEntity activityOffer: user.getFavourites().getActivityOffers()){
            FavouriteOfferCategoryId favouriteOffer =
                    new FavouriteOfferCategoryId(activityOffer.getId(), OfferCategory.activities);
            favouriteOffers.add(favouriteOffer);
        }
        return favouriteOffers;
    }

    public List<FavouriteOffer> getFavouriteOffersForUser(Long userId){
        UserEntity user = this.userService.findUserById(userId);
        Set<FavouriteOffer> favouriteOffers = new HashSet<>();

        List<LegalPersonLodgingOfferEntity> legalOffers = new ArrayList<>();
        for (LodgingOfferEntity lodgingOffer: user.getFavourites().getLodgingOffers()){
            FavouriteOffer favouriteOffer = new FavouriteOffer();
            if (lodgingOffer instanceof LegalPersonLodgingOfferEntity){
                legalOffers.add((LegalPersonLodgingOfferEntity) lodgingOffer);
            } else if (lodgingOffer instanceof PhysicalPersonLodgingOfferEntity){
                PhysicalPersonLodgingOfferEntity physicalOffer = (PhysicalPersonLodgingOfferEntity) lodgingOffer;
                favouriteOffer.setTitle(physicalOffer.getTitle());
                favouriteOffer.setImage(this.imageService.getOfferFrontImage("lodging", physicalOffer.getId()));
                favouriteOffer.setId(lodgingOffer.getId());
                favouriteOffer.setCategory(OfferCategory.lodging);
                favouriteOffer.setType(OfferType.physical);
                favouriteOffer.setCreatedAt(lodgingOffer.getCreatedAt());
                favouriteOffers.add(favouriteOffer);
            }
        }
        for (LegalPersonLodgingOfferEntity legalOffer: this.getLegalFavouriteOfferGroupedByBusiness(legalOffers)){
            FavouriteOffer favouriteOffer = new FavouriteOffer();
            favouriteOffer.setId(legalOffer.getId());
            favouriteOffer.setTitle(legalOffer.getBusiness().getName());
            favouriteOffer.setImage(this.imageService.getBusinessFrontImage(legalOffer.getBusiness().getId()));
            favouriteOffer.setCategory(OfferCategory.lodging);
            favouriteOffer.setType(OfferType.legal);
            favouriteOffer.setCreatedAt(legalOffer.getCreatedAt());
            favouriteOffers.add(favouriteOffer);
        }

        for (FoodOfferEntity foodOffer: user.getFavourites().getFoodOffers()){
            FavouriteOffer favouriteOffer = new FavouriteOffer();
            favouriteOffer.setId(foodOffer.getId());
            favouriteOffer.setCategory(OfferCategory.food);
            favouriteOffer.setTitle(foodOffer.getBusiness().getName());
            favouriteOffer.setImage(this.imageService.getOfferFrontImage("food", foodOffer.getId()));
            favouriteOffer.setCreatedAt(foodOffer.getCreatedAt());
            favouriteOffers.add(favouriteOffer);
        }
        for (AttractionOfferEntity attractionOffer: user.getFavourites().getAttractionOffers()){
            FavouriteOffer favouriteOffer = new FavouriteOffer();
            favouriteOffer.setId(attractionOffer.getId());
            favouriteOffer.setCategory(OfferCategory.attractions);
            favouriteOffer.setTitle(attractionOffer.getTitle());
            favouriteOffer.setImage(this.imageService.getOfferFrontImage("attractions", attractionOffer.getId()));
            favouriteOffer.setCreatedAt(attractionOffer.getCreatedAt());
            favouriteOffers.add(favouriteOffer);
        }
        for (ActivityOfferEntity activityOffer: user.getFavourites().getActivityOffers()){
            FavouriteOffer favouriteOffer = new FavouriteOffer();
            favouriteOffer.setId(activityOffer.getId());
            favouriteOffer.setCategory(OfferCategory.activities);
            favouriteOffer.setTitle(activityOffer.getTitle());
            favouriteOffer.setImage(this.imageService.getOfferFrontImage("activities", activityOffer.getId()));
            favouriteOffer.setCreatedAt(activityOffer.getCreatedAt());
            favouriteOffers.add(favouriteOffer);
        }
        return this.getSortedOffersByDate(favouriteOffers);
    }

    // same business can have multiple lodging offers
    // only retrieve one from each business
    public List<LegalPersonLodgingOfferEntity> getLegalFavouriteOfferGroupedByBusiness(
            List<LegalPersonLodgingOfferEntity> offers){
        List<LegalPersonLodgingOfferEntity> groupedOffers = new ArrayList<>();
        Map<String, List<LegalPersonLodgingOfferEntity>> legalOffersGroups =
                offers.stream().collect(Collectors.groupingBy(offer -> offer.getBusiness().getName()));
        for (Map.Entry<String, List<LegalPersonLodgingOfferEntity>> legalOffersGroup:
                legalOffersGroups.entrySet()){
            // get latest offer
            LegalPersonLodgingOfferEntity latestOffer = legalOffersGroup.getValue().stream()
                    .sorted(Comparator.comparing(LodgingOfferEntity::getCreatedAt).reversed())
                    .collect(Collectors.toList())
                    .get(0);
            groupedOffers.add(latestOffer);
        }
        return groupedOffers;
    }

    public List<FavouriteOffer> getSortedOffersByDate(Set<FavouriteOffer> offers){
        return offers
                .stream()
                .sorted(Comparator.comparing(FavouriteOffer::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

}
