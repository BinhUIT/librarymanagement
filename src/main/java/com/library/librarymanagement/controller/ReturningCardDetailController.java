package com.library.librarymanagement.controller;

import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.entity.BookImagePath;
import com.library.librarymanagement.request.ReturningCardDetailCreationRequest;
import com.library.librarymanagement.request.ReturningCardDetailDeletionRequest;
import com.library.librarymanagement.request.ReturningCardDetailUpdateRequest;
import com.library.librarymanagement.service.ReturningCardDetailService;

import java.util.Collections;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/returning-card-detail")
public final class ReturningCardDetailController {
    private final ReturningCardDetailService service;

    public ReturningCardDetailController(final ReturningCardDetailService service) {
        this.service = service;
    }

    @GetMapping
    public Set<BookImagePath> findBookTitlesImagePathByReturningCardId(
            @RequestParam("returning-card-id") final String idString) {
        if (this.service == null) {
            return Collections.emptySet();
        }

        try {
            return this.service.findBooksImagePathByReturningCardId(Integer.valueOf(idString));
        } catch (final Exception exception) {
            return Collections.emptySet();
        }
    }

    @PostMapping
    public boolean createReturningCardDetails(@RequestBody final ReturningCardDetailCreationRequest request) {
        if (this.service == null) {
            return false;
        }

        try {
            return this.service.createReturningCardDetail(request);
        } catch (final Exception exception) {
            return false;
        }
    }

    @PutMapping
    public boolean updateReturningCardDetails(@RequestBody final ReturningCardDetailUpdateRequest request) {
        if (this.service == null) {
            return false;
        }

        try {
            return this.service.updateReturningCardDetail(request);
        } catch (final Exception exception) {
            return false;
        }
    }

    @DeleteMapping
    public boolean deleteReturningCardDetail(@RequestBody final ReturningCardDetailDeletionRequest request) {
        if (this.service == null) {
            return false;
        }

        try {
            return this.service.deleteReturningCardDetail(request);
        } catch (final Exception exception) {
            return false;
        }
    }

}
