package com.evilcorp.Spring_Testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private BookRepository bookRepository;

    public Book createBook(String title, String author) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setAvailable(true);
        return bookRepository.save(book);
    }

    //выдача бибилиотекорем книги читателю
    public Book bookIssuance(Long bookId, Long readerId, Long librarianId) {
        if (bookRepository.getReferenceById(bookId).isAvailable() == true) {
            bookRepository.getReferenceById(bookId).setReader_id(readerId);
            bookRepository.getReferenceById(bookId).setLibrarian_id(librarianId);
            bookRepository.getReferenceById(bookId).setAvailable(false);
        }
        return bookRepository.getReferenceById(bookId);
    }

    //возвращение книги читателем бибилотекорю
    public Book returnOfTheBook(Long bookId) {
        if (bookRepository.getReferenceById(bookId).isAvailable() == false) {
            bookRepository.getReferenceById(bookId).setLibrarian_id(null);
            bookRepository.getReferenceById(bookId).setReader_id(null);
            bookRepository.getReferenceById(bookId).setAvailable(true);
        }
        return bookRepository.getReferenceById(bookId);
    }

    public List<Book> getAllBooks() {
        return List.copyOf(bookRepository.findAll());
    }
}
