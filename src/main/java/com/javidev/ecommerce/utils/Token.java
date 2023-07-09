package com.javidev.ecommerce.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.*;

public class Token {
    //* Its from this web https://www.devglan.com/online-tools/hmac-sha256-online with WS-ECOMMERCE as plain text and WS-ECOMMERCE as secret key
    private static String ACCESS_TOKEN_SECRET = "c80ab0cf0951c75f526bae1a7a2a7a71055ece14747c8686f2996b54bd966153";

    private static Long ACCESS_TOKEN_EXPIRATION = 86400000L;

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
    }
}
