package com.library.librarymanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.entity.UserPrinciple;
import com.library.librarymanagement.repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {   
    @Autowired 
    private UserRepository userRepo;
    @Override  
    public UserDetails loadUserByUsername(String fullname) 
    {
        User user=userRepo.findByFullName(fullname); 
        if(user==null) 
        { 
            throw new UsernameNotFoundException("User name not found");
        } 
        return new UserPrinciple(user);
    }


}
