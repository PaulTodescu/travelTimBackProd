package com.travelTim.business;

import com.travelTim.activities.ActivityOfferEntity;
import com.travelTim.activities.ActivityOfferService;
import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.attractions.AttractionOfferService;
import com.travelTim.currency.CurrencyConverter;
import com.travelTim.files.ImageType;
import com.travelTim.files.ImageUtils;
import com.travelTim.food.FoodOfferService;
import com.travelTim.location.City;
import com.travelTim.lodging.LegalPersonLodgingOfferDetailsDTO;
import com.travelTim.lodging.LodgingDTOMapper;
import com.travelTim.lodging.LodgingOfferEntity;
import com.travelTim.lodging.LodgingOfferService;
import com.travelTim.user.UserEntity;
import com.travelTim.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.travelTim.currency.Currency;


import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BusinessService {

    private final BusinessDAO businessDAO;
    private final BusinessDayScheduleDAO businessDayScheduleDAO;
    private final UserService userService;
    private final ImageUtils imageUtils;
    private final FoodOfferService foodOfferService;
    private final LodgingOfferService lodgingOfferService;
    private final AttractionOfferService attractionOfferService;
    private final ActivityOfferService activityOfferService;

    @Autowired
    public BusinessService(BusinessDAO businessDAO, BusinessDayScheduleDAO businessDayScheduleDAO,
                           UserService userService, ImageUtils imageUtils,
                           FoodOfferService foodOfferService, LodgingOfferService lodgingOfferService,
                           AttractionOfferService attractionOfferService,
                           ActivityOfferService activityOfferService) {
        this.businessDAO = businessDAO;
        this.businessDayScheduleDAO = businessDayScheduleDAO;
        this.userService = userService;
        this.imageUtils = imageUtils;
        this.foodOfferService = foodOfferService;
        this.lodgingOfferService = lodgingOfferService;
        this.attractionOfferService = attractionOfferService;
        this.activityOfferService = activityOfferService;
    }

    public Long addBusiness(BusinessEntity business) {
        UserEntity loggedInUser = this.userService.findLoggedInUser();
        BusinessEntity businessEntity = new BusinessEntity(
                business.getName(), business.getCity(), business.getAddress(), business.getCui(), loggedInUser);
        return this.businessDAO.save(businessEntity).getId();
    }

    public void addSchedule(Long businessId, Set<BusinessDaySchedule> scheduleToAdd){
        BusinessEntity business = this.findBusinessById(businessId);
        Set<BusinessDaySchedule> schedule = new HashSet<>();
        for (BusinessDaySchedule daySchedule: scheduleToAdd){
            BusinessDaySchedule dayScheduleToSave;
            Optional<BusinessDaySchedule> checkIfPresent = this.businessDayScheduleDAO
                    .findBusinessDayScheduleByDayAndStartTimeAndEndTimeAndClosed(
                            daySchedule.getDay(), daySchedule.getStartTime(),
                            daySchedule.getEndTime(), daySchedule.getClosed()
                    );
            if (checkIfPresent.isEmpty()){
                dayScheduleToSave = this.businessDayScheduleDAO.save(daySchedule);
            } else {
                dayScheduleToSave = checkIfPresent.get();
            }
            schedule.add(dayScheduleToSave);
        }
        business.setSchedule(schedule);
        this.businessDAO.save(business);
    }

    public BusinessEntity findBusinessById(Long businessId){
        return this.businessDAO.findBusinessEntityById(businessId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Business with id: " + businessId + " was not found")
        );
    }

    public List<BusinessEntity> getAllBusinesses() {
        return this.businessDAO.findAll();
    }

    public void editBusiness(BusinessEntity updatedBusiness, Long businessId) {
        BusinessEntity business = this.findBusinessById(businessId);
        String updatedName = updatedBusiness.getName();
        City updatedCity = updatedBusiness.getCity();
        String updatedCui = updatedBusiness.getCui();
        String updatedAddress = updatedBusiness.getAddress();

        if (updatedName != null && !(updatedName.equals(business.getName()))){
            business.setName(updatedName);
        }

        if (updatedCity != null && !(updatedCity.equals(business.getCity()))){
            business.setCity(updatedCity);
        }

        if (updatedCui != null && !(updatedCui.equals(business.getCui()))){
            business.setCui(updatedCui);
        }

        if (updatedAddress != null && !(updatedAddress.equals(business.getAddress()))){
            business.setAddress(updatedAddress);
        }
        this.businessDAO.save(business);
    }

    public void deleteBusiness(Long businessId) {
        BusinessEntity business = this.findBusinessById(businessId);
        if (business.getLodgingOffers().size() > 0){
            for (LodgingOfferEntity lodgingOffer: business.getLodgingOffers()){
                this.lodgingOfferService.deleteLodgingOffer(lodgingOffer.getId());
            }
        }
        if (business.getFoodOffer() != null){
            this.foodOfferService.deleteFoodOffer(business.getFoodOffer().getId());
        }
        if (business.getAttractionOffers().size() > 0){
            for (AttractionOfferEntity attractionOffer: business.getAttractionOffers()) {
                this.attractionOfferService.deleteAttractionOffer(attractionOffer.getId());
            }
        }
        if (business.getActivityOffers().size() > 0){
            for (ActivityOfferEntity activityOffer: business.getActivityOffers()) {
                this.activityOfferService.deleteActivityOffer(activityOffer.getId());
            }
        }
        this.deleteSchedule(businessId);
        this.businessDAO.deleteBusinessEntityById(businessId);
        this.imageUtils.deleteImage(businessId, ImageType.BUSINESS);
    }

    public void deleteSchedule(Long businessId){
        BusinessEntity business = this.findBusinessById(businessId);
        for (Iterator<BusinessDaySchedule> iterator = business.getSchedule().iterator(); iterator.hasNext();){
            BusinessDaySchedule daySchedule = iterator.next();
            daySchedule.getBusinesses().remove(business);
//            if (daySchedule.getBusinesses().size() == 0){
//                this.businessDayScheduleDAO.deleteBusinessDayScheduleById(daySchedule.getId());
//            }
            iterator.remove();
        }
    }

    public Boolean checkIfBusinessHasFoodOffer(Long businessId) {
        BusinessEntity business = this.findBusinessById(businessId);
        return business.getFoodOffer() != null;
    }

    public List<LegalPersonLodgingOfferDetailsDTO> getLodgingOffers(Long businessId) throws IOException {
        LodgingDTOMapper mapper = new LodgingDTOMapper();
        BusinessEntity business = this.findBusinessById(businessId);
        Set<LegalPersonLodgingOfferDetailsDTO> offers =
                mapper.mapLodgingOffersToLodgingDetailsDTOs(business.getLodgingOffers());
        // convert offer prices to RON
        try {
             CurrencyConverter currencyConverter = new CurrencyConverter();
             Float conversionRateFromEUR = currencyConverter.getCurrencyConversionRate(Currency.EUR.name(), Currency.RON.name());
            for (LegalPersonLodgingOfferDetailsDTO offer : offers) {
                if (offer.getCurrency() == Currency.EUR) {
                    // offer.setPrice(currencyConverter.getConvertedPrice(offer.getPrice(), conversionRateFromEUR));
                    offer.setCurrency(Currency.RON);
                }
            }
        }
        catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not retrieve currency for monetary conversion");
        }
        return offers.stream()
                .sorted(Comparator.comparing(LegalPersonLodgingOfferDetailsDTO::getPrice))
                .collect(Collectors.toList());
    }
}
