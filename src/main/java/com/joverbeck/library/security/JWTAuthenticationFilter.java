package com.joverbeck.library.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.joverbeck.library.security.SecurityConstants.MAX_TIME;
import static com.joverbeck.library.security.SecurityConstants.SECRET_KEY;
import static com.joverbeck.library.security.SecurityConstants.HEADER;
import static com.joverbeck.library.security.SecurityConstants.AUTH_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joverbeck.library.users.MyUser;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Have to override this in order to validate a user login attempt.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            MyUser creds = new ObjectMapper()
                    .readValue(req.getInputStream(), MyUser.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }  // End of the 'attemptAuthentication' method

    /**
     * Have to override this to create a token for the user when the login 
     * successfully.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + MAX_TIME))
                .sign(HMAC512(SECRET_KEY.getBytes()));
        res.addHeader(HEADER, AUTH_PREFIX + token);
        res.addHeader("Content-Type", "application/json");
        String outToken = "{'token' : '" + token + "'}";
        res.getOutputStream().write(outToken.getBytes());  // Set the token in the body
    }  // End of the 'successfulAuthentication' method
}  // End of the 'JWTAuthenticationFilter' class
