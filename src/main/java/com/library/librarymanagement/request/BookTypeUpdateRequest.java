package com.library.librarymanagement.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotNull;

public final class BookTypeUpdateRequest {
    @NotNull(message = "BookType Id in update request cannot be null!")
    private Short id = null;

    private String name = null;

    private byte[] imageData = null;

    @JsonCreator
    private BookTypeUpdateRequest(final Short id, final String name, final byte[] imageData) {
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

    public byte[] getImageData() {
        byte[] result = null;

        if (this.imageData != null) {
            result = this.imageData.clone();
        }

        return result;
    }
}
