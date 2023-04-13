package com.javidev.ecommerce.services;

import com.javidev.ecommerce.entities.Image;
import com.javidev.ecommerce.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public ArrayList<Image> getImages(
            String type,
            Long reference
    ) {
        return imageRepository.findByTypeAndReference(type, reference);
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }
}
