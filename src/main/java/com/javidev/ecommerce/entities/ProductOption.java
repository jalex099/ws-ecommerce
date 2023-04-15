package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javidev.ecommerce.config.Params;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products_options")
@Where(clause = "company_id = " + Params.COMPANY_ID + " AND is_active = true")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "name", nullable = false)
    @Getter
    @Setter
    private String name;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Column(name = "aditional_price", nullable = false)
    @Getter
    @Setter
    private Double aditionalPrice;

    @Column(name = "order")
    @Getter
    @Setter
    private Integer order;

    @Column(name = "is_active")
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isActive;

    @Column(name = "product_id", nullable = false)
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long productId;

    @Column(name = "company_id", nullable = false)
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long companyId;


    @OneToMany(mappedBy = "productOptionId", cascade = CascadeType.ALL)
    @OrderBy("order ASC")
    @Getter
    @Setter
    private Set<ProductSubOption> subOptions = new HashSet<>();
}
