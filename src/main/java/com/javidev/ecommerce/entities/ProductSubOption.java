package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javidev.ecommerce.config.Params;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "products_sub_options")
public class ProductSubOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @ManyToOne(targetEntity = Option.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "option_id", insertable = false, updatable = false)
    @Getter
    @Setter
    private Option details;

    @Column(name = "option_id", nullable = false)
    @Setter
    private Long optionId;

    @Column(name = "aditional_price", nullable = false)
    @Getter
    @Setter
    private Double aditionalPrice;

    @Column(name = "order_pos")
    @Getter
    @Setter
    private Integer orderPos;

    @ManyToOne
    @JoinColumn(name = "product_option_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    @Setter
    private ProductOption productOptionId;


}
