package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javidev.ecommerce.config.Params;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "users")
@Where(clause = "company_id = " + Params.COMPANY_ID + " AND is_active = true")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Getter
    @Setter
    private Long id;

    @Column(name = "first_name", nullable = false)
    @Getter
    @Setter
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Getter
    @Setter
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @Getter
    @Setter
    private String email;

    @Column(name = "password", nullable = false)
    @Getter
    @Setter
    private String password;

    @Column(name = "platform")
    @Getter
    @Setter
    private String platform;

    @Column(name = "is_active", nullable = false)
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean isActive;
}
