package com.javidev.ecommerce.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.javidev.ecommerce.security.AuthCredentials;
import com.javidev.ecommerce.utils.Token;
import com.javidev.ecommerce.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static String GOOGLE_CLIENT_ID = "394101880841-slcv06f3d8s3v9fspicvdnqnp26hs95r.apps.googleusercontent.com";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            //* Get the token from the request
            AuthCredentials authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);

            //* Verify the token
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                    .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                    .build();
            GoogleIdToken idToken = verifier.verify(authCredentials.getCode());
            //* If the token is invalid, throw an exception
            if (idToken == null) throw new RuntimeException("Invalid token");
            //* If the token is valid, get the user email
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                    email,
                    "",
                    Collections.emptyList()
            );
            System.out.println("attemptAuthentication" + usernamePAT.getPrincipal());
            return getAuthenticationManager().authenticate(usernamePAT);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication" + authResult.getPrincipal());
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token = Token.createToken(userDetails.getId().toString());

        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
