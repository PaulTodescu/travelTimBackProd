package com.travelTim.attractions;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

public class AttractionDTOMapper {

    private final ModelMapper modelMapper;

    public AttractionDTOMapper() {
        this.modelMapper = new ModelMapper();
    }

    public AttractionOfferDTO mapAttractionOfferToDTO(AttractionOfferEntity attractionOffer){
        return modelMapper.map(attractionOffer, AttractionOfferDTO.class);
    }

    public Set<AttractionOfferDTO> mapAttractionOffersToDTOs(Set<AttractionOfferEntity> attractionOffers){
        return attractionOffers.stream().map(this::mapAttractionOfferToDTO).collect(Collectors.toSet());
    }

    public AttractionOfferBaseDetailsDTO mapAttractionOfferToBaseDetailsDTO(AttractionOfferEntity attractionOffer){
        return modelMapper.map(attractionOffer, AttractionOfferBaseDetailsDTO.class);
    }

    public Set<AttractionOfferBaseDetailsDTO> mapAttractionOffersToBaseDetailsDTOs(Set<AttractionOfferEntity> attractionOffers){
        return attractionOffers.stream().map(this::mapAttractionOfferToBaseDetailsDTO).collect(Collectors.toSet());
    }

    public AttractionOfferEditDTO mapAttractionOfferToEditDTO(AttractionOfferEntity attractionOffer){
        return modelMapper.map(attractionOffer, AttractionOfferEditDTO.class);
    }
}
