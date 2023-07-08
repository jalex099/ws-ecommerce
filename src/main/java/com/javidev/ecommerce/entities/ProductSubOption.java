package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javidev.ecommerce.config.Params;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "products_sub_options")
@Where(clause = "company_id = " + Params.COMPANY_ID)
public class ProductSubOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false)
    @Getter
    @Setter
    private Option details;

    @Column(name = "aditional_price", nullable = false)
    @Getter
    @Setter
    private Double aditionalPrice;

    @Column(name = "order_pos")
    @Getter
    @Setter
    private Integer orderPos;

    @Column(name = "product_option_id", nullable = false)
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long productOptionId;

    @Column(name = "company_id", nullable = false)
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long companyId;
}
