package com.travelTim.files;

import com.travelTim.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;
    private final UserService userService;

    @Autowired
    public ImageController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @PostMapping(path = "/user")
    public ResponseEntity<?> uploadProfileImage(
            @RequestParam(value = "image") Optional<MultipartFile> image) {
        Long userId = this.userService.findLoggedInUser().getId();
        this.imageService.uploadImage(userId, image, ImageType.USER);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/user")
    @CrossOrigin
    public ResponseEntity<String> getUserImageForLoggedInUser(){
        Long userId = this.userService.findLoggedInUser().getId();
        String profileImage = this.imageService.getUserImage(userId);
        return new ResponseEntity<>(profileImage, HttpStatus.OK);
    }

    @GetMapping(path = "/user/{userId}")
    @CrossOrigin
    public ResponseEntity<String> getUserImage(@PathVariable("userId") Long userId){
        String profileImage = this.imageService.getUserImage(userId);
        return new ResponseEntity<>(profileImage, HttpStatus.OK);
    }

    @PostMapping(path = "/business/{businessId}")
    public ResponseEntity<?> addBusinessImage(
            @RequestParam(value = "image") Optional<MultipartFile> image,
            @PathVariable("businessId") Long businessId) {
        this.imageService.uploadImage(businessId, image, ImageType.BUSINESS);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/business/{businessId}")
    @CrossOrigin
    public ResponseEntity<String> getBusinessImage(
            @PathVariable("businessId") Long businessId){
        String businessImage = this.imageService.getBusinessImage(businessId);
        return new ResponseEntity<>(businessImage, HttpStatus.OK);
    }

    @GetMapping (path = "/business/{businessId}/name")
    @ResponseBody
    public String getBusinessImagePath(@PathVariable("businessId") Long businessId){
        return imageService.getImagePath(businessId, ImageType.BUSINESS);
    }

    @PostMapping(path = "/offer/{offerId}")
    public ResponseEntity<?> addOfferImages(
            @PathVariable("offerId") Long offerId,
            @RequestParam(value = "offerType") String offerType,
            @RequestParam(value = "images") List<MultipartFile> images) throws IOException {
        this.imageService.uploadOfferImages(offerId, images, offerType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/offer/{offerId}")
    @CrossOrigin
    public ResponseEntity<List<String>> getOfferImagesImage(
            @RequestParam(value = "offerType") String offerType,
            @PathVariable("offerId") Long offerId){
        List<String> offerImages = this.imageService.getOfferImages(offerType, offerId);
        return new ResponseEntity<>(offerImages, HttpStatus.OK);
    }

}
