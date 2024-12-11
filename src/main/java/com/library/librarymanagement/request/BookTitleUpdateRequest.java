package com.library.librarymanagement.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotNull;

public final class BookTitleUpdateRequest {
    @NotNull(message = "BookTitle Id in update request cannot be null!")
    private Integer id = null;

    private String name = null;

    private Short typeId = null;

    private String author = null;

    private byte[] imageData = null; 

    private String nxb=null;

    private Integer year=null;

    private String language=null;

    private Integer pageAmount=null;

    private String review=null;

    @JsonCreator
    public BookTitleUpdateRequest(final Integer id, final String name, final Short typeId, final String author,
            final byte[] imageData, String nxb, int year, String language, int pageAmount, String review) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.author = author;

        if (imageData != null) {
            this.imageData = imageData.clone();
        } else {
            this.imageData = null;
        } 
        this.nxb=nxb;
        this.year=year; 
        this.language=language;
        this.pageAmount=pageAmount;
        this.review=review;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Short getTypeId() {
        return this.typeId;
    }

    public void setTypeId(final Short typeId) {
        this.typeId = typeId;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public byte[] getImageData() {
        byte[] result = null;

        if (this.imageData != null) {
            result = this.imageData.clone();
        }
        return result;
    }

    public void setImageData(byte[] imageData) {
        if (imageData != null) {
            this.imageData = imageData.clone();
        } else {
            this.imageData = null;
        }
    } 

    public String getNxb() 
    {
        return this.nxb;
    } 
    public int getYear() 
    {
        return this.year;
    } 
    public String getLanguage() 
    {
        return this.language;
    } 
    public int getPageAmount() 
    {
        return this.pageAmount;
    } 
    public String getReview() 
    {
        return this.review;
    }
}
