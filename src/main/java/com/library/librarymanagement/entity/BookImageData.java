package com.library.librarymanagement.entity;

public final class BookImageData {
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

    public Integer getId() {
        return this.id;
    }

    public BookTitleImageData getTitle() {
        return this.title;
    }

    public void setTitle(final BookTitleImageData title) {
        this.title = title;
    }

    public BookStatus getStatus() {
        return this.status;
    }

    public void setStatus(final BookStatus status) {
        this.status = status;
    }

    public boolean isUsable() {
        return this.isUsable;
    }

    public void setUsable(final boolean isUsable) {
        this.isUsable = isUsable;
    }

}
