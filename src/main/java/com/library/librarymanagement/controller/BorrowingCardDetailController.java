package com.library.librarymanagement.controller;

import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.request.UpdateBorrowingCardDetailRequest;
import com.library.librarymanagement.service.BorrowingCardDetailService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("borrowing-card-detail")
public final class BorrowingCardDetailController {
    private final BorrowingCardDetailService service;

    private BorrowingCardDetailController(final BorrowingCardDetailService service) {
        this.service = service;
    }

    @PostMapping
    public boolean updateStatusBorrowing(@RequestBody @Valid final UpdateBorrowingCardDetailRequest request) {
        try {
            return service.updateBorrowingStatus(request);
        } catch (final Exception e) {
            return false;
        }
    }
}
