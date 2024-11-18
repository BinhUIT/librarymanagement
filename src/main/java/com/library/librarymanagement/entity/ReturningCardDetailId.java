package com.library.librarymanagement.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;

@Embeddable
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReturningCardDetailId implements Serializable {
    @Column(name = "ReturningCardId", columnDefinition = "int", nullable = false)
    private Integer returningCardId = null;

    @Column(name = "BookId", columnDefinition = "int", nullable = false)
    private Integer bookId = null;

    public ReturningCardDetailId(final Integer returningCardId, final Integer bookId) {
        this.returningCardId = returningCardId;
        this.bookId = bookId;
    }
}
