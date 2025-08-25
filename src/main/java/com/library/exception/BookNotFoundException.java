package com.library.exception;

//This is custom exception class invokes when book is not found
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
