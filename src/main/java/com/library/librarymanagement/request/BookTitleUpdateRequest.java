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

    @JsonCreator
    private BookTitleUpdateRequest(final Integer id, final String name, final Short typeId, final String author,
            final byte[] imageData) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.author = author;

        if (imageData != null) {
            this.imageData = imageData.clone();
        } else {
            this.imageData = null;
        }
    }

    public Integer getId() {
        return this.id;
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
