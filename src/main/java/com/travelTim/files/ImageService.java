package com.travelTim.files;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class ImageService {

    private final ServletContext context;
    private final ImageUtils imageUtils;

    @Autowired
    public ImageService(ServletContext context, ImageUtils imageUtils) {
        this.context = context;
        this.imageUtils = imageUtils;
    }

    public void uploadUserImage(Long id, Optional<MultipartFile> image) {
        String uploadPath = this.imageUtils.getUploadPath(ImageType.USER, id);
        if (uploadPath == null){
            return;
        }

        File directory = this.imageUtils.createImageDirectory(uploadPath);
        if (directory == null){
            return;
        }

        int nrFiles = Objects.requireNonNull(directory.list()).length;
        if (image.isPresent()){
            MultipartFile imageFile = image.get();
            try {
                Path copyLocation = Paths
                        .get(uploadPath + File.separator + StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename())));
                if (nrFiles != 0) {
                    FileUtils.cleanDirectory(directory);
                }
                Files.copy(imageFile.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Could not store file " + imageFile.getOriginalFilename());
            }
        } else {
            this.uploadDefaultImage(id, ImageType.USER);
        }
    }

    public void uploadBusinessImages(Long businessId, Optional<List<MultipartFile>> images) {
        String uploadPath = this.imageUtils.getUploadPath(ImageType.BUSINESS, businessId);
        if (uploadPath == null){
            return;
        }

        File directory = this.imageUtils.createImageDirectory(uploadPath);
        if (directory == null){
            return;
        }

        int nrFiles = Objects.requireNonNull(directory.list()).length;
        if (nrFiles != 0) {
            try {
                FileUtils.cleanDirectory(directory);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        if (images.isPresent()) {
            for (MultipartFile image : images.get()) {
                try {
                    Path copyLocation = Paths
                            .get(uploadPath + File.separator + StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
                    Files.copy(image.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Could not store file " + image.getOriginalFilename());
                }
            }
        } else {
            this.uploadDefaultImage(businessId, ImageType.BUSINESS);
        }
    }

    public void uploadDefaultImage(Long id, ImageType imageType) {
        String uploadPath = this.imageUtils.getUploadPath(imageType, id);
        if (uploadPath == null){
            return;
        }

        File directory = this.imageUtils.createImageDirectory(uploadPath);
        if (directory == null){
            return;
        }

        String defaultImagePath = this.imageUtils.getDefaultImagePath(imageType);

        try {
            InputStream defaultImage = new FileInputStream(context.getRealPath(defaultImagePath));
            Path copyLocation = Paths
                    .get(uploadPath + File.separator + StringUtils.cleanPath("default.png"));
            Files.copy(defaultImage, copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store file");
        }
    }

    public void uploadOfferImages(Long offerId, List<MultipartFile> offerImages, String offerType) {
        String uploadPath = this.imageUtils.getOfferUploadPath(offerType, offerId);
        if (uploadPath == null){
            return;
        }

        File directory = this.imageUtils.createImageDirectory(uploadPath);
        if (directory == null){
            return;
        }
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e){
            e.printStackTrace();
        }

        for (MultipartFile image: offerImages) {
            try {
                Path copyLocation = Paths
                        .get(uploadPath + File.separator + StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
                Files.copy(image.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Could not store file " + image.getOriginalFilename());
            }
        }
    }

    public List<String> getOfferImagesNames(String offerType, Long offerId) {
        String path = this.imageUtils.getOfferUploadPath(offerType, offerId);
        if (path == null){
            return Collections.emptyList();
        }
        return this.imageUtils.getImagesNames(path);
    }

    public String getUserImage(Long userId){
        String imagePath = this.imageUtils.getUploadPath(ImageType.USER, userId);
        if (imagePath == null){
            return null;
        }
        return this.imageUtils.getImage(imagePath);
    }

    public List<String> getBusinessImages(Long businessId){
        String path = this.imageUtils.getUploadPath(ImageType.BUSINESS, businessId);
        return this.imageUtils.getImages(path);
    }

    public String getBusinessFrontImage(Long businessId){
        return this.getBusinessImages(businessId).get(0);
    }

    public List<String> getBusinessImagesNames(Long businessId){
        String path = this.imageUtils.getUploadPath(ImageType.BUSINESS, businessId);
        return this.imageUtils.getImagesNames(path);
    }

    public List<String> getOfferImages(String offerType, Long offerId){
        String path = this.imageUtils.getOfferUploadPath(offerType, offerId);
        return this.imageUtils.getImages(path);
    }


    public String getOfferFrontImage(String offerType, Long offerId){
        return this.getOfferImages(offerType, offerId).get(0);
    }

    public String getImagePath(Long id, ImageType imageType) {
        String imagePath = this.imageUtils.getUploadPath(imageType, id);
        if (imagePath == null){
            return null;
        }
        File imageDirectory = new File(imagePath);
        File[] imageDirectoryContent = imageDirectory.listFiles();
        if (imageDirectoryContent != null) {
            if (imageDirectoryContent.length != 1) {
                return null;
            }
            return imageDirectoryContent[0].getName();
        }
        return null;
    }

}
