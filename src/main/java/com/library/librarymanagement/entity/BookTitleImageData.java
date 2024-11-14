package com.library.librarymanagement.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.library.librarymanagement.databind.ByteArraySerializer;
import com.library.librarymanagement.ulti.File;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public final class BookTitleImageData {
    @Setter(value = AccessLevel.NONE)
    private Integer id = null;

    private String name = null;

    private Integer amount = null;

    private Integer amountRemaining = null;

    private BookTypeImageData type = null;

    private String author = null;

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @JsonSerialize(using = ByteArraySerializer.class)
    private byte[] imageData = null;

    public BookTitleImageData(final BookTitleImagePath bookTitleImagePath) {
        if (bookTitleImagePath != null) {
            this.id = bookTitleImagePath.getId();
            this.name = bookTitleImagePath.getName();
            this.amount = bookTitleImagePath.getAmount();
            this.amountRemaining = bookTitleImagePath.getAmountRemaining();
            this.type = new BookTypeImageData(bookTitleImagePath.getType());
            this.author = bookTitleImagePath.getAuthor();

            final var file = new File(bookTitleImagePath.getImagePath());
            this.imageData = file.readBytes();
        }
    }

    public byte[] getImageData() {
        if (this.imageData != null) {
            return imageData.clone();
        } else {
            return new byte[] {};
        }
    }

    public void setImageData(final byte[] imageData) {
        if (imageData != null) {
            this.imageData = imageData.clone();
        } else {
            this.imageData = new byte[] {};
        }
    }

}
