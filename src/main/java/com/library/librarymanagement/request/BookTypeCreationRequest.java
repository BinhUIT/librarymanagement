package com.library.librarymanagement.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class BookTypeCreationRequest {
    @NotBlank(message = "BookType Name in creation request cannot be blank!")
    private String name = null;

    @NotNull(message = "BookType Image in creation request cannot be null!")
    private byte[] imageData = null;

    @JsonCreator
    private BookTypeCreationRequest(final String name, final byte[] image) {
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

    public byte[] getImageData() {
        byte[] result = null;

        if (this.imageData != null) {
            result = this.imageData.clone();
        }

        return result;
    }
}
