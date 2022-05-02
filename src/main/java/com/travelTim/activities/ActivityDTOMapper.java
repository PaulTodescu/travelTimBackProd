package com.travelTim.activities;

import com.travelTim.attractions.AttractionOfferBaseDetailsDTO;
import com.travelTim.attractions.AttractionOfferEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

public class ActivityDTOMapper {

    private final ModelMapper modelMapper;

    public ActivityDTOMapper() {
        this.modelMapper = new ModelMapper();
    }

    public ActivityOfferDTO mapActivityOfferToDTO(ActivityOfferEntity activityOffer){
        return this.modelMapper.map(activityOffer, ActivityOfferDTO.class);
    }

    public Set<ActivityOfferDTO> mapActivityOffersToDTOs(Set<ActivityOfferEntity> activityOffers){
        return activityOffers.stream().map(this::mapActivityOfferToDTO).collect(Collectors.toSet());
    }

    public ActivityOfferBaseDetailsDTO mapActivityOfferToBaseDetailsDTO(ActivityOfferEntity activityOffer){
        return modelMapper.map(activityOffer, ActivityOfferBaseDetailsDTO.class);
    }

    public Set<ActivityOfferBaseDetailsDTO> mapActivityOffersToBaseDetailsDTOs(Set<ActivityOfferEntity> activityOffers){
        return activityOffers.stream().map(this::mapActivityOfferToBaseDetailsDTO).collect(Collectors.toSet());
    }

    public ActivityOfferEditDTO mapActivityOfferToEditDTO(ActivityOfferEntity activityOffer){
        return modelMapper.map(activityOffer, ActivityOfferEditDTO.class);
    }

}
