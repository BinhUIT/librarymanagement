package com.library.librarymanagement.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.library.librarymanagement.request.BookTypeCreationRequest;
import com.library.librarymanagement.request.BookTypeUpdateRequest;
import com.library.librarymanagement.resource.ResourceStrings;
import com.library.librarymanagement.entity.BookTypeImageData;
import com.library.librarymanagement.entity.BookTypeImagePath;
import com.library.librarymanagement.repository.BookTypeRepository;
import com.library.librarymanagement.ulti.File;

import jakarta.transaction.Transactional;

@Service
public class BookTypeService {
    private final BookTypeRepository repository;

    @Autowired(required = true)
    public BookTypeService(final BookTypeRepository repository) {
        this.repository = repository;
    }

    public List<BookTypeImageData> findAllBookTypes() {
        List<BookTypeImageData> result = new ArrayList<>();

        if (this.repository != null) {
            for (final var bookTypeImagePath : this.repository.findAll()) {
                result.add(new BookTypeImageData(bookTypeImagePath));
            }
        }

        return result;
    }

    public BookTypeImagePath getBookTypeImagePathById(final Short id) {
        if ((this.repository != null) && (id != null)) {
            return this.repository.findById(id).orElse(null);
        } else {
            return null;
        }
    }

    public BookTypeImageData findBookTypeImageDataById(final Short id) {
        final var bookTypeImagePath = this.getBookTypeImagePathById(id);
        if (bookTypeImagePath != null) {
            return new BookTypeImageData(bookTypeImagePath);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean createBookType(final BookTypeCreationRequest request) {
        boolean succeed = this.createBookTypeBusinessLogic(request);

        if (!succeed) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return succeed;
    }

    private boolean createBookTypeBusinessLogic(final BookTypeCreationRequest request) {
        if ((request == null) || (this.repository == null) || (ResourceStrings.DIR_BOOK_TYPE_IMAGE == null)) {
            return false;
        }

        final var newBookTypeName = request.getName();
        final var imageData = request.getImageData();
        if ((newBookTypeName == null) || (imageData == null)) {
            return false;
        }

        final var newImagePath = String.format("%s/%s", ResourceStrings.DIR_BOOK_TYPE_IMAGE, newBookTypeName);

        final var newBookTypeImagePath = new BookTypeImagePath(newBookTypeName, newImagePath);
        final var savedBookTypeImagePath = this.repository.save(newBookTypeImagePath);
        if (!newImagePath.equals(savedBookTypeImagePath.getImagePath())) {
            return false;
        }

        final var newImageFile = new File(newImagePath);
        return newImageFile.createAndWrite(imageData);
    }

    @Transactional
    public boolean updateBookType(final BookTypeUpdateRequest request) {
        boolean succeed = this.updateBookTypeBusinessLogic(request);

        if (!succeed) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return succeed;
    }

    private boolean updateBookTypeBusinessLogic(final BookTypeUpdateRequest request) {
        if ((request == null) || (this.repository == null) || (ResourceStrings.DIR_BOOK_TYPE_IMAGE == null)) {
            return false;
        }

        var bookTypeImagePath = this.getBookTypeImagePathById(request.getId());
        if (bookTypeImagePath == null) {
            return false;
        }

        final var oldImageFile = new File(bookTypeImagePath.getImagePath());
        if (!oldImageFile.writeIfBytesNotNull(request.getImageData())) {
            return false;
        }

        final var bookTypeNewName = request.getName();
        if (!bookTypeImagePath.setNameIfNotBlank(bookTypeNewName)) {
            return true;
        }

        final var newImagePath = String.format("%s/%s", ResourceStrings.DIR_BOOK_TYPE_IMAGE, bookTypeNewName);
        bookTypeImagePath.setImagePath(newImagePath);
        bookTypeImagePath = this.repository.save(bookTypeImagePath);
        if (!newImagePath.equals(bookTypeImagePath.getImagePath())) {
            return false;
        }

        final var newImageFile = new File(newImagePath);
        return oldImageFile.renameTo(newImageFile);
    }
}
