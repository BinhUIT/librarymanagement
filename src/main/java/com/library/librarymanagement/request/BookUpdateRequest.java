package com.library.librarymanagement.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotNull;

public final class BookUpdateRequest {
    @NotNull(message = "Book Id in update request cannot be null!")
    private Integer id = null;

    private Integer titleId = null;

    private Byte statusId = null;

    private Boolean isUsable = true;

    @JsonCreator
    private BookUpdateRequest(Integer id, Integer titleId, Byte statusId, Boolean isUsable) {
        this.id = id;
        this.titleId = titleId;
        this.statusId = statusId;
        this.isUsable = isUsable;
    }

    public Integer getId() {
        return id;
    }

    public Integer getTitleId() {
        return titleId;
    }

    public Byte getStatusId() {
        return statusId;
    }

    public Boolean getIsUsable() {
        return isUsable;
    }
}
