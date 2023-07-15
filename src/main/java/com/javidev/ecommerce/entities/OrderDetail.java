package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    @Setter
    private Order orderId;

    @Column(name = "product_id", nullable = false)
    @Getter
    @Setter
    private Long productId;

    @Column(name = "quantity", nullable = false)
    @Getter
    @Setter
    private int quantity;

    @Column(name = "price", nullable = false)
    @Getter
    @Setter
    private double price;

    @Column(name = "order_pos")
    @Getter
    @Setter
    private int order;

    @OneToMany(mappedBy = "orderDetailId", cascade = CascadeType.ALL)
    @OrderBy("optionId ASC")
    @Getter
    private List<OrderDetailOption> options;

    public void addOption(OrderDetailOption option) {
        options.add(option);
        option.setOrderDetailId(this);
    }

    public void removeOption(OrderDetailOption option) {
        options.remove(option);
        option.setOrderDetailId(null);
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
