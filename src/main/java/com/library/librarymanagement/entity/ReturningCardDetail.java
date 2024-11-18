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
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
// @Setter(value = AccessLevel.NONE)
@FieldNameConstants
@Table(name = "ReturningCardDetail")
public final class ReturningCardDetail {
    @EmbeddedId
    private ReturningCardDetailId id = null;

    @ManyToOne(optional = false)
    @MapsId("returningCardId")
    @JoinColumn(name = "ReturningCardId", referencedColumnName = "id", nullable = false)
    private ReturningCard returningCard = null;

    @ManyToOne(optional = false)
    @MapsId("bookTitleId")
    @JoinColumn(name = "BookId", referencedColumnName = "id", nullable = false)
    private BookImagePath book = null;

    public ReturningCardDetail(final ReturningCard returningCard, final BookImagePath book) {
        this.returningCard = returningCard;
        this.book = book;
        this.refreshId();
    }

    private void refreshId() {
        if ((this.returningCard != null) && (this.book != null)) {
            this.id = new ReturningCardDetailId(returningCard.getId(), book.getId());
        } else {
            this.id = null;
        }
    }

    public void setReturningCard(final ReturningCard returningCard) {
        this.returningCard = returningCard;
        this.refreshId();
    }

    public void setBook(final BookImagePath book) {
        this.book = book;
        this.refreshId();
    }
}
