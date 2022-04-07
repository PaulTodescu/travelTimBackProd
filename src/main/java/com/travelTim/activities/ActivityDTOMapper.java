package com.travelTim.activities;

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
}
