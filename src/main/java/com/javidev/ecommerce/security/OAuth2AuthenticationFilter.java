package com.javidev.ecommerce.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFilter extends OncePerRequestFilter {

    private final RequestMatcher uriMatcher =
            new AntPathRequestMatcher("/api/users/auth", HttpMethod.POST.name());

    private final ObjectMapper mapper;

    @Autowired
    private OAuth2Services oAuth2Services;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            //* Get the token from the request
            AuthCredentials authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
            //* Verify the token
//            AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
//                    httpTransport,
//                    jsonFactory,
//                    new GenericUrl("https://account-d.docusign.com/oauth/token"),
//                    new BasicAuthentication(GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET),
//                    GOOGLE_CLIENT_ID,
//                    "https://www.googleapis.com/oauth2/v4/token")
//                    .build();
//            AuthorizationCodeTokenRequest tokenRequest = flow.newTokenRequest(authCredentials.getCode());
//            TokenResponse tokenResponse = tokenRequest.execute();
//            System.out.println(tokenResponse);
            HashMap<String, Object> tokenResponse = oAuth2Services.getAccessToken(authCredentials.getCode());
            System.out.println(tokenResponse);
            if(tokenResponse == null) throw new IOException("Invalid token");
            //* Write in the response the token
            HashMap<String, Object> params = new HashMap<>();
            params.put("id_token", tokenResponse.get("id_token"));
            params.put("access_token", tokenResponse.get("access_token"));
            HashMap<String, Object> userInfo = oAuth2Services.getUserInfo(tokenResponse.get("id_token").toString());
            params.put("profile_picture", userInfo.get("picture"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            mapper.writeValue(response.getWriter(), params);
//            filterChain.doFilter(request, response);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        RequestMatcher matcher = new NegatedRequestMatcher(uriMatcher);
        return matcher.matches(request);
    }
}
