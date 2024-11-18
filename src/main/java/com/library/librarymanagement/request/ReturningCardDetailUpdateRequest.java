package com.library.librarymanagement.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.library.librarymanagement.entity.ReturningCardDetailId;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
public final class ReturningCardDetailUpdateRequest {
    @NotNull(message = "Old returning Card Id in Returning Card Detail Update request can not be null")
    private Integer oldReturningCardId = null;

    @NotNull(message = "Old book Id in Returning Card Detail Update request can not be null")
    private Integer oldBookId = null;

    private Integer newReturningCardId = null;

    private Integer newBookId = null;

    @JsonCreator
    private ReturningCardDetailUpdateRequest(final Integer oldReturningCardId, final Integer oldBookId,
            final Integer newReturningCardId, final Integer newBookId) {
        this.oldReturningCardId = oldBookId;
        this.oldBookId = oldBookId;
        this.newReturningCardId = newReturningCardId;
        this.newBookId = newBookId;
    }

    @AssertTrue(message = "Returning Card Detail Update request is not valid")
    public boolean isValid() {
        return (this.oldReturningCardId != null) && (this.oldBookId != null) &&
                ((this.newReturningCardId != null) || (this.newBookId != null));
    }

    public ReturningCardDetailId getOldReturningCardDetailId() {
        return new ReturningCardDetailId(this.oldReturningCardId, this.oldBookId);
    }
}
