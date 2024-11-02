package com.library.librarymanagement.request;

import jakarta.validation.constraints.NotNull;

public final class BookCreationRequest {
    @NotNull(message = "Book TitleId in creation request cannot be null!")
    private Integer titleId = null;

    @NotNull(message = "Book StatusId in creation request cannot be null!")
    private Byte statusId = null;

    @NotNull(message = "Book IsUsable in creation request cannot be null!")
    private boolean isUsable = true;

    public BookCreationRequest(final Integer titleId, final Byte statusId, final boolean isUsable) {
        this.titleId = titleId;
        this.statusId = statusId;
        this.isUsable = isUsable;
    }

    public Integer getTitleId() {
        return this.titleId;
    }

    public void setTitleId(final Integer titleId) {
        this.titleId = titleId;
    }

    public Byte getStatusId() {
        return this.statusId;
    }

    public void setStatusId(final Byte statusId) {
        this.statusId = statusId;
    }

    public boolean isUsable() {
        return this.isUsable;
    }

    public void setUsable(final boolean isUsable) {
        this.isUsable = isUsable;
    }

}
