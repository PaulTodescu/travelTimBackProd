package com.travelTim.files;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ImageUtils {

    private final ServletContext context;

    @Autowired
    public ImageUtils(ServletContext context) {
        this.context = context;
    }

    public String getUploadPath(ImageType imageType, Long id){
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

    public String getOfferUploadPath(String offerType, Long offerId){
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

    public File createImageDirectory(String uploadPath) {
        File directory = new File(uploadPath);
        if(!directory.exists()) {
            if (!directory.mkdirs())
                return null;
        }
        return directory;
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

    public List<String> getImages(String path) {
        List<String> images = new ArrayList<>();
        File imageDirectory = new File(path);
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
                    images.add(image);
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return images;
    }

    public List<String> getImagesNames(String path){
        File imageDirectory = new File(path);
        List<String> imageNames = new ArrayList<>();
        File[] images = imageDirectory.listFiles();
        if (images != null) {
            for(File image: images){
                imageNames.add(image.getName());
            }
        }
        return imageNames;
    }

    public String getImagePath(Long id, ImageType imageType) {
        String path = this.getUploadPath(imageType, id);
        if (path == null){
            return null;
        }
        File imageDirectory = new File(path);
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
        String path = this.getUploadPath(imageType, id);
        if (path == null){
            return;
        }
        this.deleteDirectory(path);
    }

    public void deleteOfferImages(String offerType, Long offerId) {
        String path = this.getOfferUploadPath(offerType, offerId);
        if (path == null) {
            return;
        }
        this.deleteDirectory(path);
    }

    public void deleteDirectory(String path) {
        File directory = new File(path);
        if (directory.exists()) {
            try {
                FileUtils.deleteDirectory(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}
