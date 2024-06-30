package com.evilcorp.Spring_Testing;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long reader_id;
    private Long librarian_id;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(Long id, Long reader_id, String author) {
        this.id = id;
        this.reader_id = reader_id;
        this.author = author;
    }

    public Book() {
        this.id = null;
        this.reader_id = null;
        this.author = null;
    }
}
