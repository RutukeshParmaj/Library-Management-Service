package com.library.mapper;

import com.library.dto.UserDTO;
import com.library.entity.Book;
import com.library.entity.User;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User u = new User();
        u.setId(dto.getId());
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        u.setRole(dto.getRole());
        u.setPhone(dto.getPhone());
        u.setUsername(dto.getUsername());
        // password stored raw here; **must be encoded before saving**
        u.setPassword(dto.getPassword());
        return u;
    }

    public static UserDTO toDto(User u) {
        if (u == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(u.getId());
        dto.setName(u.getName());
        dto.setEmail(u.getEmail());
        dto.setRole(u.getRole());
        dto.setPhone(u.getPhone());
        dto.setUsername(u.getUsername());

        if (u.getBorrowedBooks() != null) {
            Set<Long> ids = u.getBorrowedBooks().stream()
                    .map(Book::getId)
                    .collect(Collectors.toSet());
            dto.setBorrowedBookIds(ids);
        } else {
            dto.setBorrowedBookIds(Collections.emptySet());
        }
        // DO NOT set password into DTO
        return dto;
    }
}
