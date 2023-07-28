package com.javidev.ecommerce.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

@Service
public class OAuth2Services {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    public HashMap<String, Object> getAccessToken(String code){
        HashMap<String, Object> map = new HashMap<>();
        try{
            GoogleAuthorizationCodeTokenRequest tokenRequest = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    new GsonFactory(),
                    GOOGLE_CLIENT_ID,
                    GOOGLE_CLIENT_SECRET,
                    code,
                    "postmessage"
            );
            GoogleTokenResponse tokenResponse = tokenRequest.execute();
            map.put("id_token", tokenResponse.getIdToken());
            map.put("access_token", tokenResponse.getAccessToken());
            return map;
        } catch (Exception e){
            System.out.println("OAuth2Services.getAccessToken" + e.getMessage());
            return null;
        }
    }

    public HashMap<String, Object> getUserInfo(String token){
        HashMap<String, Object> map = new HashMap<>();
        try{
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null){
                GoogleIdToken.Payload payload = idToken.getPayload();
                map.put("email", payload.getEmail());
                map.put("name", payload.get("name"));
                map.put("picture", payload.get("picture"));
                map.put("given_name", payload.get("given_name"));
                map.put("family_name", payload.get("family_name"));
                map.put("locale", payload.get("locale"));
            }
            return map;
        } catch (Exception e){
            System.out.println("OAuth2Services.getUserInfo" + e.getMessage());
            return null;
        }
    }
}
