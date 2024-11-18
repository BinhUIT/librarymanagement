package com.library.librarymanagement.request;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
public final class ReturningCardCreationRequest {
    @NotNull(message = "Reader ID in Returning Card creation request cannot be null!")
    private Integer readerId = null;

    @NotNull(message = "Librarian ID in Returning Card creation request cannot be null!")
    private Integer librarianId = null;

    @JsonCreator
    private ReturningCardCreationRequest(final Integer readerId, final Integer librarianId) {
        this.readerId = readerId;
        this.librarianId = librarianId;
    }
}
