package com.travelTim.business;

import com.travelTim.location.City;

public class BusinessDTO {
    private Long id;
    private String name;
    private String address;
    private City city;

    public BusinessDTO() {
    }

    public BusinessDTO(Long id, String name, String address, City city) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}


