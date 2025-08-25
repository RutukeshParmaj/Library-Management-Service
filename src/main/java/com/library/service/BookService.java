package com.library.service;

import com.library.dto.BookDTO;
import com.library.entity.Book;

import java.util.List;

public interface BookService {
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO createBook(Book book);
    BookDTO updateBook(Long id, BookDTO book);
    void deleteBook(Long id);
    BookDTO borrowBook(Long bookId, Long userId);
    void returnBook(Long bookId); // ✅ Add this line
	BookDTO createBook(BookDTO bookDto);
	BookDTO updateBook(Long id, Book book);
}
