package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getOwnUser() {
        return new ResponseEntity<>(userService.getOwnUser(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody HashMap<String, Object> data) {
        try{
            userService.createUser(
                    data.get("firstName").toString(),
                    data.get("lastName").toString(),
                    data.get("email").toString(),
                    data.get("password").toString());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("error", "User not created");
            }}, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody HashMap<String, Object> data) {
        try{
            return new ResponseEntity<>(userService.auth(
                    data.get("email").toString(),
                    data.get("password").toString()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("error", "User not created");
            }}, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/auth/google")
    public ResponseEntity<?> authGoogle(@RequestParam String token) {
        try{
            System.out.println("token: " + token);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("error", "User not created");
            }}, HttpStatus.BAD_REQUEST);
        }
    }
}
