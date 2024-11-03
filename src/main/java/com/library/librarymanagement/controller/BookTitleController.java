package com.library.librarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Collections;
import com.library.librarymanagement.request.BookTitleCreationRequest;
import com.library.librarymanagement.request.BookTitleUpdateRequest;
import com.library.librarymanagement.entity.BookTitleImageData;
import com.library.librarymanagement.service.BookTitleService;

@RestController
@RequestMapping("/book-titles")
public final class BookTitleController {
    private final BookTitleService service;

    @Autowired(required = true)
    public BookTitleController(final BookTitleService service) {
        this.service = service;
    }

    @GetMapping
    public List<BookTitleImageData> findBookTitlesImageDataByPage(@RequestParam("page") final String numPageString) {
        final Integer AMOUNT_BOOK_TITLES_IN_ONE_PAGE = 50;

        try {
            final var numPage = Integer.valueOf(numPageString);
            return this.service.findBookTitlesImageDataByPage((numPage - 1) * AMOUNT_BOOK_TITLES_IN_ONE_PAGE,
                    AMOUNT_BOOK_TITLES_IN_ONE_PAGE);
        } catch (final Exception exception) {
            return Collections.emptyList();
        }
    }

    @GetMapping("/details")
    public BookTitleImageData findBookTitlesImageDataById(@RequestParam("id") final String idString) {
        try {
            return this.service.findBookTitleImageDataById(Integer.valueOf(idString));
        } catch (final Exception exception) {
            return null;
        }
    }

    @PostMapping("/create")
    public boolean createBookTitle(@RequestBody @Valid final BookTitleCreationRequest request) {
        try {
            return this.service.createBookTitle(request);
        } catch (final Exception exception) {
            return false;
        }
    }

    @PostMapping("/update")
    public boolean updateBookTitle(@RequestBody @Valid final BookTitleUpdateRequest request) {
        try {
            return this.service.updateBookTitle(request);
        } catch (final Exception exception) {
            return false;
        }
    }

}
