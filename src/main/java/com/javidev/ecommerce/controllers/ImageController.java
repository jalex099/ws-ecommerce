package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.Image;
import com.javidev.ecommerce.services.ImageService;
import com.javidev.ecommerce.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private S3Service s3Service;

    @GetMapping
    public ResponseEntity<?> getImages(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "reference", required = false) Long reference
    ) {
        ArrayList<Image> images = imageService.getImages(type, reference);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveImage(
            @RequestParam(value = "image") MultipartFile image,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "size") String size,
            @RequestParam(value = "reference") Long reference
    ) {
        try {
            HashMap<String, Object> savedImage = imageService.uploadImage(image, size);
            Image imageEntity = new Image(
                    type,
                    StringUtils.getFilenameExtension(image.getOriginalFilename()),
                    size,
                    reference,
                    (String) savedImage.get("url"),
                    (String) savedImage.get("key"),
                    Long.parseLong(String.valueOf(savedImage.get("height"))),
                    Long.parseLong(String.valueOf(savedImage.get("width"))),
                    Long.parseLong(Params.COMPANY_ID)
            );
            Image createdImage = imageService.saveImage(imageEntity);
            return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("error", "Image not saved");
            }}, HttpStatus.BAD_REQUEST);
        }


    }
}
