package br.lostpets.project.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.lostpets.project.utils.GoogleDriveConfig;

/**
 * Service for handling image storage operations.
 * Centralizes Google Drive upload logic to eliminate code duplication.
 * 
 * Design Pattern: Facade Pattern - Provides a simplified interface for Google Drive operations
 */
@Service
public class ImageStorageService {
    
    private static final Logger logger = LoggerFactory.getLogger(ImageStorageService.class);
    private static final String DRIVE_URL_PREFIX = "https://drive.google.com/uc?id=";

    /**
     * Uploads an image file to Google Drive and returns the public URL.
     * 
     * @param file The image file to upload
     * @return Public URL of the uploaded image, or null if upload fails
     */
    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            logger.warn("Attempted to upload null or empty file");
            return null;
        }

        try {
            String fileId = GoogleDriveConfig.uploadFile(file);
            String url = DRIVE_URL_PREFIX + fileId;
            logger.info("Successfully uploaded image: {}", file.getOriginalFilename());
            return url;
        } catch (IOException e) {
            logger.error("IO error while uploading file: {}", file.getOriginalFilename(), e);
            return null;
        } catch (GeneralSecurityException e) {
            logger.error("Security error while uploading file: {}", file.getOriginalFilename(), e);
            return null;
        }
    }

    /**
     * Uploads the first non-empty image from an array of files.
     * 
     * @param files Array of image files
     * @return Public URL of the first uploaded image, or null if no valid file
     */
    public String uploadFirstImage(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            logger.warn("No files provided for upload");
            return null;
        }

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                return uploadImage(file);
            }
        }

        logger.warn("All provided files were empty");
        return null;
    }

    /**
     * Uploads multiple images and returns the URL of the first successful upload.
     * This maintains backward compatibility with existing code that processes multiple files
     * but only uses the first one.
     * 
     * @param files Array of image files
     * @return Public URL of the uploaded image, or null if no valid file
     */
    public String uploadPetImage(MultipartFile[] files) {
        return uploadFirstImage(files);
    }

    /**
     * Uploads a user profile image.
     * 
     * @param files Array of image files (typically only one for profile pictures)
     * @return Public URL of the uploaded image, or null if no valid file
     */
    public String uploadUserProfileImage(MultipartFile[] files) {
        return uploadFirstImage(files);
    }
}
