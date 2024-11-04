package com.library.librarymanagement.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class BookTitleCreationRequest {
    @NotBlank(message = "BookTitle Name in creation request cannot be blank!")
    private String name = null;

    @NotNull(message = "BookTitle TypeId in creation request cannot be null!")
    private Short typeId = null;

    @NotBlank(message = "BookTitle Author in creation request cannot be blank!")
    private String author = null;

    @NotNull(message = "BookTitle Image in creation request cannot be null!")
    private byte[] imageData = null;

    @JsonCreator
    private BookTitleCreationRequest(final String name, final Short typeId, final String author,
            final byte[] imageData) {
        this.name = name;
        this.typeId = typeId;
        this.author = author;

        if (imageData != null) {
            this.imageData = imageData.clone();
        } else {
            this.imageData = null;
        }
    }

    public String getName() {
        return this.name;
    }

    public Short getTypeId() {
        return this.typeId;
    }

    public String getAuthor() {
        return this.author;
    }

    public byte[] getImageData() {
        byte[] result = null;

        if (this.imageData != null) {
            result = this.imageData.clone();
        }

        return result;
    }
}
