package com.travelTim.user;

import org.modelmapper.ModelMapper;

public class UserDTOMapper {

    private final ModelMapper modelMapper;

    public UserDTOMapper() {
        this.modelMapper = new ModelMapper();
    }

    public UserContactDTO mapUserToUserContactDetailsDTO(UserEntity userEntity){
        return this.modelMapper.map(userEntity, UserContactDTO.class);
    }

    public UserDetailsDTO mapUserToUserDetailsDTO(UserEntity userEntity){
        return this.modelMapper.map(userEntity, UserDetailsDTO.class);
    }
}
