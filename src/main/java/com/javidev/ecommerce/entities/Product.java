package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javidev.ecommerce.config.Params;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "products")
@Where(clause = "company_id = " + Params.COMPANY_ID)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Setter
    @Getter
    private Long id;

    @Column(name = "name", nullable = false)
    @Setter
    @Getter
    private String name;

    @Column(name = "description")
    @Setter
    @Getter
    private String description;

    @Column(name = "price", nullable = false)
    @Setter
    @Getter
    private Double price;

    @Column(name = "is_active")
    @Setter
    @Getter
    private Boolean isActive;

    @Column(name = "category_id", nullable = false)
    @Setter
    @Getter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long categoryId;

}
