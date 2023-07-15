package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.User;
import com.javidev.ecommerce.repositories.UserRepository;
import com.javidev.ecommerce.security.WebSecurityConfig;
import com.javidev.ecommerce.utils.Objects;
import com.javidev.ecommerce.utils.Session;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getOwnUser() {
        Long userId = Session.getAuthUserId();
        User user = userRepository.findById(userId).orElse(null);
        Map<String, Object> userMap = Objects.mapper(user);
        userMap.remove("password");
        userMap.remove("platform");
        return userMap;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void createUser(String firstName, String lastName, String email, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        userRepository.save(firstName, lastName, email, encodedPassword, Long.parseLong(Params.COMPANY_ID));
    }

    public String auth(String email, String password) {
        return "adasd";
    }
}
