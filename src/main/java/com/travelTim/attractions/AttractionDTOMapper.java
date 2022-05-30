package com.travelTim.attractions;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AttractionDTOMapper {

    private final ModelMapper modelMapper;

    public AttractionDTOMapper() {
        this.modelMapper = new ModelMapper();
    }

    public AttractionOfferDTO mapAttractionOfferToDTO(AttractionOfferEntity offer){
        return modelMapper.map(offer, AttractionOfferDTO.class);
    }

    public Set<AttractionOfferDTO> mapAttractionOffersToDTOs(Set<AttractionOfferEntity> offers){
        return offers.stream().map(this::mapAttractionOfferToDTO).collect(Collectors.toSet());
    }

    public List<AttractionOfferDTO> mapAttractionOffersToDTOs(List<AttractionOfferEntity> offers){
        return offers.stream().map(this::mapAttractionOfferToDTO).collect(Collectors.toList());
    }

    public AttractionOfferBaseDetailsDTO mapAttractionOfferToBaseDetailsDTO(AttractionOfferEntity offer){
        return modelMapper.map(offer, AttractionOfferBaseDetailsDTO.class);
    }

    public Set<AttractionOfferBaseDetailsDTO> mapAttractionOffersToBaseDetailsDTOs(Set<AttractionOfferEntity> offers){
        return offers.stream().map(this::mapAttractionOfferToBaseDetailsDTO).collect(Collectors.toSet());
    }

    public AttractionOfferEditDTO mapAttractionOfferToEditDTO(AttractionOfferEntity offer){
        return modelMapper.map(offer, AttractionOfferEditDTO.class);
    }

    public AttractionOfferDetailsDTO mapAttractionOfferToDetailsDTO(AttractionOfferEntity offer){
        return modelMapper.map(offer, AttractionOfferDetailsDTO.class);
    }

    public AttractionOfferForBusinessPageDTO mapAttractionOfferForBusinessPageDTO(AttractionOfferEntity offer){
        return this.modelMapper.map(offer, AttractionOfferForBusinessPageDTO.class);
    }

    public Set<AttractionOfferForBusinessPageDTO> mapAttractionOffersForBusinessPageDTO(Set<AttractionOfferEntity> offers){
        return offers.stream().map(this::mapAttractionOfferForBusinessPageDTO).collect(Collectors.toSet());
    }
}
