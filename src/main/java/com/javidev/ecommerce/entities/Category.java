package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @Column(name = "is_active")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean isActive;

//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    @Getter
//    @Setter
//    private Set<Product> products = new HashSet<>();


}
