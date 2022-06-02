package com.travelTim.files;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.travelTim.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;
    private final UserService userService;
    private final ServletContext context;
    private final Storage storage;

    @Autowired
    public ImageController(ImageService imageService, UserService userService, ServletContext context, Storage storage) {
        this.imageService = imageService;
        this.userService = userService;
        this.context = context;
        this.storage = storage;
    }

    @PostMapping(path = "/user")
    public ResponseEntity<?> uploadProfileImage(
            @RequestParam(value = "image") Optional<MultipartFile> image) throws IOException {
        Long userId = this.userService.findLoggedInUser().getId();
        this.imageService.uploadUserImage(userId, image);
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
    public ResponseEntity<?> uploadBusinessImages(
            @RequestParam(value = "images") Optional<List<MultipartFile>> images,
            @PathVariable("businessId") Long businessId) throws IOException {
        this.imageService.uploadBusinessImages(businessId, images);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/business/{businessId}/front")
    @CrossOrigin
    public ResponseEntity<String> getBusinessFrontImage(
            @PathVariable("businessId") Long businessId){
        String businessImage = this.imageService.getBusinessFrontImage(businessId);
        return new ResponseEntity<>(businessImage, HttpStatus.OK);
    }

    @GetMapping(path = "/business/{businessId}/all")
    @CrossOrigin
    public ResponseEntity<List<String>> getBusinessImages(
            @PathVariable("businessId") Long businessId){
        List<String> businessImages = this.imageService.getBusinessImages(businessId);
        return new ResponseEntity<>(businessImages, HttpStatus.OK);
    }

    @GetMapping (path = "/business/{businessId}/all/names")
    public ResponseEntity<List<String>> getBusinessImagesNames(@PathVariable("businessId") Long businessId){
        List<String> businessImagesNames = imageService.getBusinessImagesNames(businessId);
        return new ResponseEntity<>(businessImagesNames, HttpStatus.OK);
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
    public ResponseEntity<List<String>> getOfferImages(
            @RequestParam(value = "offerType") String offerType,
            @PathVariable("offerId") Long offerId){
        List<String> offerImages = this.imageService.getOfferImages(offerType, offerId);
        return new ResponseEntity<>(offerImages, HttpStatus.OK);
    }

    @GetMapping(path = "/offer/{offerId}/base64")
    @CrossOrigin
    public ResponseEntity<List<String>> getOfferImagesBase64(
            @RequestParam(value = "offerType") String offerType,
            @PathVariable("offerId") Long offerId){
        List<String> offerImages = this.imageService.getOfferImagesBase64(offerType, offerId);
        return new ResponseEntity<>(offerImages, HttpStatus.OK);
    }


    @GetMapping(path = "/offer/{offerId}/names")
    public ResponseEntity<List<String>> getOfferImagesNames(
            @RequestParam(value = "offerType") String offerType,
            @PathVariable("offerId") Long offerId){
        List<String> offerImagesNames = this.imageService.getOfferImagesNames(offerType, offerId);
        return new ResponseEntity<>(offerImagesNames, HttpStatus.OK);
    }


}
