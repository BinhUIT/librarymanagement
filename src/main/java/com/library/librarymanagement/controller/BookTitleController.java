package com.library.librarymanagement.controller;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
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
    public static final Integer AMOUNT_BOOK_TITLES_IN_ONE_PAGE = 50;

    private final BookTitleService service;

    public BookTitleController(final BookTitleService service) {
        this.service = service;
    }

    @GetMapping
    public List<BookTitleImageData> findBookTitlesImageDataByPage(@RequestParam("page") final String numPageString) {
        try {
            final var numPage = Integer.valueOf(numPageString) - 1;
            final var pageable = PageRequest.of(numPage, AMOUNT_BOOK_TITLES_IN_ONE_PAGE);
            return this.service.findBookTitlesImageDataByPage(pageable);
        } catch (final Exception exception) {
            return Collections.emptyList();
        }
    }

    @GetMapping("/details")
    public BookTitleImageData findBookTitleImageDataById(@RequestParam("id") final String idString) {
        try {
            return this.service.findBookTitleImageDataById(Integer.valueOf(idString));
        } catch (final Exception exception) {
            return null;
        }
    }

    @GetMapping("/search")
    public List<BookTitleImageData> findBookTitlesImageDataByPageWithCriteria(
            @RequestParam(name = "page") final String numPageString,
            @RequestParam(name = "name", required = false) String bookTitleNameKeyword,
            @RequestParam(name = "author", required = false) String bookTitleAuthorKeyword,
            @RequestParam(name = "types", required = false) Set<Short> bookTypesIdSet) {
        try {
            final var numPage = Integer.valueOf(numPageString) - 1;
            final var pageable = PageRequest.of(numPage, AMOUNT_BOOK_TITLES_IN_ONE_PAGE);
            return this.service.findBookTitlesImageDataByPageWithCriteria(
                    pageable, bookTitleNameKeyword, bookTitleAuthorKeyword, bookTypesIdSet);
        } catch (final Exception exception) {
            return Collections.emptyList();
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
