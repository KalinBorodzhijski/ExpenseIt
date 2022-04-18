package com.example.expenseit.filter;


import com.example.expenseit.Constants;
import com.example.expenseit.errors.InvalidDataException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("Authorization");
        if(token != null){

            try{
                Claims claims = Jwts.parser().setSigningKey(Constants.SECRET_KEY).parseClaimsJws(token).getBody();
                request.setAttribute("clientId", Integer.parseInt(claims.get("clientId").toString()));
            }catch (Exception e){
                throw new InvalidDataException("Invalid or expired token");
            }
        } else{
            throw new InvalidDataException("Token must be provided");
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
