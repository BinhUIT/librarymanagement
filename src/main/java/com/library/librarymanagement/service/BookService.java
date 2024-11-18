package com.library.librarymanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.BookImageData;
import com.library.librarymanagement.entity.BookImagePath;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.request.BookCreationRequest;
import com.library.librarymanagement.request.BookUpdateRequest;

@Service
public class BookService {
    private final BookRepository repository;
    private final BookTitleService bookTitleService;
    private final BookStatusService bookStatusService;

    public BookService(final BookRepository repository, final BookTitleService bookTitleService,
            final BookStatusService bookStatusService) {
        this.repository = repository;
        this.bookTitleService = bookTitleService;
        this.bookStatusService = bookStatusService;
    }

    public BookImagePath findBookImagePathById(final Integer id) {
        if ((id != null) && (this.repository != null)) {
            return this.repository.findById(id).orElse(null);
        } else {
            return null;
        }
    }

    public BookImageData findBookImageDataById(final Integer id) {
        final var bookImagePath = this.findBookImagePathById(id);
        if (bookImagePath != null) {
            return new BookImageData(bookImagePath);
        } else {
            return null;
        }
    }

    public boolean createBook(final BookCreationRequest request) {
        if ((request == null) || (this.repository == null) || (this.bookStatusService == null)
                || (this.bookTitleService == null)) {
            return false;
        }

        final var bookStatus = this.bookStatusService.findBookStatusById(request.getStatusId());
        if (bookStatus == null) {
            return false;
        }

        final var bookTitleImagePath = this.bookTitleService.findBookTitleImagePathById(request.getTitleId());
        if (bookTitleImagePath == null) {
            return false;
        }

        final var newBook = new BookImagePath(bookTitleImagePath, bookStatus, request.isUsable());
        this.repository.save(newBook);
        return true;
    }

    public boolean updateBook(final BookUpdateRequest request) {
        if ((request == null) || (this.repository == null) || (this.bookStatusService == null)
                || (this.bookTitleService == null)) {
            return false;
        }

        var bookImagePath = this.findBookImagePathById(request.getId());
        if (bookImagePath == null) {
            return false;
        }

        bookImagePath.setStatusIfNotNull(this.bookStatusService.findBookStatusById(request.getStatusId()));
        bookImagePath.setTitleIfNotNull(this.bookTitleService.findBookTitleImagePathById(request.getTitleId()));
        bookImagePath.setUsableIfNotNull(request.getIsUsable());
        this.repository.save(bookImagePath);

        return true;
    }
}
