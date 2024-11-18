package com.library.librarymanagement.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.BookImagePath;
import com.library.librarymanagement.entity.ReturningCard;
import com.library.librarymanagement.entity.ReturningCardDetail;
import com.library.librarymanagement.entity.ReturningCardDetailId;
import com.library.librarymanagement.repository.ReturningCardDetailRepository;
import com.library.librarymanagement.request.ReturningCardDetailCreationRequest;
import com.library.librarymanagement.request.ReturningCardDetailDeletionRequest;
import com.library.librarymanagement.request.ReturningCardDetailUpdateRequest;

@Service
public class ReturningCardDetailService {
    private ReturningCardDetailRepository repository;
    private ReturningCardService returningCardService;
    private BookService bookService;

    public ReturningCardDetailService(final ReturningCardDetailRepository repository,
            final ReturningCardService returningCardService, final BookService bookService) {
        this.repository = repository;
        this.returningCardService = returningCardService;
        this.bookService = bookService;
    }

    public ReturningCardDetail findReturningCardDetailById(final ReturningCardDetailId id) {
        if ((id != null) && (this.repository != null)) {
            return this.repository.findById(id).orElse(null);
        } else {
            return null;
        }
    }

    public Set<BookImagePath> findBooksImagePathByReturningCardId(final Integer returningCardId) {
        if ((returningCardId == null) || (this.repository == null)) {
            return Collections.emptySet();
        }

        final var returningCardDetails = this.repository.findByReturingCardId(returningCardId);
        final var result = new HashSet<BookImagePath>(returningCardDetails.size());
        for (final var returningCardDetail : returningCardDetails) {
            result.add(returningCardDetail.getBook());
        }
        return result;
    }

    public boolean createReturningCardDetail(final ReturningCardDetailCreationRequest request) {
        if (request == null) {
            return false;
        }

        return this.createReturningCardDetail(request.getReturningCardDetailId());
    }

    public boolean createReturningCardDetail(final ReturningCardDetailId id) {
        if ((id == null) || (this.repository == null) ||
                (this.returningCardService == null) || (this.bookService == null)) {
            return false;
        }

        final var bookTitle = this.bookService.findBookImagePathById(id.getBookId());
        if (bookTitle == null) {
            return false;
        }

        final var returningCard = this.returningCardService.findReturningCardById(id.getReturningCardId());
        if (returningCard == null) {
            return false;
        }

        final var returningCardDetail = new ReturningCardDetail(returningCard, bookTitle);
        this.repository.save(returningCardDetail);
        return true;
    }

    public boolean createReturningCardDetail(final ReturningCard returningCard, final Integer bookTitleId) {
        if ((returningCard == null) || (this.repository == null) || (this.bookService == null)) {
            return false;
        }

        final var bookTitle = this.bookService.findBookImagePathById(bookTitleId);
        if (bookTitle == null) {
            return false;
        }

        final var returningCardDetail = new ReturningCardDetail(returningCard, bookTitle);
        this.repository.save(returningCardDetail);
        return true;
    }

    public boolean createReturningCardDetail(final ReturningCard returningCard, final BookImagePath book) {
        if ((returningCard == null) || (book == null) || (this.repository == null)) {
            return false;
        }

        final var returningCardDetail = new ReturningCardDetail(returningCard, book);
        this.repository.save(returningCardDetail);
        return true;
    }

    public boolean updateReturningCardDetail(final ReturningCardDetailUpdateRequest request) {
        if ((request == null) || (this.repository == null) || (!request.isValid())) {
            return false;
        }

        final var newBookTitleId = request.getNewBookTitleId();
        final BookImagePath newBook;
        if ((bookService != null) && (newBookTitleId != null)) {
            newBook = bookService.findBookImagePathById(newBookTitleId);
        } else {
            newBook = null;
        }

        final var newReturningCardId = request.getNewReturningCardId();
        final ReturningCard newReturningCard;
        if (((returningCardService != null) && (newReturningCardId != null))) {
            newReturningCard = returningCardService.findReturningCardById(newReturningCardId);
        } else {
            newReturningCard = null;
        }

        if (((newReturningCardId != null) && (newReturningCard == null))
                || ((newBookTitleId != null) && (newBook == null))) {
            return false;
        }

        var returningCardDetail = this.findReturningCardDetailById(request.getOldReturningCardDetailId());
        if (returningCardDetail == null) {
            return false;
        }

        if (newReturningCard != null) {
            returningCardDetail.setReturingCard(newReturningCard);
        }

        if (newBook != null) {
            returningCardDetail.setBook(newBook);
        }

        this.repository.save(returningCardDetail);
        return true;
    }

    public boolean deleteReturningCardDetail(final ReturningCardDetailDeletionRequest request) {
        if ((request == null) || (this.returningCardService == null) || (this.bookService == null)) {
            return false;
        }

        return this.deleteReturningCardDetail(
                this.returningCardService.findReturningCardById(request.getReturningCardId()),
                this.bookService.findBookImagePathById(request.getBookTitleId()));
    }

    private boolean deleteReturningCardDetail(final ReturningCard returningCard, final BookImagePath bookTitle) {
        if ((returningCard == null) || (bookTitle == null) || (this.repository == null)) {
            return false;
        }

        final var returningCardDetail = new ReturningCardDetail(returningCard, bookTitle);
        this.repository.delete(returningCardDetail);
        return true;
    }
}
