package com.library.librarymanagement.service;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.request.BookTitleCreationRequest;
import com.library.librarymanagement.request.BookTitleUpdateRequest;
import com.library.librarymanagement.resource.ResourceStrings;
import com.library.librarymanagement.entity.BookTitleImageData;
import com.library.librarymanagement.entity.BookTitleImagePath;
import com.library.librarymanagement.repository.BookTitleRepository;
import com.library.librarymanagement.repository.BookTypeRepository;
import com.library.librarymanagement.ulti.File;

@Service
public final class BookTitleService {
    private final BookTitleRepository bookTitleRepository;
    private final BookTypeRepository bookTypeRepository;

    @Autowired(required = true)
    private BookTitleService(final BookTitleRepository bookTitleRepository,
            final BookTypeRepository bookTypeRepository) {
        this.bookTitleRepository = bookTitleRepository;
        this.bookTypeRepository = bookTypeRepository;
    }

    private List<BookTitleImagePath> getBookTitlesImagePathByPage(final Integer start, final Integer amount) {
        List<BookTitleImagePath> result = Collections.emptyList();

        if ((this.bookTitleRepository != null) && (start >= 0) && (amount > 0)) {
            final var pageable = PageRequest.of(start, amount);
            result = this.bookTitleRepository.findAll(pageable).getContent();
        }

        return result;
    }

    public List<BookTitleImageData> getBookTitlesImageDataByPage(final Integer start, final Integer amount) {
        List<BookTitleImageData> result = new ArrayList<>();

        final var listBookTitleImagePath = this.getBookTitlesImagePathByPage(start, amount);
        for (final var bookTitleImagePath : listBookTitleImagePath) {
            result.add(new BookTitleImageData(bookTitleImagePath));
        }

        return result;
    }

    private BookTitleImagePath getBookTitleImagePathById(final Integer id) {
        BookTitleImagePath result = null;

        if ((this.bookTitleRepository != null) && (id != null)) {
            result = this.bookTitleRepository.findById(id).orElse(null);
        }

        return result;
    }

    public BookTitleImageData getBookTitleImageDataById(final Integer id) {
        BookTitleImageData result = null;

        final var bookTitleImagePath = this.getBookTitleImagePathById(id);
        if (bookTitleImagePath != null) {
            result = new BookTitleImageData(bookTitleImagePath);
        }

        return result;
    }

    public boolean createBookTitle(final BookTitleCreationRequest request) {
        if ((this.bookTitleRepository == null) || (request == null) || (ResourceStrings.DIR_BOOK_TITLE_IMAGE == null)
                || this.bookTitleRepository.existsByName(request.getName())) {
            return false;
        }

        final var newBookTypeImagePath = this.bookTypeRepository.findById(request.getTypeId()).orElse(null);
        if (newBookTypeImagePath == null) {
            return false;
        }

        var newImagePath = String.format("%s/%s", ResourceStrings.DIR_BOOK_TITLE_IMAGE, request.getName());
        var newImageFile = new File(newImagePath);
        if (!newImageFile.createAndWrite(request.getImageData())) {
            return false;
        }

        final var newBookTitleImagePath = new BookTitleImagePath(request.getName(), newBookTypeImagePath,
                request.getAuthor(), newImagePath);

        this.bookTitleRepository.save(newBookTitleImagePath);
        return true;
    }

    public boolean updateBookTitle(final BookTitleUpdateRequest request) {
        if ((request == null) || (this.bookTitleRepository == null) || (this.bookTypeRepository == null)
                || (ResourceStrings.DIR_BOOK_TITLE_IMAGE == null)) {
            return false;
        }

        final var newBookTypeImagePath = this.bookTypeRepository.findById(request.getTypeId()).orElse(null);
        if (newBookTypeImagePath == null) {
            return false;
        }

        var bookTitleImagePath = this.getBookTitleImagePathById(request.getId());
        if (bookTitleImagePath == null) {
            return false;
        }

        var oldImagePath = bookTitleImagePath.getImagePath();
        var newImagePath = String.format("%s/%s", ResourceStrings.DIR_BOOK_TITLE_IMAGE, request.getName());
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

        bookTitleImagePath.setName(request.getName());
        bookTitleImagePath.setType(newBookTypeImagePath);
        bookTitleImagePath.setAuthor(request.getAuthor());
        bookTitleImagePath.setImagePath(newImagePath);
        this.bookTitleRepository.save(bookTitleImagePath);

        return true;
    }
}
