package com.travelTim.food;

import org.modelmapper.ModelMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class FoodDTOMapper {

    private final ModelMapper modelMapper;

    public FoodDTOMapper() {
        this.modelMapper = new ModelMapper();
    }

    public FoodOfferDTO mapFoodOfferToDTO(FoodOfferEntity foodOffer){
        return this.modelMapper.map(foodOffer, FoodOfferDTO.class);
    }

    public Set<FoodOfferDTO> mapFoodOffersToDTOs(Set<FoodOfferEntity> foodOffers){
        return foodOffers.stream().map(this::mapFoodOfferToDTO).collect(Collectors.toSet());
    }
}
