package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javidev.ecommerce.config.Params;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products_options")
@Where(clause = "is_active = true")
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

    @Column(name = "order_pos")
    @Getter
    @Setter
    private Integer orderPos;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    @Getter
    @Setter
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    @Setter
    private Product productId;


    @OneToMany(mappedBy = "productOptionId", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order_pos ASC")
    @Getter
    @Setter
    private List<ProductSubOption> subOptions;

    public void clearSubOptions() {
        subOptions = new ArrayList<>();
    }

    public void addSubOption(ProductSubOption subOption) {
        subOptions.add(subOption);
        subOption.setProductOptionId(this);
    }

    public void removeSubOption(ProductSubOption subOption) {
        subOptions.remove(subOption);
        subOption.setProductOptionId(null);
    }
}
