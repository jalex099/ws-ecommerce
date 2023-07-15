package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.Order;
import com.javidev.ecommerce.entities.OrderDetail;
import com.javidev.ecommerce.entities.OrderDetailOption;
import com.javidev.ecommerce.entities.User;
import com.javidev.ecommerce.repositories.OrderRepository;
import com.javidev.ecommerce.repositories.UserRepository;
import com.javidev.ecommerce.utils.Objects;
import com.javidev.ecommerce.utils.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Map<String, Object> saveOrder(Order order) {
        try{
            //* Verify if the order exists
            if(order.getId() != null){
                Order orderDB = orderRepository.findById(order.getId()).orElse(null);
                if(orderDB == null){
                    order.setId(null);
                }
                else{
                    order.setDate(orderDB.getDate());
                    order.setStatus(orderDB.getStatus());
                    order.setCompanyId(orderDB.getCompanyId());
                }
            }
            //* If it doesn't exist, create it and set the date and status
            if(order.getId() == null){
                order.setDate(new Date());
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
            order.setTotalDiscount(this.getTotalDiscount(products));
            order.setTotalTax(this.getTotalTax(products));
            order.setTotalShipping(this.getTotalShipping(products));
            Order savedOrder = orderRepository.save(order);
            return Objects.mapper(savedOrder);
        } catch (Exception e) {
            return null;
        }
    }

    private double getTotal(List<OrderDetail> products) {
        double total = 0;
        for(OrderDetail product : products){
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    private double getTotalDiscount(List<OrderDetail> products) {
        double totalDiscount = 0;
        for(OrderDetail product : products){
            totalDiscount += 0;
        }
        return totalDiscount;
    }

    private double getTotalTax(List<OrderDetail> products) {
        double totalTax = 0;
        for(OrderDetail product : products){
            totalTax += 0;
        }
        return totalTax;
    }

    private double getTotalShipping(List<OrderDetail> products) {
        double totalShipping = 0;
        for(OrderDetail product : products){
            totalShipping += product.getPrice() * product.getQuantity();
        }
        return totalShipping;
    }
}