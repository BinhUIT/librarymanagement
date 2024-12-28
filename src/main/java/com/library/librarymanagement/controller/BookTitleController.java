package com.library.librarymanagement.controller;

import java.util.List;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.sql.Date;
import java.text.SimpleDateFormat;
@RestController
@RequestMapping("/book-titles")
public final class BookTitleController {
    private final BookTitleService service;

    @Autowired(required = true)
    public BookTitleController(final BookTitleService service) {
        this.service = service;
    }

    @GetMapping
    public List<BookTitleImageData> getBookTitlesImageDataByPage(@RequestParam("page") final String numPageString) {
        final Integer AMOUNT_BOOK_TITLES_IN_ONE_PAGE = 50;

        try {
            final var numPage = Integer.valueOf(numPageString);
            return this.service.getBookTitlesImageDataByPage((numPage - 1) * AMOUNT_BOOK_TITLES_IN_ONE_PAGE,
                    AMOUNT_BOOK_TITLES_IN_ONE_PAGE);
        } catch (final Exception exception) {
            return Collections.emptyList();
        }
    } 
    @GetMapping("/get/byBookType/{id}") 
    public ResponseEntity<List<BookTitleImageData>> getBookTitlesByBookType(@PathVariable short id) 
    { 
        return service.getBookTitleByType(id);
    } 

   @GetMapping("/get/byAuthor/{author}") 
   public ResponseEntity<List<BookTitleImageData>> getBookTitlesByAuthor(@PathVariable String author) 
   { 
    return service.getBookTitleByAuthor(author);
   }

   @GetMapping("/get/byName/{name}") 
   public ResponseEntity<BookTitleImageData> getBookTitleByName(@PathVariable String name) 
   { 
    return service.findByName(name);
   }
    @GetMapping("/details/{idString}")
    public BookTitleImageData getBookTitleById(@PathVariable final String idString) {
        try {
            return this.service.getBookTitleImageDataById(Integer.valueOf(idString));
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

    @GetMapping("/all") 
    public List<BookTitleImageData> getAll() 
    {
        return service.getAll();
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> exportReportBorrowingByTypeAndDateRange(
            @RequestParam(value = "id", required = false) String bookTypeIdString,
            @RequestParam("startDate") String startDateString, @RequestParam("endDate") String endDateString) {
        try {
            final var bookTypeId = bookTypeIdString != null ? Short.valueOf(bookTypeIdString) : null;
            final var startDate = Date.valueOf(startDateString);
            final var endDate = Date.valueOf(endDateString);

            final var pdfBytes = this.service.exportReportBorrowingByTypeAndDateRange(bookTypeId, startDate, endDate);

            final var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("report.pdf")
                    .build());

            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (final Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
