package com.library.librarymanagement.request;

import jakarta.validation.constraints.NotNull;

public final class BookStatusUpdateRequest {
    @NotNull(message = "BookStatus Id in update request cannot be null!")
    private Byte id = null;

    private String name = null;

    public BookStatusUpdateRequest(Byte id, String name) {
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
