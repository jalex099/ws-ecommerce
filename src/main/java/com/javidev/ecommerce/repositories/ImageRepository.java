package com.javidev.ecommerce.repositories;

import com.javidev.ecommerce.entities.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ImageRepository extends CrudRepository<Image, Long> {

    @Query("FROM Image i WHERE (:type IS NULL OR i.type = :type) " +
            "AND (:reference IS NULL OR i.reference = :reference)")
    ArrayList<Image> findByTypeAndReference(String type, Long reference);
}
