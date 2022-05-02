package com.travelTim.food;

import com.travelTim.category.CategoryEntity;
import com.travelTim.category.CategoryService;
import com.travelTim.category.CategoryType;
import com.travelTim.files.ImageUtils;
import com.travelTim.user.UserEntity;
import com.travelTim.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class FoodOfferService {

    private final FoodOfferDAO foodOfferDAO;
    private final FoodMenuCategoryDAO foodMenuCategoryDAO;
    private final FoodMenuItemDAO foodMenuItemDAO;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ImageUtils imageUtils;

    @Autowired
    public FoodOfferService(FoodOfferDAO foodOfferDAO, FoodMenuCategoryDAO foodMenuCategoryDAO,
                            FoodMenuItemDAO foodMenuItemDAO, UserService userService,
                            CategoryService categoryService, ImageUtils imageUtils) {
        this.foodOfferDAO = foodOfferDAO;
        this.foodMenuCategoryDAO = foodMenuCategoryDAO;
        this.foodMenuItemDAO = foodMenuItemDAO;
        this.userService = userService;
        this.categoryService = categoryService;
        this.imageUtils = imageUtils;
    }

    public Long addFoodOffer(FoodOfferEntity foodOffer) {
        UserEntity loggedInUser = this.userService.findLoggedInUser();
        foodOffer.setUser(loggedInUser);
        CategoryEntity category = this.categoryService.findCategoryByName(CategoryType.food);
        foodOffer.setCategory(category);
        try {
            return this.foodOfferDAO.save(foodOffer).getId();
        } catch (DataIntegrityViolationException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Selected business already has a food offer Manage from your account", exception);
        }
    }

    public FoodOfferEntity findFoodOfferById(Long foodOfferId){
        return this.foodOfferDAO.findFoodOfferEntityById(foodOfferId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Food offer with id: " + foodOfferId + " was not found")
        );
    }

    public FoodOfferEditDTO findFoodOfferForEdit(Long offerId){
        FoodOfferEntity foodOffer = this.findFoodOfferById(offerId);
        FoodDTOMapper mapper = new FoodDTOMapper();
        return mapper.mapFoodOfferToEditDTO(foodOffer);
    }

    public void addFoodOfferMenu(Long foodOfferId, Map<FoodMenuCategory, List<FoodMenuItem>> foodMenu) {
        FoodOfferEntity foodOffer = this.findFoodOfferById(foodOfferId);
        for (Map.Entry<FoodMenuCategory, List<FoodMenuItem>> menuEntry: foodMenu.entrySet()){

            FoodMenuCategory menuCategoryToSave = this.foodMenuCategoryDAO.save(menuEntry.getKey());
            Set<FoodMenuItem> menuItems = new HashSet<>();
            for (FoodMenuItem menuItem: menuEntry.getValue()){
                FoodMenuItem menuItemToSave = this.foodMenuItemDAO.save(menuItem);
                menuItems.add(menuItemToSave);
            }
            menuCategoryToSave.setFoodMenuItems(menuItems);
            foodOffer.addFoodMenuCategory(menuCategoryToSave);
        }
        this.foodOfferDAO.save(foodOffer);
    }

    public void editFoodOffer(FoodOfferEditDTO offerToSave, Long offerId){
        FoodOfferEntity offer = this.findFoodOfferById(offerId);
        offer.setBusiness(offerToSave.getBusiness());
        offer.setDescription(offerToSave.getDescription());
        this.deleteFoodOfferMenu(offer);
        this.foodOfferDAO.save(offer);
    }

    public void deleteFoodOffer(Long offerId) {
        FoodOfferEntity offer = this.findFoodOfferById(offerId);
        this.deleteFoodOfferMenu(offer);
        this.imageUtils.deleteOfferImages("food", offerId);
        this.foodOfferDAO.deleteFoodOfferEntityById(offerId);
    }

    public void deleteFoodOfferMenu(FoodOfferEntity offer) {
        for (Iterator<FoodMenuCategory> iterator1 = offer.getFoodMenuCategories().iterator(); iterator1.hasNext();){
            FoodMenuCategory category = iterator1.next();
            category.getFoodOffers().remove(offer);
//            if (category.getFoodOffers().size() == 0){
//                this.foodMenuCategoryDAO.deleteFoodMenuCategoryById(category.getId());
//            }
            iterator1.remove();
            for (Iterator<FoodMenuItem> iterator2 = category.getFoodMenuItems().iterator(); iterator2.hasNext();){
                FoodMenuItem item = iterator2.next();
                item.getFoodMenuCategories().remove(category);
                if (item.getFoodMenuCategories().size() == 0){
                    this.foodMenuItemDAO.deleteFoodMenuItemById(item.getId());
                }
                iterator2.remove();
            }
        }
    }
}
