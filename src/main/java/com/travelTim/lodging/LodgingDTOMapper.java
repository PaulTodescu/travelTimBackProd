package com.travelTim.lodging;

import org.modelmapper.ModelMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class LodgingDTOMapper {

    private final ModelMapper modelMapper;

    public LodgingDTOMapper() {
        this.modelMapper = new ModelMapper();
    }

    public LodgingOfferDTO mapLodgingOfferToDTO(LodgingOfferEntity lodgingOffer){
        return this.modelMapper.map(lodgingOffer, LodgingOfferDTO.class);
    }

    public LegalPersonLodgingOfferBaseDetailsDTO mapLegalLodgingOfferToBaseDetailsDTO(LegalPersonLodgingOfferEntity lodgingOffer){
        return this.modelMapper.map(lodgingOffer, LegalPersonLodgingOfferBaseDetailsDTO.class);
    }

    public Set<LodgingOfferDTO> mapLodgingOffersToDTOs(Set<LodgingOfferEntity> lodgingOffers){
        return lodgingOffers.stream().map(this::mapLodgingOfferToDTO).collect(Collectors.toSet());
    }

    public LegalPersonLodgingOfferDetailsDTO mapLegalLodgingOfferToLodgingDetailsDTO(LegalPersonLodgingOfferEntity lodgingOffer){
        return this.modelMapper.map(lodgingOffer, LegalPersonLodgingOfferDetailsDTO.class);
    }

    public Set<LegalPersonLodgingOfferDetailsDTO> mapLodgingOffersToLodgingDetailsDTOs(Set<LegalPersonLodgingOfferEntity> lodgingOffers){
        return lodgingOffers.stream().map(this::mapLegalLodgingOfferToLodgingDetailsDTO).collect(Collectors.toSet());
    }

    public LodgingOfferBaseDetailsDTO mapLodgingOfferToBaseDetailsDTO(LodgingOfferEntity lodgingOffer){
        return this.modelMapper.map(lodgingOffer, LodgingOfferBaseDetailsDTO.class);
    }

    public Set<LodgingOfferBaseDetailsDTO> mapLodgingOffersToBaseDetailsDTOs(Set<LodgingOfferEntity> lodgingOffers){
        return lodgingOffers.stream().map(this::mapLodgingOfferToBaseDetailsDTO).collect(Collectors.toSet());
    }

    public LodgingOfferPriceDTO mapLodgingOfferToPriceDTO(LodgingOfferEntity lodgingOffer){
        return this.modelMapper.map(lodgingOffer, LodgingOfferPriceDTO.class);
    }
}
