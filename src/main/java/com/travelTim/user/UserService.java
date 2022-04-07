package com.travelTim.user;


import com.travelTim.activities.ActivityOfferEntity;
import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.business.BusinessEntity;
import com.travelTim.files.ImageService;
import com.travelTim.files.ImageType;
import com.travelTim.food.FoodOfferEntity;
import com.travelTim.lodging.LodgingOfferEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService {

    private final UserDAO userDAO;
    private final ImageService imageService;

    @Autowired
    public UserService(UserDAO userDAO, ImageService imageService) {
        this.userDAO = userDAO;
        this.imageService = imageService;
    }

    public void addUser(UserEntity user) {

        Optional<UserEntity> userOptional = userDAO.findUserEntityByEmail(user.getEmail());
        if (userOptional.isPresent()){
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }

        UserEntity savedUser = this.userDAO.save(user);
        this.imageService.uploadImage(savedUser.getId(), Optional.empty(), ImageType.USER);
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
        this.imageService.deleteImage(loggedInUser.getId(), ImageType.USER);
    }

    public List<BusinessEntity> getAllBusinessesForCurrentUser() {
        UserEntity loggedInUser = this.findLoggedInUser();
        List<BusinessEntity> businesses = new ArrayList<>(List.copyOf(loggedInUser.getBusinesses()));
        businesses.sort((o1,o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        return businesses;
    }

    public Set<FoodOfferEntity> getAllFoodOffersForCurrentUser() {
        UserEntity loggedInUser = this.findLoggedInUser();
        return loggedInUser.getFoodOffers();
    }


    public Set<AttractionOfferEntity> getAllAttractionOffersForCurrentUser() {
        UserEntity loggedInUser = this.findLoggedInUser();
        return loggedInUser.getAttractionOffers();
    }

    public Set<ActivityOfferEntity> getAllActivityOffersForCurrentUser() {
        UserEntity loggedInUser = this.findLoggedInUser();
        return loggedInUser.getActivityOffers();
    }

    public Set<LodgingOfferEntity> getAllLodgingOffersForCurrentUser() {
        UserEntity loggedInUser = this.findLoggedInUser();
        return loggedInUser.getLodgingOffers();
    }

}
