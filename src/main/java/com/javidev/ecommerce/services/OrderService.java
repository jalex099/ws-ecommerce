package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.*;
import com.javidev.ecommerce.repositories.OrderRepository;
import com.javidev.ecommerce.repositories.ProductSubOptionRepository;
import com.javidev.ecommerce.repositories.UserRepository;
import com.javidev.ecommerce.utils.Objects;
import com.javidev.ecommerce.utils.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductSubOptionRepository productSubOptionRepository;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public Map<String, Object> saveOrder(Order order) {
        try{
            //* Verify if the order exists
            if(order.getId() != null){
                Order orderDB = orderRepository.findById(order.getId()).orElse(null);
                if(orderDB == null){
                    order.setId(null);
                }
                else{
                    order.setDateCreated(orderDB.getDateCreated());
                    order.setStatus(orderDB.getStatus());
                    order.setCompanyId(orderDB.getCompanyId());
                }
            }
            //* If it doesn't exist, create it and set the date and status
            if(order.getId() == null){
                order.setDateCreated(new Date());
                order.setStatus("PEN");
                order.setCompanyId(Long.parseLong(Params.COMPANY_ID));
            }
            //* If has user, verify it and add it
            Long userId = Session.getAuthUserId();
            User user = userService.getUserById(userId);
            if(user != null) {
                order.setUserId(user);
            }
            List<OrderDetail> products = order.getProducts();
            order.clearProducts();
            for(OrderDetail product : products){
                List<OrderDetailOption> options = product.getOptions();
                product.clearOptions();
                for(OrderDetailOption option : options){
                    product.addOption(option);
                }
                order.addProduct(product);
            }
            order.setTotal(this.getTotal(products));
            order.setTotalDiscount(this.getTotalDiscount(order.getTotal()));
            order.setTotalTax(this.getTotalTax(order.getTotal()));
            order.setTotalShipping(this.getTotalShipping(order.getTotal(), order.getTotalDiscount(), order.getTotalTax()));
            Order savedOrder = orderRepository.save(order);
            return Objects.mapper(savedOrder);
        } catch (Exception e) {
            return null;
        }
    }

    public Order deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order == null) throw new RuntimeException("Order not found");
        order.setStatus("DEL");
        orderRepository.save(order);
        return order;
    }

    public Order checkout(Long id, Order order){
        Order orderDB = orderRepository.findById(id).orElse(null);
        if(orderDB == null) throw new RuntimeException("Order not found");
        if(!orderDB.getStatus().equals("PEN")) throw new RuntimeException("Order is not pending");
        Order mergedOrder = Objects.merge(order, orderDB);
        mergedOrder.setStatus("CON");
        mergedOrder.setDateConfirmed(new Date());
        mergedOrder.setTotal(this.getTotal(mergedOrder.getProducts()));
        mergedOrder.setTotalDiscount(this.getTotalDiscount(mergedOrder.getTotal()));
        mergedOrder.setTotalTax(this.getTotalTax(mergedOrder.getTotal()));
        mergedOrder.setTotalShipping(this.getTotalShipping(mergedOrder.getTotal(), mergedOrder.getTotalDiscount(), mergedOrder.getTotalTax()));
        orderRepository.save(mergedOrder);
        if(order.getCartCode() != null){
            cartService.setCartAsPaid(order.getCartCode());
        }
        return mergedOrder;
    }

    private double getTotal(List<OrderDetail> products) {
        double total = 0;
        for(OrderDetail product : products){
            total += product.getPrice() * product.getQuantity();
            List<OrderDetailOption> options = product.getOptions();
            for(OrderDetailOption option : options){
                //* Get the selected sub-option and add its aditional price to the total
                ProductSubOption subOptionSelected = option.getDetailSubOptionSelected();
                if(subOptionSelected == null) subOptionSelected = productSubOptionRepository.findById(option.getSelectedSubOption()).orElse(null);
                if(subOptionSelected != null){
                    total += subOptionSelected.getAditionalPrice();
                }
            }
        }
        return Double.parseDouble(df.format(total));
    }

    private double getTotalDiscount(double total) {
        return 0.00;
    }

    private double getTotalTax(double total) {
        return Double.parseDouble(df.format(total / (1 + Double.parseDouble(Params.TAX))));
    }

    private double getTotalShipping(double total, double discount, double tax) {
        return Double.parseDouble(df.format(total - (discount + tax)));
    }
}
