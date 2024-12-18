package com.library.librarymanagement.service;

import java.util.List;
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

    public boolean updateBorrowingStatus(final UpdateBorrowingCardDetailRequest request) {
        if ((request == null) || (this.repository == null)) {
            return false;
        }

        final var serviceId = request.getServiceId();
        final var newStatus = request.getStatus();
        if ((serviceId == null) || (newStatus == null)) {
            return false;
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
                return false;
            }
        }

        repository.saveAll(listDetails);
        return true;
    }
}
