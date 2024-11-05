package com.library.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.BookTitleImagePath;
import com.library.librarymanagement.entity.CartDetail;
import com.library.librarymanagement.entity.User;
@Repository 
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> { 
    public List<CartDetail> findByUser(User user); 
    public List<CartDetail> findByBookTitle(BookTitleImagePath bookTitle);
}
