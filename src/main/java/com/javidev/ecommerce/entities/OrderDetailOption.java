package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders_details_options")
public class OrderDetailOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_detail_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    @Setter
    private OrderDetail orderDetailId;

    @Column(name = "product_option_id", nullable = false)
    @Getter
    @Setter
    private Long optionId;

    @Column(name = "product_sub_option_id", nullable = false)
    @Getter
    @Setter
    private Long selectedSubOption;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartDetailOption )) return false;
        return id != null && id.equals(((CartDetailOption) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
