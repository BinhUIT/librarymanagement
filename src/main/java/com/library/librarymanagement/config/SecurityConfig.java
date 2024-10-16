package com.library.librarymanagement.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; 

@Configuration 
@EnableWebSecurity
public class SecurityConfig { 
    @Autowired 
    private UserDetailsService userDetailService;  
    @Autowired 
    private JWTFilter jwtFilter;  
    @Autowired 
    private JWTBlackListFilter blackList;
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
    { 
        http.csrf(customizer->customizer.disable()); 
        http.authorizeHttpRequests( 
            authorizer->authorizer. 
            requestMatchers("/home", "/login", "/register","/verify"). permitAll() 
            .anyRequest().authenticated()
        ); 
        http.httpBasic(Customizer.withDefaults());  
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); 
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  
        http.addFilterBefore(blackList, JWTFilter.class);
        return http.build(); 
    }  
    @Bean 
    public AuthenticationProvider authenticationProvider() 
    {
        DaoAuthenticationProvider authProvider = new  DaoAuthenticationProvider();   
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(12)); 
        authProvider.setUserDetailsService(userDetailService);
        return authProvider;
    } 
    @Bean  
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception 
    { 
        return config.getAuthenticationManager();
    }

}
