package com.travelTim.business;

import org.modelmapper.ModelMapper;

public class BusinessDTOMapper {

    private final ModelMapper modelMapper;

    public BusinessDTOMapper() {
        this.modelMapper = new ModelMapper();
    }

    public BusinessDTO mapBusinessToDTO(BusinessEntity business){
        return this.modelMapper.map(business, BusinessDTO.class);
    }

    public BusinessDetailsDTO mapBusinessToDetailsDTO(BusinessEntity business){
        return this.modelMapper.map(business, BusinessDetailsDTO.class);
    }
}
