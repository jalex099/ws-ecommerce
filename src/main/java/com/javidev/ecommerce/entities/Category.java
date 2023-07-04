package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javidev.ecommerce.config.Params;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
@Entity
@Table(name = "categories")
@Where(clause = "company_id = " + Params.COMPANY_ID)
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
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isActive;

    @Column(name = "company_id", nullable = false)
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long companyId;


//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    @Getter
//    @Setter
//    private Set<Product> products = new HashSet<>();


}
