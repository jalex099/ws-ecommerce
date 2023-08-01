package com.javidev.ecommerce.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.User;
import com.javidev.ecommerce.repositories.UserRepository;
import com.javidev.ecommerce.utils.Objects;
import com.javidev.ecommerce.utils.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getOwnUser() {
        String idGoogleUser = Session.getAuthUserGoogleId();
        User user= userRepository.findOneByEmail(idGoogleUser).orElse(null);
        if (user == null) {
            return null;
        }
        Map<String, Object> userMap = Objects.mapper(user);
        userMap.remove("id");
        userMap.remove("companyId");
        return userMap;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

//    public void createUser(String firstName, String lastName, String email, String password) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(password);
//        userRepository.save(firstName, lastName, email, encodedPassword, Long.parseLong(Params.COMPANY_ID));
//    }

    public void createOrUpdateFromOAuth2(GoogleIdToken.Payload payload){
        Optional<User> user = userRepository.findOneByEmail(payload.getEmail());
        //Update
        if(user.isPresent()){
            User userToUpdate = user.get();
            userToUpdate.setFirstName((String) payload.get("given_name"));
            userToUpdate.setLastName((String) payload.get("family_name"));
            userToUpdate.setPicture((String) payload.get("picture"));
            userToUpdate.setCompanyId(Long.parseLong(Params.COMPANY_ID));
            userRepository.save(userToUpdate);
        }
        // Create
        else {
            User userToCreate = new User();
            userToCreate.setFirstName((String) payload.get("given_name"));
            userToCreate.setLastName((String) payload.get("family_name"));
            userToCreate.setEmail(payload.getEmail());
            userToCreate.setPicture((String) payload.get("picture"));
            userToCreate.setCompanyId(Long.parseLong(Params.COMPANY_ID));
            userRepository.save(userToCreate);
        }
    }

}
