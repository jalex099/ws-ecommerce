package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.Image;
import com.javidev.ecommerce.entities.MomeCloud;
import com.javidev.ecommerce.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.*;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;


    @Value("${mome.cloud.api.urlImages}")
    private String cloudApiUrlImages;

    public ArrayList<Image> getImages(
            String type,
            Long reference
    ) {
        return imageRepository.findByTypeAndReference(type, reference);
    }

    public HashMap<String, Object> uploadImage(MultipartFile image) throws IOException {
        MomeCloud momeCloud = new MomeCloud();
        byte[] imageBytes = Base64.getEncoder().encode(image.getBytes());
        String base64Image = new String(imageBytes);
//        String extension = StringUtils.getFilenameExtension(image.getOriginalFilename());
        String key = String.format("%s_%s", UUID.randomUUID(), Params.COMPANY_ID);
        momeCloud.setFileBase64(base64Image);
        momeCloud.setFilename(key);
        momeCloud.setOriginalFilename(image.getOriginalFilename());
        momeCloud.setSize("large");
        RestTemplate restTemplate = new RestTemplate();
        try{
            ResponseEntity<HashMap> response = restTemplate.postForEntity(cloudApiUrlImages, momeCloud, HashMap.class);
            HashMap<String, Object> imageData = new HashMap<>();
            imageData.put("key", response.getBody().get("key"));
            imageData.put("url", response.getBody().get("url"));
            return imageData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }
}
