package com.library.librarymanagement.controller;

import java.util.List;
import java.util.Collections;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    public BookTypeController(final BookTypeService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<BookTypeImageData> findAllBookTypes() {
        try {
            return this.service.findAllBookTypes();
        } catch (final Exception exception) {
            return Collections.emptyList();
        }
    }

    @GetMapping
    public BookTypeImageData findBookTypeById(@RequestParam("id") final String idString) {
        try {
            return this.service.findBookTypeImageDataById(Short.valueOf(idString));
        } catch (final Exception exception) {
            return null;
        }
    }

    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] findImageBookTypeById(@RequestParam("id") final String idString) {
        try {
            final var bookTypeImage = this.service.findBookTypeImageDataById(Short.valueOf(idString));
            return bookTypeImage.getImageData().clone();
        } catch (final Exception exception) {
            return new byte[] {};
        }
    }

    @PostMapping
    public boolean createBookType(@RequestBody @Valid final BookTypeCreationRequest request) {
        try {
            return this.service.createBookType(request);
        } catch (final Exception exception) {
            return false;
        }
    }

    @PutMapping
    public boolean updateBookType(@RequestBody @Valid final BookTypeUpdateRequest request) {
        try {
            return this.service.updateBookType(request);
        } catch (final Exception exception) {
            return false;
        }
    }
}
