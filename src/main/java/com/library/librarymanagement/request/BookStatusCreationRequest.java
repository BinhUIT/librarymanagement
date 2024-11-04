package com.library.librarymanagement.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotBlank;

public final class BookStatusCreationRequest {
    @NotBlank(message = "BookStatus Name in creation request cannot be blank!")
    private String name = null;

    @JsonCreator
    private BookStatusCreationRequest(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
