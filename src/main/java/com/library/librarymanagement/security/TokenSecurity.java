package com.library.librarymanagement.security;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.library.librarymanagement.entity.Token;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.repository.TokenRepository;
import com.library.librarymanagement.repository.UserRepository;

@Component
public class TokenSecurity {    
    private Random rand;  
     
    private List<String> listToken;   
    @Autowired 
    private TokenRepository tokenRepository;
    @Autowired 
    private UserRepository userRepo;
    public TokenSecurity()
    {
          
        rand = new Random();
    } 
    public String generateToken(String fullName) 
    {    

        int tk=rand.nextInt(100000);  
        String token = Integer.toString(tk);  
        while(token.length()<6) 
        { 
            token=token+"0";
        }   
         
        User user = userRepo.findByFullName(fullName);  
        if(user!=null) 
        {  
            token = token +Integer.toString(user.getUserId());
        }
        addToTableToken(token);
        return token;
    }   
    public void addToTableToken(String token) 
    {  
        List<Token> listToken = tokenRepository.findAll();  
    
        int listSize= listToken.size(); 
        if(listToken.isEmpty()) 
        { 
            tokenRepository.save(new Token(0, token)); 
            return;
        } 
        int maxId= listToken.get(listSize-1).getTokenId(); 
        tokenRepository.save(new Token(maxId+1, token));    
        

        


    }
    public boolean validateToken(String token) 
    {
        return (tokenRepository.findByTokenString(token)!=null);
    } 
    public int extractUserId(String token) 
    {
        String id= token.substring(6);  
        return Integer.parseInt(id);

    } 
    public void removeFromList(String token) 
    { 
        Token tokenString = tokenRepository.findByTokenString(token);  
        tokenRepository.delete(tokenString);
    }  
    public boolean checkToken(String token) 
    { 
        if(!validateToken(token)) return false;  
        int userId = extractUserId(token); 
        User user = userRepo.findById(userId).orElse(null); 
        if(user==null||user.getEnable()==false) 
        { 
            removeFromList(token); 
            return false;
        }  
        return true;
        
    }
    

}
