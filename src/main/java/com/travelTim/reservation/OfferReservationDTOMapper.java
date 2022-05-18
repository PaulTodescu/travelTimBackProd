package com.travelTim.reservation;

import org.modelmapper.ModelMapper;

public class OfferReservationDTOMapper {

    private ModelMapper mapper;

    public OfferReservationDTOMapper() {
        this.mapper = new ModelMapper();
    }

    public OfferReservationDetailsDTO mapReservationToDetailsDTO(OfferReservationEntity reservation){
        return this.mapper.map(reservation, OfferReservationDetailsDTO.class);
    }
}
