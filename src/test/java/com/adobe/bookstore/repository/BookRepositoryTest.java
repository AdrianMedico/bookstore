package com.adobe.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.adobe.bookstore.models.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void repositoryIsNotNull() {
        assertThat(bookRepository).isNotNull();
    }

    @Test
    void bookRepositoryShouldFindBookWhenSaved() {
        String bookId = "0f6011c9-fe19-45a1-86fa-421641447172";
        // given
        bookRepository.save(new Book(bookId, "clean code", 1));

        // when
        Book book = bookRepository.findById(bookId).get();

        // then
        assertThat(book.getId()).isEqualTo(bookId);
    }

    @Test
    void bookRepositoryShouldFindBookWhenPopulatedAtStartup() {
        // given
        String bookIdFromData = "b6e8c865-2221-4435-9a65-d30ca0a63701";

        // when
        Book book = bookRepository.findById(bookIdFromData).get();

        // then
        assertThat(book.getName()).isEqualTo("ad laborum pariatur consequat commodo");
    }


}
