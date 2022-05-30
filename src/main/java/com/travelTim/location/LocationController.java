package com.travelTim.location;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(path = "/cities")
    public ResponseEntity<List<City>> getAllCities() {
        LinkedList<City> cities = new LinkedList<>(Arrays.asList(City.values()));
        cities.sort((o1, o2) -> o1.toString().compareToIgnoreCase(o2.toString()));
        cities.remove(City.Timisoara);
        cities.addFirst(City.Timisoara);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @PostMapping(path = "/visited/user")
    public ResponseEntity<LocationEntity> addVisitedLocationForUser(
            @RequestBody LocationEntity location) {
        LocationEntity savedLocation = this.locationService.addVisitedLocationForUser(location);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }

    @GetMapping(path = "/visited/user")
    public ResponseEntity<List<LocationEntity>> getVisitedLocationsForUser() {
        List<LocationEntity> visitedLocations = this.locationService.getVisitedLocationsForUser();
        return new ResponseEntity<>(visitedLocations, HttpStatus.OK);
    }

    @DeleteMapping(path = "/visited/{locationId}/user")
    public ResponseEntity<Void> removeVisitedLocationForUser(
            @PathVariable("locationId") Long locationId) {
        this.locationService.removeVisitedLocationForUser(locationId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
