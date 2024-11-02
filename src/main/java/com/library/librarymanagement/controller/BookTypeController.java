package com.library.librarymanagement.controller;

import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.request.BookTypeCreationRequest;
import com.library.librarymanagement.request.BookTypeUpdateRequest;
import com.library.librarymanagement.entity.BookTypeImageData;
import com.library.librarymanagement.service.BookTypeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/book-types")
public final class BookTypeController {
    private final BookTypeService service;

    @Autowired(required = true)
    private BookTypeController(final BookTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<BookTypeImageData> getAllBookTypes() {
        try {
            return this.service.getAllBookTypes();
        } catch (final Exception exception) {
            return Collections.emptyList();
        }
    }

    @GetMapping("/details")
    public BookTypeImageData getBookTypeById(@RequestParam("id") final String idString) {
        try {
            return this.service.getBookTypeImageDataById(Short.valueOf(idString));
        } catch (final Exception exception) {
            return null;
        }
    }

    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImageBookTypeById(@RequestParam("id") final String idString) {
        try {
            final var bookTypeImage = this.service.getBookTypeImageDataById(Short.valueOf(idString));
            return bookTypeImage.getImageData().clone();
        } catch (final Exception exception) {
            return new byte[] {};
        }
    }

    @PostMapping("/create")
    public boolean createBookType(@RequestBody @Valid final BookTypeCreationRequest request) {
        try {
            return this.service.createBookType(request);
        } catch (final Exception exception) {
            return false;
        }
    }

    @PostMapping("/update")
    public boolean updateBookType(@RequestBody @Valid final BookTypeUpdateRequest request) {
        try {
            return this.service.updateBookType(request);
        } catch (final Exception exception) {
            return false;
        }
    }
}
