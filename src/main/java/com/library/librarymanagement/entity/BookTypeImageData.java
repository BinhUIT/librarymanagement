package com.library.librarymanagement.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.library.librarymanagement.databind.ByteArraySerializer;
import com.library.librarymanagement.ulti.File;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public final class BookTypeImageData {
    private Short id = null;

    private String name = null;

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @JsonSerialize(using = ByteArraySerializer.class)
    private byte[] imageData = null;

    public BookTypeImageData(final BookTypeImagePath bookTypeImagePath) {
        if (bookTypeImagePath != null) {
            this.id = bookTypeImagePath.getId();
            this.name = bookTypeImagePath.getName();

            final File file = new File(bookTypeImagePath.getImagePath());
            this.imageData = file.readBytes();
        }
    }

    public byte[] getImageData() {
        if (this.imageData != null) {
            return this.imageData.clone();
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
