package com.javidev.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
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

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Setter
    @Getter
    private Category category;

}
