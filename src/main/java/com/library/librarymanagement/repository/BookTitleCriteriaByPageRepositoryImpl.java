package com.library.librarymanagement.repository;

import com.library.librarymanagement.entity.BookTitleImagePath;
import com.library.librarymanagement.entity.BookTypeImagePath;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class BookTitleCriteriaByPageRepositoryImpl implements BookTitleCriteriaByPageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final CriteriaBuilder criteriaBuilder;

    public BookTitleCriteriaByPageRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public Page<BookTitleImagePath> findByPageWithCriteria(
            final Pageable pageable,
            final String bookTitleNameKeyword,
            final String bookTitleAuthorKeyword,
            final Set<Short> bookTypesIdSet) {
        final var query = this.criteriaBuilder.createQuery(BookTitleImagePath.class);
        final var root = query.from(BookTitleImagePath.class);

        // Tạo danh sách Predicate cho truy vấn chính và truy vấn đếm
        final var predicates = buildPredicates(root, bookTitleNameKeyword, bookTitleAuthorKeyword, bookTypesIdSet);
        query.where(this.criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        // Sắp xếp theo yêu cầu của Pageable
        pageable.getSort().forEach((Order order) -> {
            if (order.isAscending()) {
                query.orderBy(this.criteriaBuilder.asc(root.get(order.getProperty())));
            } else {
                query.orderBy(this.criteriaBuilder.desc(root.get(order.getProperty())));
            }
        });

        // Tạo truy vấn để lấy kết quả phân trang
        final var typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Lấy danh sách kết quả
        final var results = typedQuery.getResultList();

        // Tạo truy vấn để đếm tổng số bản ghi
        final long totalRecords = countTotalRecords(bookTitleNameKeyword, bookTitleAuthorKeyword, bookTypesIdSet);

        // Trả về Page kết quả
        return new PageImpl<>(results, pageable, totalRecords);
    }

    private List<Predicate> buildPredicates(final Root<BookTitleImagePath> root,
            final String bookTitleNameKeyword, final String bookTitleAuthorKeyword, final Set<Short> bookTypesIdSet) {
        final List<Predicate> predicates = new ArrayList<>();

        if (bookTitleNameKeyword != null && !bookTitleNameKeyword.isBlank()) {
            predicates.add(this.criteriaBuilder.like(root
                    .get(BookTitleImagePath.Fields.name),
                    "%" + bookTitleNameKeyword + "%"));
        }
        if (bookTitleAuthorKeyword != null && !bookTitleAuthorKeyword.isBlank()) {
            predicates.add(this.criteriaBuilder.like(root
                    .get(BookTitleImagePath.Fields.author),
                    "%" + bookTitleAuthorKeyword + "%"));
        }
        if (bookTypesIdSet != null && !bookTypesIdSet.isEmpty()) {
            predicates.add(root.get(BookTitleImagePath.Fields.type)
                    .get(BookTypeImagePath.Fields.id).in(bookTypesIdSet));
        }
        return predicates;
    }

    private long countTotalRecords(final String bookTitleNameKeyword,
            final String bookTitleAuthorKeyword, final Set<Short> bookTypesIdSet) {
        final var countQuery = this.criteriaBuilder.createQuery(Long.class);
        final var countRoot = countQuery.from(BookTitleImagePath.class);
        final var countPredicates = buildPredicates(countRoot,
                bookTitleNameKeyword, bookTitleAuthorKeyword, bookTypesIdSet);
        countQuery.select(this.criteriaBuilder.count(countRoot))
                .where(this.criteriaBuilder.and(countPredicates.toArray(new Predicate[0])));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
