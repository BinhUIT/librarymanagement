package com.library.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.Notification;
import com.library.librarymanagement.entity.User;
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    public List<Notification> findByReader(User reader);
}
