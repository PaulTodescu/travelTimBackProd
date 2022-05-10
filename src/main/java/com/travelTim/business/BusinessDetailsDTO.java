package com.travelTim.business;

import com.travelTim.location.City;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BusinessDetailsDTO {

    private Long id;
    private String name;
    private String address;
    private City city;
    private String websiteLink;
    private String facebookLink;
    private String twitterLink;
    private Set<BusinessDaySchedule> schedule;

    public BusinessDetailsDTO() {
    }

    public BusinessDetailsDTO(Long id, String name, String address, City city, String websiteLink,
                              String facebookLink, String twitterLink,
                              Set<BusinessDaySchedule> schedule) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.websiteLink = websiteLink;
        this.facebookLink = facebookLink;
        this.twitterLink = twitterLink;
        this.schedule = schedule;
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

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public Set<BusinessDaySchedule> getSchedule() {
        return schedule.stream()
                .sorted(Comparator.comparing(
                        daySchedule -> List.of("Monday","Tuesday","Wednesday", "Thursday",
                                        "Friday","Saturday","Sunday")
                                .indexOf(daySchedule.getDay().toString())))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void setSchedule(Set<BusinessDaySchedule> schedule) {
        this.schedule = schedule;
    }
}
