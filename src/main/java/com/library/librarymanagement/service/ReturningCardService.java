package com.library.librarymanagement.service;

import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.ReturningCard;
import com.library.librarymanagement.repository.ReturningCardRepository;
import com.library.librarymanagement.request.ReturningCardCreationRequest;
import com.library.librarymanagement.request.ReturningCardUpdateRequest;

@Service
public class ReturningCardService {
    private final ReturningCardRepository repository;
    private final UserService userService;

    public ReturningCardService(final ReturningCardRepository repository, final UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public ReturningCard findReturningCardById(final Integer id) {
        if ((id != null) && (this.repository != null)) {
            return this.repository.findById(id).orElse(null);
        } else {
            return null;
        }
    }

    public boolean createReturningCard(final ReturningCardCreationRequest request) {
        if ((request == null) || (this.repository == null)) {
            return false;
        }

        // TODO: Confirm the role
        final var librarian = this.userService.findUserById(request.getLibrarianId());
        if ((librarian == null) || (librarian.getRole() != 3)) {
            return false;
        }

        final var reader = this.userService.findUserById(request.getReaderId());
        if ((reader == null) || (reader.getRole() != 2)) {
            return false;
        }

        this.repository.save(new ReturningCard(reader, librarian));
        return true;
    }

    public boolean updateReturningCard(final ReturningCardUpdateRequest request) {
        if ((request == null) || (this.repository == null) || (this.userService == null)) {
            return false;
        }

        var returningCard = this.findReturningCardById(request.getReturningCardId());
        if (returningCard == null) {
            return false;
        }

        final var newLibrarianId = request.getLibrarianId();
        if (newLibrarianId != null) {
            final var newLibrarian = this.userService.findUserById(newLibrarianId);
            if (newLibrarian != null) {
                returningCard.setLibrarian(newLibrarian);
            } else {
                return false;
            }
        }

        final var newReaderId = request.getReaderId();
        if (newReaderId != null) {
            final var newReader = this.userService.findUserById(newReaderId);
            if (newReader != null) {
                returningCard.setReader(newReader);
            } else {
                return false;
            }
        }

        this.repository.save(returningCard);
        return true;
    }
}
