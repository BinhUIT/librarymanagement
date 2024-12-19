package com.library.librarymanagement.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.BorrowingCardDetail;
import com.library.librarymanagement.repository.BorrowingCardDetailRepository;
import com.library.librarymanagement.request.UpdateBorrowingCardDetailRequest;

@Service
public class BorrowingCardDetailService {
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
            if (!detail.updateStatus(newStatus)) {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }

        repository.saveAll(listDetails);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
