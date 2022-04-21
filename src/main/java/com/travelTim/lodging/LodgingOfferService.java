package com.travelTim.lodging;

import com.travelTim.category.CategoryEntity;
import com.travelTim.category.CategoryService;
import com.travelTim.category.CategoryType;
import com.travelTim.currency.Currency;
import com.travelTim.currency.CurrencyConverter;
import com.travelTim.files.ImageService;
import com.travelTim.user.UserEntity;
import com.travelTim.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class LodgingOfferService {

    private final LodgingOfferDAO lodgingOfferDAO;
    private final LegalPersonLodgingOfferDAO legalPersonLodgingOfferDAO;
    private final PhysicalPersonLodgingOfferDAO physicalPersonLodgingOfferDAO;
    private final LodgingOfferUtilityDAO lodgingOfferUtilityDAO;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    @Autowired
    public LodgingOfferService(LodgingOfferDAO lodgingOfferDAO, LegalPersonLodgingOfferDAO legalPersonLodgingOfferDAO,
                               PhysicalPersonLodgingOfferDAO physicalPersonLodgingOfferDAO, LodgingOfferUtilityDAO lodgingOfferUtilityDAO,
                               UserService userService, CategoryService categoryService, ImageService imageService) {
        this.lodgingOfferDAO = lodgingOfferDAO;
        this.legalPersonLodgingOfferDAO = legalPersonLodgingOfferDAO;
        this.physicalPersonLodgingOfferDAO = physicalPersonLodgingOfferDAO;
        this.lodgingOfferUtilityDAO = lodgingOfferUtilityDAO;
        this.userService = userService;
        this.categoryService = categoryService;
        this.imageService = imageService;
    }

    public Long addPhysicalPersonLodgingOffer(PhysicalPersonLodgingOfferEntity lodgingOffer){
        UserEntity loggedInUser = this.userService.findLoggedInUser();
        lodgingOffer.setUser(loggedInUser);
        CategoryEntity category = this.categoryService.findCategoryByName(CategoryType.lodging);
        lodgingOffer.setCategory(category);
        this.addLodgingOfferUtilities(lodgingOffer, lodgingOffer.getUtilities());
        return this.lodgingOfferDAO.save(lodgingOffer).getId();
    }

    public Long addLegalPersonLodgingOffer(LegalPersonLodgingOfferEntity lodgingOffer){
        UserEntity loggedInUser = this.userService.findLoggedInUser();
        lodgingOffer.setUser(loggedInUser);
        CategoryEntity category = this.categoryService.findCategoryByName(CategoryType.lodging);
        lodgingOffer.setCategory(category);
        this.addLodgingOfferUtilities(lodgingOffer, lodgingOffer.getUtilities());
        return this.lodgingOfferDAO.save(lodgingOffer).getId();
    }

    public LodgingOfferEntity findLodgingOfferEntityById(Long lodgingOfferId){
        return this.lodgingOfferDAO.findLodgingOfferEntityById(lodgingOfferId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Lodging offer with id: " + lodgingOfferId + " was not found")
        );
    }

    public void addLodgingOfferUtilities(LodgingOfferEntity lodgingOffer, Set<LodgingOfferUtilityEntity> lodgingOfferUtilities) {
        Set<LodgingOfferUtilityEntity> utilities = new HashSet<>();

        for (LodgingOfferUtilityEntity utility: lodgingOfferUtilities){
            if (this.lodgingOfferUtilityDAO.findLodgingOfferUtilityEntityByName(utility.getName()).isEmpty()){
                this.lodgingOfferUtilityDAO.save(utility);
                utilities.add(utility);
            } else {
                LodgingOfferUtilityEntity lodgingOfferUtility =
                        this.lodgingOfferUtilityDAO.findLodgingOfferUtilityEntityByName(utility.getName()).get();
                utilities.add(lodgingOfferUtility);
            }
        }

        lodgingOffer.setUtilities(utilities);
        this.lodgingOfferDAO.save(lodgingOffer);
    }

    public LegalPersonLodgingOfferBaseDetailsDTO findLegalPersonLodgingOfferById(Long offerId) {
        LegalPersonLodgingOfferEntity offer =  this.legalPersonLodgingOfferDAO.findLegalPersonLodgingOfferEntityById(offerId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Lodging offer of type legal person with id: " + offerId + " was not found")
                );
        LodgingDTOMapper mapper = new LodgingDTOMapper();
        return mapper.mapLegalLodgingOfferToBaseDetailsDTO(offer);
    }

    public LodgingOfferEntity findPhysicalPersonLodgingOfferById(Long offerId)  {
        return this.physicalPersonLodgingOfferDAO.findPhysicalPersonLodgingOfferEntityById(offerId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Lodging offer of type physical person with id: " + offerId + " was not found")
        );
    }

    public LodgingOfferPriceDTO getLodgingOfferPrice(Long offerId, Currency currency) {
        LodgingOfferEntity offer = this.findLodgingOfferEntityById(offerId);
        Float convertedPrice = offer.getPrice();
        // convert offer price to the selected currency
        try {
            CurrencyConverter currencyConverter = new CurrencyConverter();
            Float conversionRateFromRON = currencyConverter.getCurrencyConversionRate(Currency.RON.name(), currency.name());
            Float conversionRateFromEUR = currencyConverter.getCurrencyConversionRate(Currency.EUR.name(), currency.name());
            Float conversionRateFromGBP = currencyConverter.getCurrencyConversionRate(Currency.GBP.name(), currency.name());
            Float conversionRateFromUSD = currencyConverter.getCurrencyConversionRate(Currency.USD.name(), currency.name());
            if (offer.getCurrency() != currency) {
                switch (offer.getCurrency()) {
                    case RON:
                        convertedPrice = currencyConverter.getConvertedPrice(offer.getPrice(), conversionRateFromRON);
                        break;
                    case EUR:
                        convertedPrice = currencyConverter.getConvertedPrice(offer.getPrice(), conversionRateFromEUR);
                        break;
                    case GBP:
                        convertedPrice = currencyConverter.getConvertedPrice(offer.getPrice(), conversionRateFromGBP);
                        break;
                    case USD:
                        convertedPrice = currencyConverter.getConvertedPrice(offer.getPrice(), conversionRateFromUSD);
                        break;
                }
            }
        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not retrieve currency for monetary conversion");
        }
        return new LodgingOfferPriceDTO(convertedPrice, currency);
    }
}
