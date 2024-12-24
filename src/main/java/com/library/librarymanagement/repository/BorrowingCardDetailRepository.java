package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.BookImagePath;
import com.library.librarymanagement.entity.BorrowingCardDetail;

import java.util.List;
import java.util.Set;

@Repository
public interface BorrowingCardDetailRepository extends JpaRepository<BorrowingCardDetail, Integer>{
    List<BorrowingCardDetail> findByService_ServiceId(final Integer id);

    List<BorrowingCardDetail> findByService_ServiceIdAndBook_IdIn(Integer serviceId, Set<Integer> bookIds);   

    List<BorrowingCardDetail> findByBook(BookImagePath book);

    BorrowingCardDetail findByService_ServiceIdAndBook_Id(Integer serviceId, Integer bookId);

    

}
