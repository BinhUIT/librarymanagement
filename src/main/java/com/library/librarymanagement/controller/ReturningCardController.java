package com.library.librarymanagement.controller;

import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.entity.ReturningCard;
import com.library.librarymanagement.request.ReturningCardCreationRequest;
import com.library.librarymanagement.service.ReturningCardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/returning-card")
public final class ReturningCardController {
    private final ReturningCardService service;

    public ReturningCardController(final ReturningCardService service) {
        this.service = service;
    }

    @GetMapping
    public ReturningCard findReturningCardById(@RequestParam("id") String idString) {
        if (this.service == null) {
            return null;
        }

        try {
            final var id = Integer.valueOf(idString);
            return this.service.findReturningCardById(id);
        } catch (final Exception exception) {
            return null;
        }
    }

    @PostMapping
    public boolean createReturningCard(@RequestBody final ReturningCardCreationRequest request) {
        if (this.service == null) {
            return false;
        }

        try {
            return this.service.createReturningCard(request);
        } catch (final Exception exception) {
            return false;
        }
    }
}
