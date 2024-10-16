package com.library.librarymanagement.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.request.BookTypeCreationRequest;
import com.library.librarymanagement.request.BookTypeUpdateRequest;
import com.library.librarymanagement.entity.BookTypeImageData;
import com.library.librarymanagement.entity.BookTypeImagePath;
import com.library.librarymanagement.repository.BookTypeRepository;
import com.library.librarymanagement.ulti.File;

@Service
public final class BookTypeService {
    private static final String IMAGE_FOLDER_PATH = "C:/Users/Snow/Desktop/Test";

    private final BookTypeRepository repository;

    @Autowired(required = true)
    public BookTypeService(final BookTypeRepository repository) {
        this.repository = repository;
    }

    public List<BookTypeImageData> getBookTypes() {
        List<BookTypeImageData> result = new ArrayList<>();

        if (this.repository != null) {
            for (final var bookTypeImagePath : this.repository.findAll()) {
                result.add(new BookTypeImageData(bookTypeImagePath));
            }
        }

        return result;
    }

    private BookTypeImagePath getBookTypeImagePathById(final Short id) {
        BookTypeImagePath result = null;

        if ((this.repository != null) && (id != null)) {
            result = this.repository.findById(id).orElse(null);
        }

        return result;
    }

    public BookTypeImageData getBookTypeImageById(final Short id) {
        BookTypeImageData result = null;

        final var bookTypeImagePath = this.getBookTypeImagePathById(id);
        if (bookTypeImagePath != null) {
            result = new BookTypeImageData(bookTypeImagePath);
        }

        return result;
    }

    public boolean createBookType(final BookTypeCreationRequest request) {
        if ((request == null) || (this.repository == null) || (this.repository.existsByName(request.getName()))) {
            return false;
        }

        var newImagePath = String.format("%s/%s/%s", IMAGE_FOLDER_PATH, "BookType",
                request.getName());
        var newImageFile = new File(newImagePath);
        if (!newImageFile.createAndWrite(request.getImageData())) {
            return false;
        }

        final var newBookType = new BookTypeImagePath(request.getName(), newImagePath);
        this.repository.save(newBookType);

        return true;
    }

    public boolean updateBookType(final BookTypeUpdateRequest request) {
        if ((request == null) || (this.repository == null)) {
            return false;
        }

        var bookType = this.getBookTypeImagePathById(request.getId());
        if (bookType == null) {
            return false;
        }

        var oldImagePath = bookType.getImagePath();
        var newImagePath = String.format("%s/%s/%s", IMAGE_FOLDER_PATH, "BookType",
                request.getName());
        if (oldImagePath == null || newImagePath == null) {
            return false;
        }

        var oldImageFile = new File(oldImagePath);
        if (newImagePath.equals(oldImagePath)) {
            return oldImageFile.write(request.getImageData());
        }
        if (Boolean.TRUE.equals(oldImageFile.isExisted()) && Boolean.FALSE.equals(oldImageFile.isModifiable())) {
            return false;
        }

        var newImageFile = new File(newImagePath);
        if ((!newImageFile.createAndWrite(request.getImageData())) || (!oldImageFile.delete())) {
            newImageFile.delete();
            return false;
        }

        bookType.setName(request.getName());
        bookType.setImagePath(newImagePath);
        this.repository.save(bookType);

        return true;
    }
}
