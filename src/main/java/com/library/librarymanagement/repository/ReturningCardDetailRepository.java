package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.librarymanagement.entity.ReturningCardDetail;
import com.library.librarymanagement.entity.ReturningCardDetailId;
import java.util.List;

public interface ReturningCardDetailRepository extends JpaRepository<ReturningCardDetail, ReturningCardDetailId> {
    List<ReturningCardDetail> findByReturingCardId(Integer returingCardId);
}
