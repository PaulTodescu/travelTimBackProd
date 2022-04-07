package com.travelTim.location;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/location")
public class LocationController {

    @GetMapping(path = "/cities")
    public ResponseEntity<List<City>> getAllCities(){
        LinkedList<City> cities = new LinkedList<>(Arrays.asList(City.values()));
        cities.sort((o1, o2) -> o1.toString().compareToIgnoreCase(o2.toString()));
        cities.remove(City.Timisoara);
        cities.addFirst(City.Timisoara);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
}
