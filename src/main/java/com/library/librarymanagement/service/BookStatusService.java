package com.library.librarymanagement.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.library.librarymanagement.repository.BookStatusRepository;
import com.library.librarymanagement.request.BookStatusCreationRequest;
import com.library.librarymanagement.request.BookStatusUpdateRequest;
import com.library.librarymanagement.entity.BookStatus;

@Service
public class BookStatusService {
    private final BookStatusRepository repository;

    public BookStatusService(final BookStatusRepository repository) {
        this.repository = repository;
    }

    public List<BookStatus> getAllBookStatus() {
        if (this.repository != null) {
            return this.repository.findAll();
        } else {
            return Collections.emptyList();
        }
    }

    public BookStatus findBookStatusById(final Byte id) {
        if ((this.repository != null) && (id != null)) {
            return this.repository.findById(id).orElse(null);
        } else {
            return null;
        }
    }

    public boolean createBookStatus(final BookStatusCreationRequest request) {
        if ((request == null) || (this.repository == null)) {
            return false;
        }

        final var newBookStatusName = request.getName();
        if (newBookStatusName == null) {
            return false;
        }

        final var newBookStatus = new BookStatus(newBookStatusName);
        this.repository.save(newBookStatus);
        return true;
    }

    public boolean updateBookStatus(final BookStatusUpdateRequest request) {
        if ((request == null) || (this.repository == null)) {
            return false;
        }

        var bookStatus = this.findBookStatusById(request.getId());
        if (bookStatus == null) {
            return false;
        }

        if (bookStatus.setNameIfNotBlank(request.getName())) {
            this.repository.save(bookStatus);
        }

        return true;
    }
}
