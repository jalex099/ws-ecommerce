package com.javidev.ecommerce.utils;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.JsonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class Token {
    //* Its from this web https://www.devglan.com/online-tools/hmac-sha256-online with WS-ECOMMERCE as plain text and WS-ECOMMERCE as secret key
    private static String ACCESS_TOKEN_SECRET = "c80ab0cf0951c75f526bae1a7a2a7a71055ece14747c8686f2996b54bd966153";

    private static Long ACCESS_TOKEN_EXPIRATION = 86400000L;

    private static String GOOGLE_CLIENT_ID = "394101880841-slcv06f3d8s3v9fspicvdnqnp26hs95r.apps.googleusercontent.com";



    public static String createToken(String idUser){
        long expirationTime = ACCESS_TOKEN_EXPIRATION * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        return Jwts.builder()
                .setSubject(idUser)
                .setExpiration(expirationDate)
//                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication (String token){
        try{
//            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
//                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
//                .build();
//            GoogleIdToken idToken = verifier.verify(token);
//            if( idToken == null) throw new RuntimeException("Invalid token");
//            GoogleIdToken.Payload payload = idToken.getPayload();
//            String userId = payload.getSubject();
//            return new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String idUser = claims.getSubject();
            return new UsernamePasswordAuthenticationToken(idUser, null, Collections.emptyList());
        }
        catch (JwtException e){
            return null;
        }
//        catch (GeneralSecurityException | IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
