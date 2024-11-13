package com.library.librarymanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.experimental.FieldNameConstants;

@Entity 
@FieldNameConstants 
@Table(name="TOKEN")
public class Token {  
    @Id 
    @Column(name="TOKENID", columnDefinition="INT", nullable=false) 
    private int tokenId; 

    @Column(name="TOKENSTRING", columnDefinition="varchar(100)", nullable=false) 
    private String tokenString; 

    public Token() 
    {

    }
    public Token(int tokenId, String tokenString) 
    { 
        this.tokenId=tokenId; 
        this.tokenString = tokenString;
    } 
    public int getTokenId() 
    { 
        return this.tokenId;
    } 
    public String getTokenString() 
    {
        return this.tokenString;
    }

}
