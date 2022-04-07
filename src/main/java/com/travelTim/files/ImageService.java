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

    @Autowired
    public ImageService(ServletContext context) {
        this.context = context;
    }

    public void uploadImage(Long id, Optional<MultipartFile> image, ImageType imageType) {

        String uploadPath = this.getUploadPath(imageType, id);
        if (uploadPath == null){
            return;
        }

        File directory = this.createImageDirectory(uploadPath);
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
            uploadDefaultImage(id, imageType);
        }
    }

    public void uploadDefaultImage(Long id, ImageType imageType) { // triggered when user registers

        String uploadPath = this.getUploadPath(imageType, id);
        if (uploadPath == null){
            return;
        }

        File directory = this.createImageDirectory(uploadPath);
        if (directory == null){
            return;
        }

        String defaultImagePath = getDefaultImagePath(imageType);

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

    public void uploadOfferImages(Long offerId, List<MultipartFile> offerImages, String offerType) throws IOException {
        String uploadPath = this.getOfferUploadPath(offerType, offerId);
        if (uploadPath == null){
            return;
        }

        File directory = this.createImageDirectory(uploadPath);
        if (directory == null){
            return;
        }

        int nrFiles = Objects.requireNonNull(directory.list()).length;
        if (nrFiles != 0) {
            FileUtils.cleanDirectory(directory);
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

    private String getUploadPath(ImageType imageType, Long id){
        String uploadPath = null;
        switch (imageType){
            case USER:
                uploadPath = context.getRealPath("/images/user/" + id);
                break;
            case BUSINESS:
                uploadPath = context.getRealPath("/images/business/" + id);
                break;
        }
        return uploadPath;
    }

    private String getOfferUploadPath(String offerType, Long offerId){
        String basePath = "/images/offer";
        String uploadPath;
        switch (offerType){
            case "lodging":
                uploadPath = context.getRealPath(basePath + "/lodging/" + offerId);
                break;
            case "food":
                uploadPath = context.getRealPath(basePath + "/food/" + offerId);
                break;
            case "attractions":
                uploadPath = context.getRealPath(basePath + "/attractions/" + offerId);
                break;
            case "activities":
                uploadPath = context.getRealPath(basePath + "/activities/" + offerId);
                break;
            default:
                return null;
        }
        return uploadPath;
    }

    public String getDefaultImagePath(ImageType imageType){
        switch (imageType){
            case USER:
                return "/images/user/default.png";
            case BUSINESS:
                return "/images/business/default.png";
            default:
                return null;
        }
    }

    private File createImageDirectory(String uploadPath) {
        File directory = new File(uploadPath);
        if(!directory.exists()) {
            if (!directory.mkdirs())
                return null;
        }
        return directory;
    }

    public String getUserImage(Long userId){
        String imagePath = this.getUploadPath(ImageType.USER, userId);
        if (imagePath == null){
            return null;
        }
        return this.getImage(imagePath);
    }

    public String getBusinessImage(Long businessId){
        String imagePath = this.getUploadPath(ImageType.BUSINESS, businessId);
        if (imagePath == null){
            return null;
        }
        return this.getImage(imagePath);
    }

    public String getOfferImage(String offerType, Long offerId){
        String imagesPath = this.getOfferUploadPath(offerType, offerId);
        return this.getImage(imagesPath);
    }

    public String getImage(String imagePath) {
        String image = null;
        File imageDirectory = new File(imagePath);
        File[] imageDirectoryContent = imageDirectory.listFiles();
        if (imageDirectoryContent != null){
            File imageFile = imageDirectoryContent[0];
            String encodeBase64;
            try {
                String extension = FilenameUtils.getExtension(imageFile.getName());
                FileInputStream fileInputStream = new FileInputStream(imageFile);
                byte [] bytes = new byte[(int) imageFile.length()];
                fileInputStream.read(bytes);
                encodeBase64 = Base64.getEncoder().encodeToString(bytes);
                image = "data:image/" + extension + ";base64," + encodeBase64;
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public List<String> getOfferImages(String offerType, Long offerId){
        String imagePath = this.getOfferUploadPath(offerType, offerId);
        if (imagePath == null){
            return null;
        }

        List<String> offerImages = new ArrayList<>();
        File imageDirectory = new File(imagePath);
        File[] imageDirectoryContent = imageDirectory.listFiles();

        if (imageDirectoryContent != null){
            for (File imageFile: imageDirectoryContent){
                String encodeBase64;
                String image;
                try {
                    String extension = FilenameUtils.getExtension(imageFile.getName());
                    FileInputStream fileInputStream = new FileInputStream(imageFile);
                    byte [] bytes = new byte[(int) imageFile.length()];
                    fileInputStream.read(bytes);
                    encodeBase64 = Base64.getEncoder().encodeToString(bytes);
                    image = "data:image/" + extension + ";base64," + encodeBase64;
                    offerImages.add(image);
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return offerImages;

    }

    public String getImagePath(Long id, ImageType imageType) {
        String imagePath = this.getUploadPath(imageType, id);
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



    public void deleteImage(Long id, ImageType imageType){
        String imagePath = this.getUploadPath(imageType, id);
        if (imagePath == null){
            return;
        }
        File directory = new File(imagePath);
        if (directory.exists()){
            try {
                FileUtils.deleteDirectory(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteOfferImages(String offerType, Long offerId) {
        String imagesPath = this.getOfferUploadPath(offerType, offerId);
        if (imagesPath == null){
            return;
        }

        File directory = new File(imagesPath);
        if (directory.exists()){
            try {
                FileUtils.deleteDirectory(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
