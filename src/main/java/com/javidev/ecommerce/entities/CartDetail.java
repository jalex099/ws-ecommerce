package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts_details")
public class CartDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    @Setter
    private Cart cartId;

    @Column(name = "product_id", nullable = false)
    @Getter
    @Setter
    private Long productId;

    @Column(name = "quantity", nullable = false)
    @Getter
    @Setter
    private int quantity;

    @Column(name = "order_pos")
    @Getter
    @Setter
    private int order;

    @OneToMany(mappedBy = "cartDetailId", cascade = CascadeType.ALL)
    @OrderBy("optionId ASC")
    @Getter
    private List<CartDetailOption> options;

    public void addOption(CartDetailOption option) {
        options.add(option);
        option.setCartDetailId(this);
    }

    public void removeOption(CartDetailOption option) {
        options.remove(option);
        option.setCartDetailId(null);
    }

    public void clearOptions() {
        options = new ArrayList<>();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartDetail)) return false;
        return id != null && id.equals(((CartDetail) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
