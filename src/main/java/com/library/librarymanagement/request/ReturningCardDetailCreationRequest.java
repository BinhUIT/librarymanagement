package com.library.librarymanagement.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.library.librarymanagement.entity.ReturningCardDetailId;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
public final class ReturningCardDetailCreationRequest {
    @NotNull(message = "Returning Card Id in Returning Card Detail Creation request can not be null")
    private Integer returningCardId = null;

    @NotNull(message = "Book Id in Returning Card Detail Creation request can not be null")
    private Integer bookId = null;

    @JsonCreator
    private ReturningCardDetailCreationRequest(final Integer returningCardId, final Integer bookId) {
        this.returningCardId = returningCardId;
        this.bookId = bookId;
    }

    public ReturningCardDetailId getReturningCardDetailId() {
        return new ReturningCardDetailId(this.returningCardId, this.bookId);
    }
}
