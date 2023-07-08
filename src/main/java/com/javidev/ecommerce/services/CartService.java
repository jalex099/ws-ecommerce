package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.Cart;
import com.javidev.ecommerce.entities.CartDetail;
import com.javidev.ecommerce.entities.CartDetailOption;
import com.javidev.ecommerce.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public ArrayList<Cart> getCarts() {
        return (ArrayList<Cart>) cartRepository.findAll();
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart saveCart(Cart cart) {
        //* If it exists, get it and set the code and date and company from the DB
        if(cart.getId() != null) {
            Cart cartDB = cartRepository.findById(cart.getId()).orElse(null);
            if(cartDB == null) {
                cart.setId(null);
            }
            else {
                cart.setCode(cartDB.getCode());
                cart.setDate(cartDB.getDate());
                cart.setCompanyId(cartDB.getCompanyId());
            }
        }
        //* If it doesn't exist, create it and set the code and date and company
        if(cart.getId() == null ){
            cart.setCode(UUID.randomUUID().toString());
            cart.setDate(new Date());
            cart.setCompanyId(Long.parseLong(Params.COMPANY_ID));
        }
        List<CartDetail> products = cart.getProducts();
        cart.clearProducts();
        for (CartDetail product : products) {
            List<CartDetailOption> options = product.getOptions();
            product.clearOptions();
            for (CartDetailOption option : options) {
                option.setCartDetailId(product);
                product.addOption(option);
            }
            cart.addProduct(product);
        }
        return cartRepository.save(cart);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    public Cart toogleVisibility(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if(cart != null) {
            cart.setVisibility( cart.getVisibility().equals("PRIVATE") ? "PUBLIC" : "PRIVATE" );
            return cartRepository.save(cart);
        }
        return null;
    }

    public Cart activate(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if(cart != null) {
            cart.setStatus("ACT");
            return cartRepository.save(cart);
        }
        return null;
    }

    public Cart deactivate(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if(cart != null) {
            cart.setStatus("INA");
            return cartRepository.save(cart);
        }
        return null;
    }
}

