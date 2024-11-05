package com.library.librarymanagement.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.library.librarymanagement.request.BookTitleCreationRequest;
import com.library.librarymanagement.request.BookTitleUpdateRequest;
import com.library.librarymanagement.resource.ResourceStrings;
import com.library.librarymanagement.entity.BookTitleImageData;
import com.library.librarymanagement.entity.BookTitleImagePath;
import com.library.librarymanagement.repository.BookTitleRepository;
import com.library.librarymanagement.ulti.File;

import jakarta.transaction.Transactional;

@Service
public class BookTitleService {
    private final BookTitleRepository repository;
    private final BookTypeService bookTypeService;

    @Autowired(required = true)
    public BookTitleService(final BookTitleRepository repository, final BookTypeService bookTypeService) {
        this.repository = repository;
        this.bookTypeService = bookTypeService;
    }

    public List<BookTitleImagePath> findBookTitlesImagePathByPage(final Pageable pageable) {
        if ((this.repository != null) && (pageable != null)) {
            return this.repository.findAll(pageable).getContent();
        } else {
            return Collections.emptyList();
        }
    }

    public List<BookTitleImageData> findBookTitlesImageDataByPage(final Pageable pageable) {
        List<BookTitleImageData> result = new ArrayList<>();

        final var listBookTitleImagePath = this.findBookTitlesImagePathByPage(pageable);
        for (final var bookTitleImagePath : listBookTitleImagePath) {
            result.add(new BookTitleImageData(bookTitleImagePath));
        }

        return result;
    }

    public BookTitleImagePath findBookTitleImagePathById(final Integer id) {
        if ((this.repository != null) && (id != null)) {
            return this.repository.findById(id).orElse(null);
        } else {
            return null;
        }
    }

    public BookTitleImageData findBookTitleImageDataById(final Integer id) {
        final var bookTitleImagePath = this.findBookTitleImagePathById(id);
        if (bookTitleImagePath != null) {
            return new BookTitleImageData(bookTitleImagePath);
        } else {
            return null;
        }
    }

    private List<BookTitleImagePath> findBookTitlesImagePathByPageWithCriteria(final Pageable pageable,
            final String bookTitleNameKeyword, final String bookTitleAuthorKeyword, final Set<Short> bookTypesIdSet) {
        if (this.repository == null) {
            return Collections.emptyList();
        }

        final var page = this.repository.findByPageWithCriteria(pageable, bookTitleNameKeyword,
                bookTitleAuthorKeyword, bookTypesIdSet);
        if (page == null) {
            return Collections.emptyList();
        }
        return page.getContent();
    }

    public List<BookTitleImageData> findBookTitlesImageDataByPageWithCriteria(final Pageable pageable,
            final String bookTitleNameKeyword, final String bookTitleAuthorKeyword, final Set<Short> bookTypesIdSet) {
        List<BookTitleImageData> result = new ArrayList<>();

        final var listBookTitleImagePath = this.findBookTitlesImagePathByPageWithCriteria(
                pageable, bookTitleNameKeyword, bookTitleAuthorKeyword, bookTypesIdSet);
        for (final var bookTitleImagePath : listBookTitleImagePath) {
            result.add(new BookTitleImageData(bookTitleImagePath));
        }

        return result;
    }

    @Transactional
    public boolean createBookTitle(final BookTitleCreationRequest request) {
        boolean succeed = this.createBookTitleBusinessLogic(request);

        if (!succeed) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return succeed;
    }

    private boolean createBookTitleBusinessLogic(final BookTitleCreationRequest request) {
        if ((request == null) || (this.repository == null) || (this.bookTypeService == null)
                || (ResourceStrings.DIR_BOOK_TITLE_IMAGE == null)) {
            return false;
        }

        final var newBookTitleName = request.getName();
        final var imageData = request.getImageData();
        final var newBookTitleAuthor = request.getAuthor();
        if ((newBookTitleName == null) || (imageData == null) || (newBookTitleAuthor == null)) {
            return false;
        }

        final var bookTypeImagePath = this.bookTypeService.getBookTypeImagePathById(request.getTypeId());
        if (bookTypeImagePath == null) {
            return false;
        }

        final var newImagePath = String.format("%s/%s", ResourceStrings.DIR_BOOK_TITLE_IMAGE, request.getName());

        final var bookTitleImagePath = new BookTitleImagePath(
                newBookTitleName,
                bookTypeImagePath,
                newBookTitleAuthor,
                newImagePath);
        final var savedBookTitleImagePath = this.repository.save(bookTitleImagePath);
        if (!newImagePath.equals(savedBookTitleImagePath.getImagePath())) {
            return false;
        }

        final var newImageFile = new File(newImagePath);
        return newImageFile.createAndWrite(imageData);
    }

    @Transactional
    public boolean updateBookTitle(final BookTitleUpdateRequest request) {
        boolean succeed = this.updateBookTitleBusinessLogic(request);

        if (!succeed) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return succeed;
    }

    public boolean updateBookTitleBusinessLogic(final BookTitleUpdateRequest request) {
        if ((request == null) || (this.repository == null) || (this.bookTypeService == null)
                || (ResourceStrings.DIR_BOOK_TITLE_IMAGE == null)) {
            return false;
        }

        var bookTitleImagePath = this.findBookTitleImagePathById(request.getId());
        if (bookTitleImagePath == null) {
            return false;
        }

        final var oldImageFile = new File(bookTitleImagePath.getImagePath());
        if (!oldImageFile.writeIfBytesNotNull(request.getImageData())) {
            return false;
        }

        bookTitleImagePath.setTypeIfNotNull(this.bookTypeService.getBookTypeImagePathById(request.getTypeId()));
        bookTitleImagePath.setAuthorIfNotBlank(request.getAuthor());

        final var bookTitleNewName = request.getName();
        if (!bookTitleImagePath.setNameIfNotBlank(bookTitleNewName)) {
            this.repository.save(bookTitleImagePath);
            return true;
        }

        final var newImagePath = String.format("%s/%s", ResourceStrings.DIR_BOOK_TITLE_IMAGE, bookTitleNewName);
        bookTitleImagePath.setImagePath(newImagePath);
        bookTitleImagePath = this.repository.save(bookTitleImagePath);
        if (!newImagePath.equals(bookTitleImagePath.getImagePath())) {
            return false;
        }

        final var newImageFile = new File(newImagePath);
        return oldImageFile.renameTo(newImageFile);
    }
}
