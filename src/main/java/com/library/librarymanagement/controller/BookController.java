package com.library.librarymanagement.controller;

import java.util.List;

import org.assertj.core.api.IntegerAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.entity.BookImageData;
import com.library.librarymanagement.entity.BookImagePath;
import com.library.librarymanagement.request.BookCreationRequest;
import com.library.librarymanagement.request.BookUpdateRequest;
import com.library.librarymanagement.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public final class BookController {
    private final BookService service;

    @Autowired(required = true)
    private BookController(final BookService service) {
        this.service = service;
    }

    @GetMapping("/details")
    public BookImageData getBookImageDataById(@RequestParam("id") final String idString) {
        try {
            final var id = Integer.valueOf(idString);
            return this.service.getBookImageDataById(id);
        } catch (final Exception exception) {
            return null;
        }
    }

    @PostMapping("/create")
    public boolean createBook(@RequestBody @Valid final BookCreationRequest request) {
        try {
            return this.service.createBook(request);
        } catch (final Exception exception) {
            return false;
        }
    }

    @PostMapping("/update")
    public boolean updateBook(@RequestBody @Valid BookUpdateRequest request) {
        try {
            return this.service.updateBook(request);
        } catch (final Exception exception) {
            return false;
        }
    } 
    @GetMapping("/all") 
    public List<BookImagePath> getAllBook() 
    { 
        return service.getAllBook();
    }
}
