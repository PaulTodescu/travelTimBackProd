package com.travelTim.reservation;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OfferReservationDTOMapper {

    private final ModelMapper mapper;

    public OfferReservationDTOMapper() {
        this.mapper = new ModelMapper();
    }

    public OfferReservationDetailsDTO mapReservationToDetailsDTO(OfferReservationEntity reservation){
        return this.mapper.map(reservation, OfferReservationDetailsDTO.class);
    }
}
