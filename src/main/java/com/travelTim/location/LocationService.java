package com.travelTim.location;

import com.travelTim.user.UserEntity;
import com.travelTim.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocationService {

    private final LocationDAO locationDAO;
    private final UserService userService;

    @Autowired
    public LocationService(LocationDAO locationDAO, UserService userService) {
        this.locationDAO = locationDAO;
        this.userService = userService;
    }

    public LocationEntity addVisitedLocationForUser(LocationEntity visitedLocation) {
        UserEntity user = this.userService.findLoggedInUser();
        user.getVisitedLocations().add(visitedLocation);
        visitedLocation.setUser(user);
        return this.locationDAO.save(visitedLocation);
    }

    public LocationEntity findLocationById(Long locationId) {
        return this.locationDAO.findLocationEntityById(locationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Location with id: " + locationId + " was not found"));
    }

    public List<LocationEntity> getVisitedLocationsForUser() {
        UserEntity user = this.userService.findLoggedInUser();
        return user.getVisitedLocations().stream()
                .sorted(Comparator.comparing(LocationEntity::getAddress))
                .collect(Collectors.toList());
    }

    public void removeVisitedLocationForUser(Long locationId) {
        UserEntity user = this.userService.findLoggedInUser();
        LocationEntity location = this.findLocationById(locationId);
        user.getVisitedLocations().remove(location);
        location.setUser(null);
        this.locationDAO.deleteLocationEntityById(locationId);
    }
}
