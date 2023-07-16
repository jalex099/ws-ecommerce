package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javidev.ecommerce.config.Params;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Where(clause = "company_id = " + Params.COMPANY_ID)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Getter
    @Setter
    private Long id;

    @Column(name = "date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Getter
    @Setter
    private Date date;

    @Column(name = "status", nullable = false)
    @Getter
    @Setter
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Setter
    private User userId;

    @Column(name = "first_name", nullable = false)
    @Getter
    @Setter
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Getter
    @Setter
    private String lastName;

    @Column(name = "email", nullable = false)
    @Getter
    @Setter
    private String email;

    @Column(name = "phone")
    @Getter
    @Setter
    private String phone;

    @Column(name = "address", nullable = false)
    @Getter
    @Setter
    private String address;

    @Column(name = "city", nullable = false)
    @Getter
    @Setter
    private String city;

    @Column(name = "state", nullable = false)
    @Getter
    @Setter
    private String state;

    @Column(name = "country", nullable = false)
    @Getter
    @Setter
    private String country;

    @Column(name="total", columnDefinition = "double default 0")
    @Getter
    @Setter
    private double total;

    @Column(name="total_discount", columnDefinition = "double default 0")
    @Getter
    @Setter
    private double totalDiscount;

    @Column(name="total_tax", columnDefinition = "double default 0")
    @Getter
    @Setter
    private double totalTax;

    @Column(name="total_shipping", columnDefinition = "double default 0")
    @Getter
    @Setter
    private double totalShipping;

    @Column(name= "payment_method", nullable = false)
    @Getter
    @Setter
    private String paymentMethod;

    @Column(name= "payment_status", nullable = false)
    @Getter
    @Setter
    private String paymentStatus = "PEN";

    @Column(name= "card_number")
    @Getter
    @Setter
    private String cardNumber;

    @Column(name= "pos_id")
    @Getter
    @Setter
    private String posId;

    @Column(name = "company_id", nullable = false)
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long companyId;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order ASC")
    @Getter
    private List<OrderDetail> products;

    public void addProduct(OrderDetail product) {
        products.add(product);
        product.setOrderId(this);
    }

    public void removeProduct(OrderDetail product) {
        products.remove(product);
        product.setOrderId(null);
    }
    public void clearProducts() {
        products = new ArrayList<>();
    }
}
