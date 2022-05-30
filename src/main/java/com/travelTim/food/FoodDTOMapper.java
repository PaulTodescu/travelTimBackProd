package com.travelTim.food;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FoodDTOMapper {

    private final ModelMapper modelMapper;

    public FoodDTOMapper() {
        this.modelMapper = new ModelMapper();
    }

    public FoodOfferDTO mapFoodOfferToDTO(FoodOfferEntity offer){
        return this.modelMapper.map(offer, FoodOfferDTO.class);
    }

    public Set<FoodOfferDTO> mapFoodOffersToDTOs(Set<FoodOfferEntity> offers){
        return offers.stream().map(this::mapFoodOfferToDTO).collect(Collectors.toSet());
    }

    public List<FoodOfferDTO> mapFoodOffersToDTOs(List<FoodOfferEntity> offers){
        return offers.stream().map(this::mapFoodOfferToDTO).collect(Collectors.toList());
    }

    public FoodOfferBaseDetailsDTO mapFoodOfferToBaseDetailsDTO(FoodOfferEntity offer){
        return this.modelMapper.map(offer, FoodOfferBaseDetailsDTO.class);
    }

    public Set<FoodOfferBaseDetailsDTO> mapFoodOffersToBaseDetailsDTOs(Set<FoodOfferEntity> offers){
        return offers.stream().map(this::mapFoodOfferToBaseDetailsDTO).collect(Collectors.toSet());
    }

    public FoodOfferEditDTO mapFoodOfferToEditDTO(FoodOfferEntity offer){
        return modelMapper.map(offer, FoodOfferEditDTO.class);
    }

    public FoodOfferDetailsDTO mapFoodOfferToDetailsDTO(FoodOfferEntity offer){
        return modelMapper.map(offer, FoodOfferDetailsDTO.class);
    }

    public FoodOfferIdMenuImageDTO mapFoodOfferToIdMenuImageDTO(FoodOfferEntity offer){
        return this.modelMapper.map(offer, FoodOfferIdMenuImageDTO.class);
    }
}
