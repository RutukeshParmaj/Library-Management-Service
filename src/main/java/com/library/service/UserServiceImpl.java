package com.library.service;

import com.library.dto.UserDTO;
import com.library.entity.Book;
import com.library.entity.User;
import com.library.exception.BookNotFoundException;
import com.library.exception.DuplicateEmailException;
import com.library.exception.UserNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	public UserDTO createUser(UserDTO user) {
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new DuplicateEmailException("Email already registered: " + user.getEmail());
		}
		System.out.print("Password 2 --------------"+user.getPassword());
		User user1 = convertToEntity(user);
		System.out.print("Password 3 --------------"+user.getPassword());
		return convertToDTO(userRepository.save(user1));
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> usersList=userRepository.findAll();
		List<UserDTO> userDTOs = usersList.stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
		return userDTOs;
	}

	@Override
	public UserDTO getUserById(Long id) {
		return convertToDTO((User) userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id)));
	}

	@Override
	public UserDTO updateUser(Long id, UserDTO updatedUser) {
		UserDTO existing = getUserById(id);
		if (!existing.getEmail().equals(updatedUser.getEmail()) &&
			userRepository.findByEmail(updatedUser.getEmail()).isPresent()) {
			throw new DuplicateEmailException("Email already in use: " + updatedUser.getEmail());
		}
		existing.setName(updatedUser.getName());
		existing.setEmail(updatedUser.getEmail());
		existing.setPhone(updatedUser.getPhone());
		existing.setRole(updatedUser.getRole());
		User us = convertToEntity(existing);
		return convertToDTO(userRepository.save(us));
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserDTO borrowBook(Long userId, Long bookId) {

		// 1. Fetch entity from repo
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		Book book = bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + bookId));

		// 2. Validate
		if (book.getStatus() == Book.Status.BORROWED) {
			throw new RuntimeException("Book is already borrowed by someone else.");
		}

		// 3. Update entity
		book.setStatus(Book.Status.BORROWED);
		user.getBorrowedBooks().add(book); // entity relationship

		// 4. Save changes
		bookRepository.save(book);
		userRepository.save(user);

		// 5. Convert to DTO and return
		return convertToDTO(user);
	}
	
	public UserDTO convertToDTO(User user) {
	    Set<Long> bookIds = user.getBorrowedBooks()
	                            .stream()
	                            .map(Book::getId)
	                            .collect(Collectors.toSet());

	    return new UserDTO(
	        user.getId(),
	        user.getName(),
	        user.getEmail(),
	        user.getRole(),
	        user.getPhone(),
	        user.getUsername(),
	        bookIds,         // ✅ Correct: bookIds passed as borrowedBookIds
	        user.getPassword() // ✅ Correct: password passed as password
	    );
	}

	
	public User convertToEntity(UserDTO dto) {
	    User user = new User();
	    user.setId(dto.getId());
	    user.setName(dto.getName());
	    user.setEmail(dto.getEmail());
	    user.setRole(dto.getRole());
	    user.setPhone(dto.getPhone());
	    user.setUsername(dto.getUsername());
	    user.setPassword(dto.getPassword());
	    // Password can be set if needed
	    return user;
	}



}
