package com.library.librarymanagement.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.library.librarymanagement.entity.BookTitleImagePath;

public interface BookTitleCriteriaByPageRepository {
    Page<BookTitleImagePath> findByPageWithCriteria(
            Pageable pageable,
            String bookTitleNameKeyword,
            String bookTitleAuthorKeyword,
            Set<Short> bookTypesIdSet);
}
