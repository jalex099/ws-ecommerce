package com.javidev.ecommerce.utils;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.JsonFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class Token {
    private static String GOOGLE_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    public void setGoogleClientId(String googleClientId) {
        GOOGLE_CLIENT_ID = googleClientId;
    }

    public static UsernamePasswordAuthenticationToken getAuthentication (String token){
        try{
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                .build();
            GoogleIdToken idToken = verifier.verify(token);

            if( idToken == null) throw new RuntimeException("Invalid token");
            GoogleIdToken.Payload payload = idToken.getPayload();
            return new UsernamePasswordAuthenticationToken(payload.getEmail(), null, Collections.emptyList());
        }
        catch (JwtException e){
            return null;
        }
        catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
