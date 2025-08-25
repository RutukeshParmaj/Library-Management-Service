package com.library.service;

import com.library.dto.UserDTO;

import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
	UserDTO createUser(UserDTO user);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id, @Valid UserDTO user);
    void deleteUser(Long id);
    UserDTO borrowBook(Long userId, Long bookId);
}
