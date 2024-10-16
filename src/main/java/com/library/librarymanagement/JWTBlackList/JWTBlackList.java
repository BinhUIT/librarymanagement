package com.library.librarymanagement.JWTBlackList;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class JWTBlackList { 
    private List<String> blacklist;  
    public JWTBlackList() 
    {
        blacklist = new ArrayList<String>();
    }
    public void addToken(String token) 
    {
        blacklist.add(token);
    } 
    public boolean isInBlackList(String token) 
    {  
        if(blacklist==null) return false;
        return blacklist.contains(token); 
    }

}
