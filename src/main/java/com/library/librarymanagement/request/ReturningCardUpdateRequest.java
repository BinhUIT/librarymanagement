package com.library.librarymanagement.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
public final class ReturningCardUpdateRequest {
    @NotNull(message = "Returning Card ID in Returning Card update request cannot be null!")
    private Integer returningCardId = null;

    private Integer readerId = null;

    private Integer librarianId = null;

    @JsonCreator
    private ReturningCardUpdateRequest(final Integer returningCardId,
            final Integer readerId, final Integer librarianId) {
        this.returningCardId = returningCardId;
        this.readerId = readerId;
        this.librarianId = librarianId;
    }
}
