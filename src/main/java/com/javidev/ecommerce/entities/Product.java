package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javidev.ecommerce.config.Params;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Where(clause = "company_id = " + Params.COMPANY_ID + " AND is_active = true")
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

    @Column(name = "order_pos")
    @Setter
    @Getter
    private Integer orderPos;

    @Column(name = "is_active")
    @Setter
    @Getter
    private Boolean isActive;

    @Column(name = "category_id", nullable = false)
    @Setter
    @Getter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long categoryId;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
    @OrderBy("order_pos ASC")
    @Getter
    @Setter
    private List<ProductOption> options;


}
