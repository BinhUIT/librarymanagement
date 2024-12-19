package com.library.librarymanagement.request;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.library.librarymanagement.entity.BorrowingCardDetail.Status;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
@NoArgsConstructor(access = AccessLevel.NONE)
@AllArgsConstructor(access = AccessLevel.NONE)
public final class UpdateBorrowingCardDetailRequest {
    @NotNull(message = "Service Id in update request cannot be null!")
    private Integer serviceId = null;

    private Set<Integer> booksId = Collections.emptySet();

    @NotNull(message = "Status in update request cannot be null!")
    private Status status = Status.PENDING;

    @JsonCreator
    public UpdateBorrowingCardDetailRequest(
            final Integer serviceId, final Set<Integer> booksId, final Status newStatus) {
        this.serviceId = serviceId;

        if (booksId != null) {
            this.booksId = Collections.unmodifiableSet(booksId);
        } else {
            this.booksId = null;
        }

        this.status = newStatus;
    }

    public void setBooksId(final Set<Integer> booksId) {
        if (booksId != null) {
            this.booksId = Collections.unmodifiableSet(booksId);
        } else {
            this.booksId = null;
        }
    }
}
