package com.library.librarymanagement.request;

import jakarta.validation.constraints.NotBlank;

public final class BookStatusCreationRequest {
    @NotBlank(message = "BookStatus Name in creation request cannot be blank!")
    private String name = null;

    public BookStatusCreationRequest(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
