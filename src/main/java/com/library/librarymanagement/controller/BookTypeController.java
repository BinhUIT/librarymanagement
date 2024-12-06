package com.library.librarymanagement.controller;

import java.util.List;
import java.sql.Date;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            return this.service.getBookTypeImageById(Short.valueOf(idString));
        } catch (final Exception exception) {
            return null;
        }
    } 
    
   @GetMapping("/findByName/{name}") 
   public ResponseEntity<BookTypeImageData> findByName(@PathVariable String name) 
   {
        return service.findByName(name);
   }

    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImageBookTypeById(@RequestParam("id") final String idString) {
        try {
            final var bookTypeImage = this.service.getBookTypeImageById(Short.valueOf(idString));
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

    @GetMapping("/report")
    public ResponseEntity<byte[]> exportReportBorrowingByTypeAndDateRange(
            @RequestParam("startDate") String startDateString, @RequestParam("endDate") String endDateString) {
        try {
            final var startDate = Date.valueOf(startDateString);
            final var endDate = Date.valueOf(endDateString);

            final var pdfBytes = this.service.exportReportBorrowingByDateRange(startDate, endDate);

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
