package com.library.librarymanagement.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.request.BookTitleCreationRequest;
import com.library.librarymanagement.request.BookTitleUpdateRequest;
import com.library.librarymanagement.config.DotenvConfig;
import com.library.librarymanagement.entity.BookTitleImagePath;
import com.library.librarymanagement.repository.BookTitleRepository;
import com.library.librarymanagement.repository.BookTypeRepository;
import com.library.librarymanagement.ulti.File;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public final class BookTitleService {
    private static final String IMAGE_FOLDER_PATH = "C:/Users/Snow/Desktop/Test";

    private final BookTitleRepository bookTitleRepository;
    private final BookTypeRepository bookTypeRepository;
    private final Dotenv dotenv;

    @Autowired(required = true)
    private BookTitleService(final BookTitleRepository bookTitleRepository,
            final BookTypeRepository bookTypeRepository, final Dotenv dotenv) {
        this.bookTitleRepository = bookTitleRepository;
        this.bookTypeRepository = bookTypeRepository;
        this.dotenv = dotenv;
    }

    public List<BookTitleImagePath> getBookTitles(final Integer start, final Integer amount) {
        List<BookTitleImagePath> result = Collections.emptyList();

        if ((this.bookTitleRepository != null) && (start >= 0) && (amount > 0)) {
            final var pageable = PageRequest.of(start, amount);
            result = this.bookTitleRepository.findAll(pageable).getContent();
        }

        return result;
    }

    public BookTitleImagePath getBookTitleImagePathById(final Integer id) {
        BookTitleImagePath result = null;

        if ((this.bookTitleRepository != null) && (id != null)) {
            result = this.bookTitleRepository.findById(id).orElse(null);
        }

        return result;
    }

    public boolean createBookTitle(final BookTitleCreationRequest request) {
        if ((this.bookTitleRepository == null) || (request == null)
                || this.bookTitleRepository.existsByName(request.getName())) {
            return false;
        }

        final var newBookTypeImagePath = this.bookTypeRepository.findById(request.getTypeId()).orElse(null);
        if (newBookTypeImagePath == null) {
            return false;
        }

        var newImagePath = String.format("%s/%s/%s", IMAGE_FOLDER_PATH, "BookTitle", request.getName());
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
        if ((request == null) || (this.bookTitleRepository == null) || (this.bookTypeRepository == null)) {
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
        var newImagePath = String.format("%s/%s/%s", IMAGE_FOLDER_PATH, "BookTitle", request.getName());
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
