package com.travelTim.business;

import com.travelTim.activities.ActivityOfferEntity;
import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.currency.Currency;
import com.travelTim.currency.CurrencyConverter;
import com.travelTim.files.ImageService;
import com.travelTim.files.ImageType;
import com.travelTim.location.City;
import com.travelTim.lodging.LegalPersonLodgingOfferDetailsDTO;
import com.travelTim.lodging.LegalPersonLodgingOfferEntity;
import com.travelTim.lodging.LodgingDTOMapper;
import com.travelTim.lodging.LodgingOfferEntity;
import com.travelTim.user.UserEntity;
import com.travelTim.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BusinessService {

    private final BusinessDAO businessDAO;
    private final UserService userService;
    private final ImageService imageService;

    @Autowired
    public BusinessService(BusinessDAO businessDAO, UserService userService, ImageService imageService) {
        this.businessDAO = businessDAO;
        this.userService = userService;
        this.imageService = imageService;
    }

    public Long addBusiness(BusinessEntity business) {
        UserEntity loggedInUser = this.userService.findLoggedInUser();
        BusinessEntity businessEntity = new BusinessEntity(
                business.getName(), business.getCity(), business.getAddress(), business.getCui(), loggedInUser);
        return this.businessDAO.save(businessEntity).getId();
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
        if (this.businessDAO.existsById(businessId)) {

            BusinessEntity business = this.findBusinessById(businessId);
            if (business.getLodgingOffers() != null){
                for (LodgingOfferEntity lodgingOffer: business.getLodgingOffers()){
                    this.imageService.deleteOfferImages("lodging", lodgingOffer.getId());
                }
            }
            if (business.getFoodOffer() != null){
                this.imageService.deleteOfferImages("food", business.getFoodOffer().getId());
            }
            if (business.getAttractionOffers() != null){
                for (AttractionOfferEntity attractionOffer: business.getAttractionOffers()) {
                    this.imageService.deleteOfferImages("attractions", attractionOffer.getId());
                }
            }
            if (business.getActivityOffers() != null){
                for (ActivityOfferEntity activityOffer: business.getActivityOffers()) {
                    this.imageService.deleteOfferImages("activities", activityOffer.getId());
                }
            }

            this.businessDAO.deleteBusinessEntityById(businessId);
            this.imageService.deleteImage(businessId, ImageType.BUSINESS);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Business with id: " + businessId + " was not found!");
        }
    }

    public Boolean checkIfBusinessHasFoodOffer(Long businessId) {
        BusinessEntity business = this.findBusinessById(businessId);
        return business.getFoodOffer() != null;
    }

    public List<LegalPersonLodgingOfferDetailsDTO> getLodgingOffers(Long businessId, Currency currency) throws IOException {
        LodgingDTOMapper mapper = new LodgingDTOMapper();
        BusinessEntity business = this.findBusinessById(businessId);
        Set<LegalPersonLodgingOfferDetailsDTO> offers =
                mapper.mapLodgingOffersToLodgingDetailsDTOs(business.getLodgingOffers());
        // convert offer prices to the selected currency
        try {
            CurrencyConverter currencyConverter = new CurrencyConverter();
            Float conversionRateFromRON = currencyConverter.getCurrencyConversionRate(Currency.RON.name(), currency.name());
            Float conversionRateFromEUR = currencyConverter.getCurrencyConversionRate(Currency.EUR.name(), currency.name());
            Float conversionRateFromGBP = currencyConverter.getCurrencyConversionRate(Currency.GBP.name(), currency.name());
            Float conversionRateFromUSD = currencyConverter.getCurrencyConversionRate(Currency.USD.name(), currency.name());
            for (LegalPersonLodgingOfferDetailsDTO offer : offers) {
                if (offer.getCurrency() != currency) {
                    switch (offer.getCurrency()) {
                        case RON:
                            offer.setPrice(currencyConverter.getConvertedPrice(offer.getPrice(), conversionRateFromRON));
                            break;
                        case EUR:
                            offer.setPrice(currencyConverter.getConvertedPrice(offer.getPrice(), conversionRateFromEUR));
                            break;
                        case GBP:
                            offer.setPrice(currencyConverter.getConvertedPrice(offer.getPrice(), conversionRateFromGBP));
                            break;
                        case USD:
                            offer.setPrice(currencyConverter.getConvertedPrice(offer.getPrice(), conversionRateFromUSD));
                            break;
                    }
                    offer.setCurrency(currency);
                }
            }
        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not retrieve currency for monetary conversion");
        }
        return offers.stream()
                .sorted(Comparator.comparing(LegalPersonLodgingOfferDetailsDTO::getPrice))
                .collect(Collectors.toList());
    }
}
