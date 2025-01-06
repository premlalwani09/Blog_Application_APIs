package com.blog.Blogging_Application_API.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private  JWTTokenHelper jwtTokenHelper;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Get Token
        String requestToken = request.getHeader("Authorization");

        // Bearer 2524fvffbb

        System.out.println(requestToken);



        String username = null;

        String token = null;

        if (requestToken != null && requestToken.startsWith("Bearer ")){

            token = requestToken.substring(7);

            try{
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            }
            catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            }
            catch (ExpiredJwtException e){
                System.out.println("JWT Token has Expired.");
            }
            catch (MalformedJwtException e){
                System.out.println("Invalid JWT");
            }

        }
        else{
            System.out.println("JWT Token does not begin with Bearer");
        }



        // once we get the token, now validate
        // 2. Validate Token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(username);

            if (this.jwtTokenHelper.validateToken(token, userDetails)){

                // valid token
                // authentication should be done

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
            else {
                System.out.println("Invalid JWT Token");
            }
        }
        else {

            System.out.println("Username is null or context is not null");
        }

        filterChain.doFilter(request, response);
    }
}
