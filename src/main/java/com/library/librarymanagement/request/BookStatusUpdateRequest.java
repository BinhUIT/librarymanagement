package com.library.librarymanagement.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotNull;

public final class BookStatusUpdateRequest {
    @NotNull(message = "BookStatus Id in update request cannot be null!")
    private Byte id = null;

    private String name = null;

    @JsonCreator
    public BookStatusUpdateRequest(final Byte id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Byte getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
