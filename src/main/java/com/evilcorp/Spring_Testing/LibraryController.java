package com.evilcorp.Spring_Testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {


    private LibraryService libraryService;

    // Получение списка всех книг в библиотеке
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = libraryService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Выдача книги читателю
    @PostMapping("/books/issue")
    public ResponseEntity<Book> issueBookToReader(@RequestParam Long bookId, @RequestParam Long readerId, @RequestParam Long librerianId) {
        Book book = libraryService.bookIssuance(bookId, readerId, librerianId);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Возврат книги в библиотеку
    @PostMapping("/books/return")
    public ResponseEntity<Book> returnBookByReader(@RequestParam Long bookId) {
        Book book = libraryService.returnOfTheBook(bookId);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Создание новой книги в библиотеке
    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book newBook = libraryService.createBook(book.getTitle(), book.getAuthor());
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }
}
