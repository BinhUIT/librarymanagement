package com.library.librarymanagement.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class BookTypeCreationRequest {
    @NotBlank(message = "BookType Name in creation request cannot be blank!")
    private String name = null;

    @NotNull(message = "BookType Image in creation request cannot be null!")
    private byte[] imageData = null;

    public BookTypeCreationRequest(final String name, final byte[] image) {
        this.name = name;

        if (image != null) {
            this.imageData = image.clone();
        } else {
            this.imageData = null;
        }
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
