package com.library.dto;

import com.library.entity.Book;
import com.library.entity.Book.Status;

public class BookDTO {

	private Long id;
	private String title;
	private String author;
	private String isbn;
	private Book.Status status;
	private UserDTO borrowedByUserId;

	// Constructors
	public BookDTO() {
	}

	public BookDTO(Long id, String title, String author, String isbn, Book.Status status, UserDTO borrowedByUserId) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.status = status;
		this.borrowedByUserId = borrowedByUserId;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Book.Status status) {
		this.status = status;
	}

	public UserDTO getBorrowedByUserId() {
		return borrowedByUserId;
	}

	public void setBorrowedByUserId(UserDTO userDTO) {
		this.borrowedByUserId = userDTO;
	}
}
