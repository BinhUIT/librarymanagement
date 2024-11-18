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

    @NotNull(message = "Old book Title Id in Returning Card Detail Update request can not be null")
    private Integer oldBookTitleId = null;

    private Integer newReturningCardId = null;

    private Integer newBookTitleId = null;

    @JsonCreator
    private ReturningCardDetailUpdateRequest(final Integer oldReturningCardId, final Integer oldBookTitleId,
            final Integer newReturningCardId, final Integer newBookTitleId) {
        this.oldReturningCardId = oldBookTitleId;
        this.oldBookTitleId = oldBookTitleId;
        this.newReturningCardId = newReturningCardId;
        this.newBookTitleId = newBookTitleId;
    }

    @AssertTrue(message = "Returning Card Detail Update request is not valid")
    public boolean isValid() {
        return (this.oldReturningCardId != null) && (this.oldBookTitleId != null) &&
                ((this.newReturningCardId != null) || (this.newBookTitleId != null));
    }

    public ReturningCardDetailId getOldReturningCardDetailId() {
        return new ReturningCardDetailId(this.oldReturningCardId, this.oldBookTitleId);
    }
}
