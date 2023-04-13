package com.javidev.ecommerce.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Service
public class S3Service {

    private final static String BUCKET_NAME = "ecommerce.testing";

    @Autowired
    private AmazonS3 s3Client;

    public HashMap<String, Object> putObject(MultipartFile file){
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String key = String.format("%s.%s", UUID.randomUUID(), extension);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        try{
            PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, key, file.getInputStream(), metadata);
            s3Client.putObject(request);
            HashMap<String, Object> data = new HashMap<>();
            String url = s3Client.getUrl(BUCKET_NAME, key).toString();
            data.put("url", url);
            data.put("key", key);
            return data;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


}
