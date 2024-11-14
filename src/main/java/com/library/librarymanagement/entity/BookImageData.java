package com.library.librarymanagement.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public final class BookImageData {
    @Setter(value = AccessLevel.NONE)
    private Integer id = null;

    private BookTitleImageData title = null;

    private BookStatus status = null;

    private boolean isUsable = true;

    public BookImageData(final BookImagePath bookImagePath) {
        if (bookImagePath != null) {
            this.id = bookImagePath.getId();
            this.title = new BookTitleImageData(bookImagePath.getTitle());
            this.status = bookImagePath.getStatus();
            this.isUsable = bookImagePath.isUsable();
        }
    }
}
