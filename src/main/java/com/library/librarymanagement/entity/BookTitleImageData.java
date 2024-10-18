package com.library.librarymanagement.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.library.librarymanagement.databind.ByteArraySerializer;
import com.library.librarymanagement.ulti.File;

public final class BookTitleImageData {
    private Integer id = null;

    private String name = null;

    private Integer amount = null;

    private Integer amountRemaining = null;

    private BookTypeImageData type = null;

    private String author = null;

    @JsonSerialize(using = ByteArraySerializer.class)
    private byte[] imageData = null;

    public BookTitleImageData(final BookTitleImagePath bookTitleImagePath) {
        this.id = bookTitleImagePath.getId();
        this.name = bookTitleImagePath.getName();
        this.amount = bookTitleImagePath.getAmount();
        this.amountRemaining = bookTitleImagePath.getAmountRemaining();
        this.type = new BookTypeImageData(bookTitleImagePath.getType());
        this.author = bookTitleImagePath.getAuthor();

        final var file = new File(bookTitleImagePath.getImagePath());
        this.imageData = file.readBytes();
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

    public Integer getAmount() {
        return this.amount;
    }

    public Integer getAmountRemaining() {
        return this.amountRemaining;
    }

    public BookTypeImageData getType() {
        return this.type;
    }

    public void setType(final BookTypeImageData type) {
        this.type = type;
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
            result = imageData.clone();
        }

        return result;
    }

    public void setImageData(final byte[] imageData) {
        if (imageData != null) {
            this.imageData = imageData.clone();
        } else {
            this.imageData = null;
        }
    }

}
