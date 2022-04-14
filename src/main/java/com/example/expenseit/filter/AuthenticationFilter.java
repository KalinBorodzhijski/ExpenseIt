package com.example.expenseit.filter;


import com.example.expenseit.Constants;
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

        String header = request.getHeader("Authorization");
        if(header != null){
            String[] tokenArr = header.split("Bearer ");
            if(tokenArr[1] != null){
                String token = tokenArr[1];

                try{
                    Claims claims = Jwts.parser().setSigningKey(Constants.SECRET_KEY).parseClaimsJws(token).getBody();
                    request.setAttribute("clientId", Integer.parseInt(claims.get("clientId").toString()));
                }catch (Exception e){
                    response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid or expired token");
                }
            } else{
                response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid format. Format must be Bearer [token]");
            }
        } else{
            response.sendError(HttpStatus.FORBIDDEN.value(), "Token must be provided");
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
