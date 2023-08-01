package com.javidev.ecommerce.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.javidev.ecommerce.services.OAuth2Services;
import com.javidev.ecommerce.services.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OAuth2Services oAuth2Services;

    @GetMapping
    public ResponseEntity<?> getOwnUser() {
        return new ResponseEntity<>(userService.getOwnUser(), HttpStatus.OK);
    }
//    @PostMapping
//    public ResponseEntity<?> createUser(@RequestBody HashMap<String, Object> data) {
//        try{
//            userService.createUser(
//                    data.get("firstName").toString(),
//                    data.get("lastName").toString(),
//                    data.get("email").toString(),
//                    data.get("password").toString());
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new HashMap<String, String>() {{
//                put("error", "User not created");
//            }}, HttpStatus.BAD_REQUEST);
//        }
//    }
    @PostMapping("/auth/google")
    public ResponseEntity<?> authGoogle(@RequestBody HashMap<String, Object> data) {
        try{
            String code = (String) data.get("code");
            if (code == null) throw new Exception("Code is null");
            HashMap<String, Object> tokenResponse = oAuth2Services.getAccessToken(code);
            if (tokenResponse == null) throw new IOException("Invalid code");
            String idToken = (String) tokenResponse.get("id_token");
            GoogleIdToken.Payload payload = oAuth2Services.getUserInfo(idToken);
            userService.createOrUpdateFromOAuth2(payload);

            HashMap<String, Object> map = new HashMap<>();
            map.put("id_token", idToken);
            map.put("access_token", tokenResponse.get("access_token"));
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            HashMap<String, String> map = new HashMap<>();
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }
}
