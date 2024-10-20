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
    public BookUpdateRequest(Integer id, Integer titleId, Byte statusId, Boolean isUsable) {
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

    public void setTitleId(Integer typeId) {
        this.titleId = typeId;
    }

    public Byte getStatusId() {
        return statusId;
    }

    public void setStatusId(Byte statusId) {
        this.statusId = statusId;
    }

    public Boolean getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(Boolean isUsable) {
        this.isUsable = isUsable;
    }
}
