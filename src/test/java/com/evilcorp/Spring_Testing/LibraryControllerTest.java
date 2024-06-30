package com.evilcorp.Spring_Testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Range;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(LibraryController.class)
public class LibraryControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private LibraryService libraryService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book(1L, 1L, "Test Author");
    }

    @Test
    void getAllBooksTest() {

        when(libraryService.getAllBooks()).thenReturn((List<Book>) Flux.just(testBook));

        webTestClient.get().uri("/library/books")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class);
    }

    @Test
    void issueBookToReaderTest() {
        when(libraryService.bookIssuance(any(Long.class), any(Long.class), any(Long.class))).thenReturn(testBook);

        webTestClient.post().uri(uriBuilder ->
                        uriBuilder.path("/library/books/issue")
                                .queryParam("bookId", "1")
                                .queryParam("readerId", "2")
                                .queryParam("librerianId", "3")
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class);
    }

    @Test
    void returnBookByReaderTest() {
        when(libraryService.returnOfTheBook(any(Long.class))).thenReturn(testBook);

        webTestClient.post().uri(uriBuilder ->
                uriBuilder.path("/library/books/return")
                .queryParam("bookId", "1")
                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class);
    }

    @Test
    void createBookTest() {
        when(libraryService.createBook(any(String.class), any(String.class))).thenReturn(testBook);

        webTestClient.post().uri("/library/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class);
    }
}
