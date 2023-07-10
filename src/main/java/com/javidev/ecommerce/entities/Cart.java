package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javidev.ecommerce.config.Params;
import com.zaxxer.hikari.util.SuspendResumeLock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "carts")
@Where(clause = "company_id = " + Params.COMPANY_ID)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Getter
    @Setter
    private Long id;

    @Column(name = "code", nullable = false)
    @Getter
    @Setter
    private String code;

    @Column(name = "date", nullable = false)
    @Getter
    @Setter
    private Date date;

    @Column(name = "status", nullable = false)
    @Getter
    @Setter
    private String status;

    @Column(name = "visibility", nullable = false)
    @Getter
    @Setter
    private String visibility = "PUBLIC";


    @ManyToOne
    @JoinColumn(name = "user_id")
    @Setter
    private User userId;

    @Column(name = "company_id", nullable = false)
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long companyId;

    @OneToMany(mappedBy = "cartId", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order ASC")
    @Getter
    private List<CartDetail> products;

    @Transient
    @Setter
    private double total;

    public void addProduct(CartDetail product) {
        products.add(product);
        product.setCartId(this);
    }

    public void removeProduct(CartDetail product) {
        products.remove(product);
        product.setCartId(null);
    }

    public void clearProducts() {
        products = new ArrayList<>();
    }


    public Long getUserId() {
        return (userId != null) ? userId.getId() : null;
    }
}
