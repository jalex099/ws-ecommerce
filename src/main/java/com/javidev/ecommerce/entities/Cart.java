package com.javidev.ecommerce.entities;

import com.javidev.ecommerce.config.Params;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.Date;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;

    @Column(name = "date", nullable = false)
    @Getter
    @Setter
    private Date date;

    @Column(name = "status", nullable = false)
    @Getter
    @Setter
    private String status;
}
