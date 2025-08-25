package com.library.service;

import com.library.dto.BookDTO;
import com.library.dto.UserDTO;
import com.library.entity.Book;
import com.library.entity.User;
import com.library.exception.BookNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Value("${library.email}")
    private String libraryEmail;

    @Value("${owner.email}")
    private String ownerEmail;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
        return mapToDTO(book);
    }

    @Override
    public BookDTO createBook(BookDTO bookDto) {
        Book book = mapToEntity(bookDto);
        Book savedBook = bookRepository.save(book);

        emailService.sendEmail(
                libraryEmail,
                ownerEmail,
                "📘 New Book Added: " + savedBook.getTitle(),
                "A new book titled '" + savedBook.getTitle() + "' has been added to the library system."
        );

        return mapToDTO(savedBook);
    }

 
    public BookDTO updateBook(Long id, BookDTO updatedBookDto) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        existing.setTitle(updatedBookDto.getTitle());
        existing.setAuthor(updatedBookDto.getAuthor());
        existing.setIsbn(updatedBookDto.getIsbn());
        existing.setStatus(updatedBookDto.getStatus());

        return mapToDTO(bookRepository.save(existing));
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookDTO borrowBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + bookId));

        if (book.getStatus() == Book.Status.BORROWED) {
            throw new IllegalStateException("Book is already borrowed.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        book.setStatus(Book.Status.BORROWED);
        book.setBorrowedBy(user);
        bookRepository.save(book);

        emailService.sendEmail(
                libraryEmail,
                ownerEmail,
                "📚 Book Borrowed: " + book.getTitle(),
                "User " + user.getName() + " (" + user.getEmail() + ") has borrowed the book '" + book.getTitle() + "'."
        );

        return mapToDTO(book);
    }

    @Override
    public void returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + bookId));

        if (book.getStatus() == Book.Status.AVAILABLE) {
            throw new IllegalStateException("Book is already available.");
        }

        User borrower = book.getBorrowedBy();
        if (borrower != null) {
            borrower.getBorrowedBooks().remove(book);
            userRepository.save(borrower);
        }

        book.setStatus(Book.Status.AVAILABLE);
        book.setBorrowedBy(null);
        bookRepository.save(book);

        emailService.sendEmail(
                libraryEmail,
                ownerEmail,
                "🔄 Book Returned: " + book.getTitle(),
                "Book '" + book.getTitle() + "' has been returned to the library."
        );
    }

    // ---------------------- 🔁 DTO MAPPERS ----------------------

    private BookDTO mapToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setStatus(book.getStatus());

        if (book.getBorrowedBy() != null) {
            User user = book.getBorrowedBy();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            dto.setBorrowedByUserId(userDTO);
        }

        return dto;
    }

    private Book mapToEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setStatus(dto.getStatus() != null ? dto.getStatus() : Book.Status.AVAILABLE);
        return book;
    }

	@Override
	public BookDTO createBook(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookDTO updateBook(Long id, Book book) {
		// TODO Auto-generated method stub
		return null;
	}
}
