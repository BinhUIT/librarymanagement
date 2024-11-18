package com.library.librarymanagement.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldNameConstants
@Table(name = "ReturningCard")
@DynamicInsert
@DynamicUpdate
public final class ReturningCard {
    @Id
    @Column(name = "Id", nullable = false, unique = true, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Integer id = null;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ReaderId", nullable = false)
    private User reader = null;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Librarian", nullable = false)
    private User librarian = null;

    @Column(name = "Date", nullable = false)
    private LocalDate date = null;

    public ReturningCard(final User reader, final User librarian) {
        this.reader = reader;
        this.librarian = librarian;
    }

    public ReturningCard(final User reader, final User librarian, final LocalDate date) {
        this.reader = reader;
        this.librarian = librarian;
        this.date = date;
    }
}
