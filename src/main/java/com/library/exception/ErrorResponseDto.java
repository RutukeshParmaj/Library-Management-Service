package com.library.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

//This is DTO for transferring Book data between client and server
//Shows only required field to user

public class ErrorResponseDto {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public ErrorResponseDto(LocalDateTime timestamp, int status, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
		this.path = path;
	}
	public ErrorResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
