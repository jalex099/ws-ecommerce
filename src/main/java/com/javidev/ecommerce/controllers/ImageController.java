package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.Image;
import com.javidev.ecommerce.services.ImageService;
import com.javidev.ecommerce.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            BufferedImage bImg = ImageIO.read(image.getInputStream());
            HashMap<String, Object> imageData = imageService.uploadImage(image);
//            HashMap<String, Object> imageData = s3Service.putObject(image);
//            Image imageEntity = new Image();
//            imageEntity.setType(type);
//            imageEntity.setSize(size);
//            imageEntity.setWidth(Long.parseLong(String.valueOf(bImg.getWidth())));
//            imageEntity.setHeight(Long.parseLong(String.valueOf(bImg.getHeight())));
//            imageEntity.setWeight(Double.parseDouble(String.valueOf(image.getSize()/1024)));
//            imageEntity.setReference(reference);
//            imageEntity.setCompanyId(Long.parseLong(Params.COMPANY_ID));
//            imageEntity.setUrl((String) imageData.get("url"));
//            imageEntity.setKey((String) imageData.get("key"));
//            imageEntity.setExtension(image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".") + 1));
//
//            Image createdImage = imageService.saveImage(imageEntity);
//            return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
            return new ResponseEntity<>(imageData, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("error", "Image not saved");
            }}, HttpStatus.BAD_REQUEST);
        }


    }
}
