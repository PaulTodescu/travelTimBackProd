package com.travelTim.food;

import com.travelTim.business.BusinessEntity;
import com.travelTim.category.CategoryEntity;
import com.travelTim.category.CategoryService;
import com.travelTim.category.CategoryType;
import com.travelTim.user.UserEntity;
import com.travelTim.user.UserService;
import org.hibernate.exception.ConstraintViolationException;
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

    @Autowired
    public FoodOfferService(FoodOfferDAO foodOfferDAO, FoodMenuCategoryDAO foodMenuCategoryDAO, FoodMenuItemDAO foodMenuItemDAO, UserService userService, CategoryService categoryService) {
        this.foodOfferDAO = foodOfferDAO;
        this.foodMenuCategoryDAO = foodMenuCategoryDAO;
        this.foodMenuItemDAO = foodMenuItemDAO;
        this.userService = userService;
        this.categoryService = categoryService;
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

    public void addFoodOfferMenu(Long foodOfferId, Map<FoodMenuCategory, List<FoodMenuItem>> foodMenu) {

        FoodOfferEntity foodOffer = this.findFoodOfferById(foodOfferId);

        for (Map.Entry<FoodMenuCategory, List<FoodMenuItem>> menuEntry: foodMenu.entrySet()){

            FoodMenuCategory menuCategoryToSave;
            String categoryName = menuEntry.getKey().getName();
            if (this.foodMenuCategoryDAO.findFoodMenuCategoryByName(categoryName).isEmpty()){
                menuCategoryToSave = this.foodMenuCategoryDAO.save(menuEntry.getKey());
            } else {
                menuCategoryToSave = this.foodMenuCategoryDAO.findFoodMenuCategoryByName(categoryName).get();
            }

            Set<FoodMenuItem> menuItems = new HashSet<>();
            FoodMenuItem menuItemToSave;
            for (FoodMenuItem menuItem: menuEntry.getValue()){
                String itemName = menuItem.getName();
                Double itemWeight = menuItem.getWeight();
                Double itemPrice = menuItem.getPrice();
                if (this.foodMenuItemDAO.findFoodMenuItemByNameAndWeightAndPrice(itemName, itemWeight, itemPrice).isEmpty()){
                    menuItemToSave = this.foodMenuItemDAO.save(menuItem);
                } else {
                    menuItemToSave = this.foodMenuItemDAO.findFoodMenuItemByNameAndWeightAndPrice(itemName, itemWeight, itemPrice).get();
                }
                menuItems.add(menuItemToSave);
            }
            menuCategoryToSave.setFoodMenuItems(menuItems);
            foodOffer.addFoodMenuCategory(menuCategoryToSave);
        }

        this.foodOfferDAO.save(foodOffer);

    }
}
