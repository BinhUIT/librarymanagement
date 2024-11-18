package com.library.librarymanagement.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldNameConstants
@Table(name = "ReturningCardDetail")
public final class ReturningCardDetail {
    @EmbeddedId
    private ReturningCardDetailId id = null;

    public ReturningCardDetail(final ReturningCard returningCard, final BookImagePath book) {
        this.returingCard = returningCard;
        this.book = book;
    }

    @ManyToOne(optional = false)
    @MapsId("returningCardId")
    @JoinColumn(name = "ReturningCardId", referencedColumnName = "id", nullable = false)
    private ReturningCard returingCard = null;

    @ManyToOne(optional = false)
    @MapsId("bookTitleId")
    @JoinColumn(name = "BookId", referencedColumnName = "id", nullable = false)
    private BookImagePath book = null;
}
