package com.library.librarymanagement.controller;

import java.util.List;
import java.util.Collections;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.entity.BookStatus;
import com.library.librarymanagement.request.BookStatusCreationRequest;
import com.library.librarymanagement.request.BookStatusUpdateRequest;
import com.library.librarymanagement.service.BookStatusService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/book-status")
public final class BookStatusController {
    private final BookStatusService service;

    public BookStatusController(final BookStatusService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<BookStatus> findAllBookStatus() {
        try {
            return this.service.getAllBookStatus();
        } catch (final Exception exception) {
            return Collections.emptyList();
        }
    }

    @GetMapping
    public BookStatus findBookStatusById(@RequestParam("id") final String idString) {
        try {
            return this.service.findBookStatusById(Byte.valueOf(idString));
        } catch (final Exception exception) {
            return null;
        }
    }

    @PostMapping
    public boolean createBookStatus(@RequestBody @Valid final BookStatusCreationRequest request) {
        try {
            return this.service.createBookStatus(request);
        } catch (final Exception exception) {
            return false;
        }
    }

    @PutMapping
    public boolean updateBookStatus(@RequestBody @Valid final BookStatusUpdateRequest request) {
        try {
            return this.service.updateBookStatus(request);
        } catch (final Exception exception) {
            return false;
        }
    }
}
