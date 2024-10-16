package com.library.librarymanagement.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.library.librarymanagement.JWTBlackList.JWTBlackList;
import com.library.librarymanagement.service.JWTService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; 
@Component
public class JWTBlackListFilter  extends OncePerRequestFilter{   
    @Autowired 
    private JWTBlackList blackList;  
    @Autowired 
    private ApplicationContext context; 
    @Autowired  
    private JWTService jwtService;
    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException , IOException, java.io.IOException
    {  
        String token=null;  
        
        String authHeader = request.getHeader("Authorization"); 
        if(authHeader!=null&&authHeader.startsWith("Bearer ")) 
        {
            token = authHeader.substring(7);
        } 
        if(token!=null&&blackList.isInBlackList(token)) 
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
            return;
        } 
        filterChain.doFilter(request, response); 
    }
}
