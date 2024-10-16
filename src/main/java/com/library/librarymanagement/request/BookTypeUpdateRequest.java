package com.library.librarymanagement.request;

import jakarta.validation.constraints.NotNull;

public class BookTypeUpdateRequest {
    @NotNull(message = "BookType Id in update request cannot be null!")
    private Short id = 0;

    private String name = null;

    private byte[] imageData = new byte[] {};

    public BookTypeUpdateRequest(final Short id, final String name, final byte[] imageData) {
        this.id = id;
        this.name = name;

        if (imageData != null) {
            this.imageData = imageData.clone();
        } else {
            this.imageData = null;
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
