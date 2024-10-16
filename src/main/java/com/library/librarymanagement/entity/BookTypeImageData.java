package com.library.librarymanagement.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.library.librarymanagement.databind.ByteArraySerializer;
import com.library.librarymanagement.ulti.File;

public final class BookTypeImageData {
    private Short id = null;

    private String name = null;

    @JsonSerialize(using = ByteArraySerializer.class)
    private byte[] imageData = null;

    public BookTypeImageData(final BookTypeImagePath bookTypeImagePath) {
        if (bookTypeImagePath != null) {
            this.id = bookTypeImagePath.getId();
            this.name = bookTypeImagePath.getName();

            final File file = new File(bookTypeImagePath.getImagePath());
            imageData = file.readBytes();
        }
    }

    public Short getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public byte[] getImageData() {
        byte[] result = null;

        if (this.imageData != null) {
            result = this.imageData.clone();
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
