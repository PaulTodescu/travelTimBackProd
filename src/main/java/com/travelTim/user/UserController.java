package com.travelTim.user;

import com.travelTim.activities.ActivityOfferEntity;
import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.business.BusinessEntity;
import com.travelTim.food.FoodOfferEntity;
import com.travelTim.lodging.LodgingOfferEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<UserEntity> adduser(@RequestBody UserEntity user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        List<UserEntity> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("userId") Long userId){
        UserEntity user = userService.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping()
    @ResponseBody
    public String getBeforeLoggedInUsername(@RequestParam String email){
        return userService.findUserByEmail(email).getFirstName();
    }

    @GetMapping(path = "/name")
    @ResponseBody
    public String getLoggedInUserName(){
        return userService.findLoggedInUserName();
    }


    @GetMapping(path = "/info")
    public ResponseEntity<UserEntity> getLoggedInUser(){
        UserEntity user = this.userService.findLoggedInUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user){
        this.userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/phone")
    public ResponseEntity<UserEntity> updateUserPhoneNumber(@RequestBody(required = false) String phoneNumber){
        System.out.println("received phone: " + phoneNumber);
        this.userService.updateUserPhoneNumber(phoneNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(){
        this.userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/businesses")
    public ResponseEntity<List<BusinessEntity>> getAllBusinessesForCurrentUser(){
        List<BusinessEntity> businesses = this.userService.getAllBusinessesForCurrentUser();
        return new ResponseEntity<>(businesses, HttpStatus.OK);
    }

    @GetMapping("/offers/lodging")
    public ResponseEntity<Set<LodgingOfferEntity>> getAllLodgingOffersForCurrentUser(){
        Set<LodgingOfferEntity> lodgingOffers = this.userService.getAllLodgingOffersForCurrentUser();
        return new ResponseEntity<>(lodgingOffers, HttpStatus.OK);
    }

    @GetMapping("/offers/food")
    public ResponseEntity<Set<FoodOfferEntity>> getAllFoodOffersForCurrentUser(){
        Set<FoodOfferEntity> foodOffers = this.userService.getAllFoodOffersForCurrentUser();
        return new ResponseEntity<>(foodOffers, HttpStatus.OK);
    }

    @GetMapping("/offers/attraction")
    public ResponseEntity<Set<AttractionOfferEntity>> getAllAttractionOffersForCurrentUser(){
        Set<AttractionOfferEntity> attractionOffers = this.userService.getAllAttractionOffersForCurrentUser();
        return new ResponseEntity<>(attractionOffers, HttpStatus.OK);
    }

    @GetMapping("/offers/activity")
    public ResponseEntity<Set<ActivityOfferEntity>> getAllActivityOffersForCurrentUser(){
        Set<ActivityOfferEntity> activityOffers = this.userService.getAllActivityOffersForCurrentUser();
        return new ResponseEntity<>(activityOffers, HttpStatus.OK);
    }

}
