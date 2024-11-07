package com.library.librarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.repository.BookStatusRepository;
import com.library.librarymanagement.request.BookStatusCreationRequest;
import com.library.librarymanagement.request.BookStatusUpdateRequest;
import com.library.librarymanagement.entity.BookStatus;

@Service
public final class BookStatusService {
    private final BookStatusRepository repository;

    @Autowired(required = true)
    public BookStatusService(final BookStatusRepository repository) {
        this.repository = repository;
    }

    public List<BookStatus> getAllBookStatus() {
        return this.repository.findAll();
    }

    public BookStatus getBookStatusById(final Byte id) {
        return this.repository.findById(id).orElse(null);
    }

    public boolean createBookStatus(final BookStatusCreationRequest request) {
        boolean result = false;

        if ((request != null) && (request.getName() != null)) {
            final var newBookStatus = new BookStatus(request.getName());
            this.repository.save(newBookStatus);
            result = true;
        }

        return result;
    }

    public boolean updateBookStatus(final BookStatusUpdateRequest request) {
        boolean result = true;

        if ((request != null) && (request.getId() != null) && (request.getName() != null)) {
            var bookStatus = this.repository.findById(request.getId()).orElse(null);
            if (bookStatus != null) {
                bookStatus.setName(request.getName());
                this.repository.save(bookStatus);
                result = true;
            }
        }

        return result;

    }
}
