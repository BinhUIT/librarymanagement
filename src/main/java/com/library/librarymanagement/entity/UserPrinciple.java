package com.library.librarymanagement.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrinciple implements UserDetails{ 
     private User user; 
    public UserPrinciple() 
    {

    } 
    public UserPrinciple(User user) 
    {
        this.user=user;
    } 
    @Override 
    public Collection<?extends GrantedAuthority> getAuthorities() {
        return Collections.singleton( new SimpleGrantedAuthority("USER"));
    } 
    @Override 
    public String getPassword() 
    {
        return user.getPassword();
    }  
    @Override 
    public String getUsername() 
    {
        return user.getFullname(); 
    } 
    @Override 
    public boolean isAccountNonExpired() 
    {
        return true;
    } 
    @Override 
    public  boolean isAccountNonLocked() 
    {
        return user.getEnable();
    }  
    @Override 
    public boolean isEnabled() 
    {
        return user.getEnable();
    }

}
