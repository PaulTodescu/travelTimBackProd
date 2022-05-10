package com.travelTim.activities;

import com.travelTim.attractions.AttractionOfferBaseDetailsDTO;
import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.attractions.AttractionOfferForBusinessPageDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

public class ActivityDTOMapper {

    private final ModelMapper modelMapper;

    public ActivityDTOMapper() {
        this.modelMapper = new ModelMapper();
    }

    public ActivityOfferDTO mapActivityOfferToDTO(ActivityOfferEntity offer){
        return this.modelMapper.map(offer, ActivityOfferDTO.class);
    }

    public Set<ActivityOfferDTO> mapActivityOffersToDTOs(Set<ActivityOfferEntity> offers){
        return offers.stream().map(this::mapActivityOfferToDTO).collect(Collectors.toSet());
    }

    public ActivityOfferBaseDetailsDTO mapActivityOfferToBaseDetailsDTO(ActivityOfferEntity offer){
        return modelMapper.map(offer, ActivityOfferBaseDetailsDTO.class);
    }

    public Set<ActivityOfferBaseDetailsDTO> mapActivityOffersToBaseDetailsDTOs(Set<ActivityOfferEntity> offers){
        return offers.stream().map(this::mapActivityOfferToBaseDetailsDTO).collect(Collectors.toSet());
    }

    public ActivityOfferEditDTO mapActivityOfferToEditDTO(ActivityOfferEntity offer){
        return modelMapper.map(offer, ActivityOfferEditDTO.class);
    }

    public ActivityOfferDetailsDTO mapActivityOfferToDetailsDTO(ActivityOfferEntity offer){
        return modelMapper.map(offer, ActivityOfferDetailsDTO.class);
    }

    public ActivityOfferForBusinessPageDTO mapActivityOfferForBusinessPageDTO(ActivityOfferEntity offer){
        return this.modelMapper.map(offer, ActivityOfferForBusinessPageDTO.class);
    }

    public Set<ActivityOfferForBusinessPageDTO> mapActivityOffersForBusinessPageDTO(Set<ActivityOfferEntity> offers){
        return offers.stream().map(this::mapActivityOfferForBusinessPageDTO).collect(Collectors.toSet());
    }

}
