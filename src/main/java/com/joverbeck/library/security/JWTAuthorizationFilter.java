package com.joverbeck.library.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.joverbeck.library.security.SecurityConstants.HEADER;
import static com.joverbeck.library.security.SecurityConstants.SECRET_KEY;
import static com.joverbeck.library.security.SecurityConstants.AUTH_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	// Constructor
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }  // End of the Constructor

    /**
     * Have to override this method so that we can filter out requests that 
     * don't have a token.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER);

        if (header == null || !header.startsWith(AUTH_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }  // End of the 'doFilterInternal' method

    /**
     * This is a method that will get the JWT token when one exists.  
     * Otherwise it will return null.
     * 
     * @param request The next Servlet Request that needs to be verified.
     * @return The token when one exists.  Otherwise it returns null.
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(SECRET_KEY.getBytes()))
                    .build()
                    .verify(token.replace(AUTH_PREFIX, ""))
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }  // End of the 'UsernamePasswordAuthenticationToken' method
}  // End of the 'JWTAuthorizationFilter' class
