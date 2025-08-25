package com.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class UserDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Role is required")
    private String role;

    private String phone;

    @NotBlank(message = "Username is required")
    private String username;

    // Accept password in requests, but never serialize it in responses
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "password is required")
    private String password;

    @JsonIgnore
    private Set<Long> borrowedBookIds;

    public UserDTO() {}

    public UserDTO(Long id, String name, String email, String role, String phone, String username,
                   Set<Long> borrowedBookIds, String password) {
        this.id = id; this.name = name; this.email = email; this.role = role;
        this.phone = phone; this.username = username; this.borrowedBookIds = borrowedBookIds;
        this.password = password;
    }

    // getters & setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<Long> getBorrowedBookIds() { return borrowedBookIds; }
    public void setBorrowedBookIds(Set<Long> borrowedBookIds) { this.borrowedBookIds = borrowedBookIds; }
}
