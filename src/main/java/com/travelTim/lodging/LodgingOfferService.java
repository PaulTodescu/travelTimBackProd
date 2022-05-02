package com.travelTim.lodging;

import com.travelTim.category.CategoryService;
import com.travelTim.category.CategoryType;
import com.travelTim.currency.Currency;
import com.travelTim.currency.CurrencyConverter;
import com.travelTim.files.ImageUtils;
import com.travelTim.user.UserEntity;
import com.travelTim.user.UserService;
import com.travelTim.category.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
    private final ImageUtils imageUtils;

    @Autowired
    public LodgingOfferService(LodgingOfferDAO lodgingOfferDAO, LegalPersonLodgingOfferDAO legalPersonLodgingOfferDAO,
                               PhysicalPersonLodgingOfferDAO physicalPersonLodgingOfferDAO, LodgingOfferUtilityDAO lodgingOfferUtilityDAO,
                               UserService userService, CategoryService categoryService, ImageUtils imageUtils) {
        this.lodgingOfferDAO = lodgingOfferDAO;
        this.legalPersonLodgingOfferDAO = legalPersonLodgingOfferDAO;
        this.physicalPersonLodgingOfferDAO = physicalPersonLodgingOfferDAO;
        this.lodgingOfferUtilityDAO = lodgingOfferUtilityDAO;
        this.userService = userService;
        this.categoryService = categoryService;
        this.imageUtils = imageUtils;
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
            if (this.lodgingOfferUtilityDAO.
                    findLodgingOfferUtilityEntityByName(utility.getName()).isEmpty()){
                this.lodgingOfferUtilityDAO.save(utility);
                utilities.add(utility);
            } else {
                LodgingOfferUtilityEntity lodgingOfferUtility =
                        this.lodgingOfferUtilityDAO
                                .findLodgingOfferUtilityEntityByName(utility.getName()).get();
                utilities.add(lodgingOfferUtility);
            }
        }
        lodgingOffer.setUtilities(utilities);
        this.lodgingOfferDAO.save(lodgingOffer);
    }

    public void deleteLodgingOfferUtilities(LodgingOfferEntity offer){
        for (Iterator<LodgingOfferUtilityEntity> iterator =
                offer.getUtilities().iterator(); iterator.hasNext();){
            LodgingOfferUtilityEntity utility = iterator.next();
            utility.getLodgingOffers().remove(offer);
            if (utility.getLodgingOffers().size() == 0){
                this.lodgingOfferUtilityDAO.deleteLodgingOfferUtilityEntityById(utility.getId());
            }
            iterator.remove();
        }
    }

    public LegalPersonLodgingOfferBaseDetailsDTO findLegalPersonLodgingOfferById(Long offerId) {
        LegalPersonLodgingOfferEntity offer =
                this.legalPersonLodgingOfferDAO.findLegalPersonLodgingOfferEntityById(offerId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Lodging offer of type legal person with id: " + offerId + " was not found")
                );
        LodgingDTOMapper mapper = new LodgingDTOMapper();
        return mapper.mapLegalLodgingOfferToBaseDetailsDTO(offer);
    }

    public LegalPersonLodgingOfferEditDTO findLegalPersonLodgingOfferForEdit(Long offerId) {
        LodgingOfferEntity offer =
                this.legalPersonLodgingOfferDAO.findLegalPersonLodgingOfferEntityById(offerId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Lodging offer of type legal person with id: " + offerId + " was not found")
                );
        LodgingDTOMapper mapper = new LodgingDTOMapper();
        return mapper.mapLodgingOfferToLegalPersonOfferEditDTO(offer);
    }

    public LodgingOfferEntity findPhysicalPersonLodgingOfferById(Long offerId) {
        PhysicalPersonLodgingOfferEntity offer = this.physicalPersonLodgingOfferDAO
                .findPhysicalPersonLodgingOfferEntityById(offerId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Lodging offer of type physical person with id: " + offerId +
                                " was not found")
        );
        if (offer.getCurrency() == Currency.EUR){
           // try {
                //CurrencyConverter currencyConverter = new CurrencyConverter();
                //Float conversionRateFromEUR = currencyConverter
                //        .getCurrencyConversionRate(Currency.EUR.name(), Currency.RON.name());
                //offer.setPrice(currencyConverter.getConvertedPrice(offer.getPrice(), conversionRateFromEUR));
                offer.setCurrency(Currency.RON);
//            } catch (IOException ioException) {
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
//                        "Could not retrieve currency for monetary conversion");
//            }
        }
        return offer;
    }

    public PhysicalPersonLodgingOfferEditDTO findPhysicalPersonLodgingOfferForEdit(Long offerId) {
        LodgingOfferEntity offer = this.physicalPersonLodgingOfferDAO.findPhysicalPersonLodgingOfferEntityById(offerId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Lodging offer of type physical person with id: " + offerId + " was not found")
                );
        LodgingDTOMapper mapper = new LodgingDTOMapper();
        return mapper.mapLodgingOfferToPhysicalPersonOfferEditDTO(offer);
    }

    public void editLegalPersonLodgingOffer(LegalPersonLodgingOfferEditDTO offerToSave,
                                            Long offerId){
        LegalPersonLodgingOfferEntity offer =
                this.legalPersonLodgingOfferDAO.findLegalPersonLodgingOfferEntityById(offerId)
                        .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Lodging offer of type legal person with id: " + offerId +
                                        " was not found")
                        );
        offer.setNrRooms(offerToSave.getNrRooms());
        offer.setNrBathrooms(offerToSave.getNrBathrooms());
        offer.setNrSingleBeds(offerToSave.getNrSingleBeds());
        offer.setNrDoubleBeds(offerToSave.getNrDoubleBeds());
        offer.setFloor(offerToSave.getFloor());
        offer.setPrice(offerToSave.getPrice());
        offer.setCurrency(offerToSave.getCurrency());
        offer.setDescription(offerToSave.getDescription());
        offer.setBusiness(offerToSave.getBusiness());
        this.addLodgingOfferUtilities(offer, offerToSave.getUtilities());
        this.lodgingOfferDAO.save(offer);
    }

    public void editPhysicalPersonLodgingOffer(PhysicalPersonLodgingOfferEditDTO offerToSave,
                                            Long offerId){
        PhysicalPersonLodgingOfferEntity offer =
                this.physicalPersonLodgingOfferDAO.findPhysicalPersonLodgingOfferEntityById(offerId)
                        .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Lodging offer of type legal person with id: " + offerId +
                                        " was not found")
                        );
        offer.setTitle(offerToSave.getTitle());
        offer.setAddress(offerToSave.getAddress());
        offer.setCity(offerToSave.getCity());
        offer.setNrRooms(offerToSave.getNrRooms());
        offer.setNrBathrooms(offerToSave.getNrBathrooms());
        offer.setNrSingleBeds(offerToSave.getNrSingleBeds());
        offer.setNrDoubleBeds(offerToSave.getNrDoubleBeds());
        offer.setFloor(offerToSave.getFloor());
        offer.setPrice(offerToSave.getPrice());
        offer.setCurrency(offerToSave.getCurrency());
        offer.setDescription(offerToSave.getDescription());
        this.addLodgingOfferUtilities(offer, offerToSave.getUtilities());
        this.lodgingOfferDAO.save(offer);
    }

    public void deleteLodgingOffer(Long offerId) {
        LodgingOfferEntity offer = this.findLodgingOfferEntityById(offerId);
        this.deleteLodgingOfferUtilities(offer);
        this.imageUtils.deleteOfferImages("lodging", offerId);
        this.lodgingOfferDAO.deleteLodgingOfferEntityById(offerId);
    }

}
