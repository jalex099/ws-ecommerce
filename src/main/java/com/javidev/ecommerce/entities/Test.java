package com.javidev.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "test")
public class Test {
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
}
