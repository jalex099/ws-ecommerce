package com.javidev.ecommerce.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.*;
import com.javidev.ecommerce.repositories.CartRepository;
import com.javidev.ecommerce.utils.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    public ArrayList<Map<String, Object>> getCartsByUserId(Long userId) {
        ArrayList<Map<String, Object>> carts = new ArrayList<>();
        ArrayList<Cart> cartsDB = cartRepository.findByUserId(userId);
        for (Cart cart : cartsDB) {
            carts.add(this.completeCart(cart.getId()));
        }
        return carts;
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Map<String, Object> saveCart(Cart cart) {
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
        //* Si viene con un usuario, se busca en la base de datos
        Long userId = Session.getAuthUserId();
        User user = userService.getUserById(userId);
        if(user != null) {
            cart.setUserId(user);
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
        Cart savedCart = cartRepository.save(cart);
        return this.completeCart(savedCart.getId());
    }

    public void deleteCart(Long id) {
        if(this.isOwner(id))
            cartRepository.deleteById(id);
    }

    public Cart toogleVisibility(Long id) {
        if(!this.isOwner(id)) return null;
        Cart cart = cartRepository.findById(id).orElse(null);
        if(cart == null) return null;
        cart.setVisibility( cart.getVisibility().equals("PRIVATE") ? "PUBLIC" : "PRIVATE" );
        return cartRepository.save(cart);
    }

    public Cart activate(Long id) {
        if(!this.isOwner(id)) return null;
        Cart cart = cartRepository.findById(id).orElse(null);
        if(cart == null) return null;
        cart.setStatus("ACT");
        return cartRepository.save(cart);
    }

    public Cart deactivate(Long id) {
        if(!this.isOwner(id)) return null;
        Cart cart = cartRepository.findById(id).orElse(null);
        if(cart == null) return null;
        cart.setStatus("INA");
        return cartRepository.save(cart);
    }

    public Cart clone(String code){
        Cart cartToClone = cartRepository.findByCode(code);
        if(cartToClone == null) return null;
        Long userId = Session.getAuthUserId();
        if(cartToClone.getUserId().equals(userId)) return null;
        User user = userService.getUserById(Session.getAuthUserId());
        cartToClone.setUserId(user);
        return cartRepository.save(cartToClone);
    }

    //* Private method to check if the user is the owner of the cart
    private Boolean isOwner(Long id) {
        Long userId = Session.getAuthUserId();
        Cart cart = cartRepository.findById(id).orElse(null);
        if(cart == null) return false;
        //* If the cart doesn't have a user, it's not private
        if(cart.getUserId() == null) return true;
        return cart.getUserId().equals(userId);
    }

    private double getTotal(Cart cart) {
        double total = 0.0;
        for (CartDetail detail : cart.getProducts()) {
            Product product = productService.getProduct(detail.getProductId());
            if(product == null) continue;
            total += product.getPrice() * detail.getQuantity();
        }
        return total;
    }

    private Map<String, Object> completeCart(Long cartId){
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Map<String, Object> map = mapper.convertValue(cart, Map.class);
        map.put("total", this.getTotal(cart));
        return map;
    }
}

