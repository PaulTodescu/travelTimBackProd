package com.travelTim.user;


import com.travelTim.activities.ActivityDTOMapper;
import com.travelTim.activities.ActivityOfferBaseDetailsDTO;
import com.travelTim.activities.ActivityOfferEntity;
import com.travelTim.attractions.AttractionDTOMapper;
import com.travelTim.attractions.AttractionOfferBaseDetailsDTO;
import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.business.BusinessEntity;
import com.travelTim.currency.Currency;
import com.travelTim.currency.CurrencyConverter;
import com.travelTim.files.ImageService;
import com.travelTim.files.ImageType;
import com.travelTim.files.ImageUtils;
import com.travelTim.food.FoodDTOMapper;
import com.travelTim.food.FoodOfferBaseDetailsDTO;
import com.travelTim.food.FoodOfferEntity;
import com.travelTim.lodging.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserDAO userDAO;
    private final ImageService imageService;
    private final ImageUtils imageUtils;


    @Autowired
    public UserService(UserDAO userDAO, ImageService imageService, ImageUtils imageUtils) {
        this.userDAO = userDAO;
        this.imageService = imageService;
        this.imageUtils = imageUtils;
    }

    public void addUser(UserEntity user) {

        Optional<UserEntity> userOptional = userDAO.findUserEntityByEmail(user.getEmail());
        if (userOptional.isPresent()){
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }

        UserEntity savedUser = this.userDAO.save(user);
        this.imageService.uploadUserImage(savedUser.getId(), Optional.empty());
    }

    public UserEntity findUserById(Long id){
        return userDAO.findUserEntityById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with id: " + id + " was not found")
        );
    }

    public UserEntity findUserByEmail(String email){
        return userDAO.findUserEntityByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with email: " + email + " was not found")
        );
    }

    public List<UserEntity> findAllUsers(){
        return userDAO.findAll();
    }

    public String findLoggedInUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        return email;
    }

    public String findLoggedInUserName() {
        String email = this.findLoggedInUserEmail();
        Optional<UserEntity> user = userDAO.findUserEntityByEmail(email);
        if(user.isPresent()){
            return user.get().getFirstName();
        } else {
            return "unknown";
        }
    }

    public UserEntity findLoggedInUser() {
        String email = this.findLoggedInUserEmail();
        return this.findUserByEmail(email);
    }

    public UserDTO mapUserToDTO(UserEntity user){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }

    public void updateUser(UserEntity updatedUser) {
        UserEntity loggedInUser = this.findUserByEmail(this.findLoggedInUserEmail());

        String updatedFirstName = updatedUser.getFirstName();
        String updatedLastName = updatedUser.getLastName();
        String updatedPhoneNumber = updatedUser.getPhoneNumber();
        UserGender updatedGender = updatedUser.getGender();

        if (updatedFirstName != null && !(updatedFirstName.equals(loggedInUser.getFirstName()))){
            loggedInUser.setFirstName(updatedFirstName);
        }
        if (updatedLastName != null && !(updatedLastName.equals(loggedInUser.getLastName()))){
            loggedInUser.setLastName(updatedLastName);
        }
        if (updatedPhoneNumber != null && !(updatedPhoneNumber.equals(loggedInUser.getPhoneNumber()))){
            loggedInUser.setPhoneNumber(updatedPhoneNumber);
        }
        if (updatedGender != null && !(updatedGender.equals(loggedInUser.getGender()))){
            loggedInUser.setGender(updatedGender);
        }
        this.userDAO.save(loggedInUser);
    }

    public void updateUserPhoneNumber(String phoneNumber) {
        UserEntity loggedInUser = this.findUserByEmail(this.findLoggedInUserEmail());
        loggedInUser.setPhoneNumber(phoneNumber);
        this.userDAO.save(loggedInUser);
    }

    public void deleteUser() {
        UserEntity loggedInUser = this.findLoggedInUser();
        this.userDAO.deleteUserEntityById(loggedInUser.getId());
        this.imageUtils.deleteImage(loggedInUser.getId(), ImageType.USER);
    }

    public List<BusinessEntity> getAllBusinessesForCurrentUser() {
        UserEntity loggedInUser = this.findLoggedInUser();
        List<BusinessEntity> businesses = new ArrayList<>(List.copyOf(loggedInUser.getBusinesses()));
        businesses.sort((o1,o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        return businesses;
    }


    public List<LodgingOfferBaseDetailsDTO> getAllLodgingOffersForCurrentUser() throws IOException {
        UserEntity user = this.findLoggedInUser();

        LodgingDTOMapper mapper = new LodgingDTOMapper();
        // offers with price in EUR need to be converted to RON
        CurrencyConverter currencyConverter = new CurrencyConverter();
        //Float conversionRate = currencyConverter.getCurrencyConversionRate(Currency.EUR.name(), Currency.RON.name());
        Set<LodgingOfferBaseDetailsDTO> offers = mapper.mapLodgingOffersToBaseDetailsDTOs(user.getLodgingOffers());
        for (LodgingOfferBaseDetailsDTO offer: offers){
            if (offer.getCurrency() == Currency.EUR){
               // offer.setPrice(currencyConverter.getConvertedPrice(offer.getPrice(), conversionRate));
                offer.setCurrency(Currency.RON);
            }
            offer.setImage(this.imageService.getOfferFrontImage("lodging", offer.getId()));
        }
        return offers.stream()
                .sorted(Comparator.comparing(LodgingOfferBaseDetailsDTO::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public List<FoodOfferBaseDetailsDTO> getAllFoodOffersForCurrentUser() {
        UserEntity user = this.findLoggedInUser();
        FoodDTOMapper mapper = new FoodDTOMapper();
        Set<FoodOfferBaseDetailsDTO> offers = mapper.mapFoodOffersToBaseDetailsDTOs(
                user.getFoodOffers()
        );
        for (FoodOfferBaseDetailsDTO offer: offers){
            offer.setImage(this.imageService.getBusinessFrontImage(offer.getBusiness().getId()));
        }
        return offers.stream()
                .sorted(Comparator.comparing(FoodOfferBaseDetailsDTO::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public List<AttractionOfferBaseDetailsDTO> getAllAttractionOffersForCurrentUser() {
        UserEntity user = this.findLoggedInUser();
        AttractionDTOMapper mapper = new AttractionDTOMapper();
        Set<AttractionOfferBaseDetailsDTO> offers = mapper.mapAttractionOffersToBaseDetailsDTOs(
                user.getAttractionOffers()
        );
        for (AttractionOfferBaseDetailsDTO offer: offers) {
            offer.setImage(this.imageService.getOfferFrontImage("attractions", offer.getId()));
        }
        return offers.stream()
                .sorted(Comparator.comparing(AttractionOfferBaseDetailsDTO::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public List<ActivityOfferBaseDetailsDTO> getAllActivityOffersForCurrentUser() {
        UserEntity user = this.findLoggedInUser();
        ActivityDTOMapper mapper = new ActivityDTOMapper();
        Set<ActivityOfferBaseDetailsDTO> offers = mapper.mapActivityOffersToBaseDetailsDTOs(
                user.getActivityOffers()
        );
        for (ActivityOfferBaseDetailsDTO offer: offers) {
            offer.setImage(this.imageService.getOfferFrontImage("activities", offer.getId()));
        }
        return offers.stream()
                .sorted(Comparator.comparing(ActivityOfferBaseDetailsDTO::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

}
