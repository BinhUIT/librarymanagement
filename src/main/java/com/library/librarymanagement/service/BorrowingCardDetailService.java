package com.library.librarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.BookImagePath;
import com.library.librarymanagement.entity.BookTitleImagePath;
import com.library.librarymanagement.entity.BorrowingCardDetail;
import com.library.librarymanagement.entity.BorrowingCardDetail.Status;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.BookStatusRepository;
import com.library.librarymanagement.repository.BookTitleRepository;
import com.library.librarymanagement.repository.BorrowingCardDetailRepository;
import com.library.librarymanagement.request.UpdateBorrowingCardDetailRequest;

@Service
public class BorrowingCardDetailService { 
    @Autowired
    private BookRepository bookRepo;
    @Autowired 
    private BookTitleRepository bookTitleRepo; 

    @Autowired 
    private BookStatusRepository bookStatusRepo;
    public final BorrowingCardDetailRepository repository;

    public BorrowingCardDetailService(final BorrowingCardDetailRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Boolean> UpdateBorrowingStatus(final UpdateBorrowingCardDetailRequest request) {
        if ((request == null) || (this.repository == null)) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        final var serviceId = request.getServiceId();
        final var newStatus = request.getStatus();
        if ((serviceId == null) || (newStatus == null)) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        final var booksId = request.getBooksId();

        final List<BorrowingCardDetail> listDetails;
        if ((booksId == null) || (booksId.isEmpty())) {
            listDetails = repository.findByService_ServiceId(serviceId);
        } else {
            listDetails = repository.findByService_ServiceIdAndBook_IdIn(serviceId, booksId);
        }

        for (final var detail : listDetails) {
            System.out.println(detail.getId());
            if (!detail.updateStatus(newStatus)) {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }

        repository.saveAll(listDetails);
        if(request.getStatus()==Status.CANCELLED||request.getStatus()==Status.RETURNED) 
        {
            for(final var detail: listDetails) 
            {
                BookImagePath bookImagePath = bookRepo.findById(detail.getBook().getId()).orElse(null);
                bookImagePath.setStatus(bookStatusRepo.findById((byte)0).orElse(null)); 
                bookRepo.save(bookImagePath); 

                BookTitleImagePath bookTitle = bookTitleRepo.findById(detail.getBook().getTitle().getId()).orElse(null);
                bookTitle.setAmountRemaining(bookTitle.getAmountRemaining()+1); 
                bookTitleRepo.save(bookTitle);
            }
        } 

        System.out.println("Success");
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
