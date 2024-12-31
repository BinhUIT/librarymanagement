package com.library.librarymanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.BookImageData;
import com.library.librarymanagement.entity.BookImagePath;
import com.library.librarymanagement.entity.BookStatus;
import com.library.librarymanagement.entity.BookTitleImagePath;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.request.BookCreationRequest;
import com.library.librarymanagement.request.BookUpdateRequest;

@Service
public final class BookService {
    private final BookRepository repository;
    private final BookTitleService bookTitleService;
    private final BookStatusService bookStatusService;

    @Autowired(required = true)
    private BookService(final BookRepository repository, final BookTitleService bookTitleService,
            final BookStatusService bookStatusService) {
        this.repository = repository;
        this.bookTitleService = bookTitleService;
        this.bookStatusService = bookStatusService;
    }

    public BookImagePath getBookImagePathById(final Integer id) {
        final BookImagePath result;

        if (id != null) {
            result = this.repository.findById(id).orElse(null);
        } else {
            result = null;
        }

        return result;
    }

    public BookImageData getBookImageDataById(final Integer id) {
        final var bookImagePath = this.getBookImagePathById(id);

        final BookImageData result;
        if (bookImagePath != null) {
            result = new BookImageData(bookImagePath);
        } else {
            result = null;
        }

        return result;
    }

    public boolean createBook(final BookCreationRequest request) {
        boolean result = false;

        final BookTitleImagePath bookTitleImagePath;
        if (request != null) {
            bookTitleImagePath = this.bookTitleService.getBookTitleImagePathById(request.getTitleId());
        } else {
            bookTitleImagePath = null;
        }

        final BookStatus bookStatus;
        if (bookTitleImagePath != null) {
            bookStatus = this.bookStatusService.getBookStatusById(request.getStatusId());
        } else {
            bookStatus = null;
        }

        if (bookStatus != null) {
            final var newBook = new BookImagePath(bookTitleImagePath, bookStatus, request.isUsable());
            this.repository.save(newBook);
            result = true;
        }

        return result;
    }

    public boolean updateBook(final BookUpdateRequest request) {
        boolean result = false;

        final Integer bookId;
        if (request != null) {
            bookId = request.getId();
        } else {
            bookId = null;
        }

        final BookImagePath book;
        if (bookId != null) {
            book = this.repository.findById(bookId).orElse(null);
        } else {
            book = null;
        }

        final BookTitleImagePath newBookTitleImagePath;
        if (book != null) {
            newBookTitleImagePath = this.bookTitleService.getBookTitleImagePathById(request.getTitleId());
        } else {
            newBookTitleImagePath = null;
        }

        if (newBookTitleImagePath != null) {
            book.setTitle(newBookTitleImagePath);
        }

        final BookStatus newBookStatus;
        if (book != null) {
            newBookStatus = this.bookStatusService.getBookStatusById(request.getStatusId());
        } else {
            newBookStatus = null;
        }

        if (newBookStatus != null) {
            book.setStatus(newBookStatus);
        }

        final Boolean newBookIsUsable;
        if (book != null) {
            newBookIsUsable = request.getIsUsable();
        } else {
            newBookIsUsable = null;
        }

        if (newBookIsUsable != null) {
            book.setIsUsable(newBookIsUsable);
        }

        if (book != null) {
            this.repository.save(book);
            result = true;
        }

        return result;
    }    

    public List<BookImagePath> getAllBook() 
    { 
        List<BookImagePath> listBook = repository.findAll();
        List<BookImagePath> listRes= new ArrayList<>();
        for(int i=0;i<listBook.size();i++)
        {
            if(listBook.get(i).getIsUsable()==true||(listBook.get(i).getIsUsable()==false&&listBook.get(i).getStatus().getId()==(byte)3) )
            {
                listRes.add(listBook.get(i));
            } 
        } 
        return listRes;
    }

    
    
}
