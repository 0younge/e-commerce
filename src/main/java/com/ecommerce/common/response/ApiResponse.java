package com.ecommerce.common.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter

public class ApiResponse<T> {
	private final int status;
	private final String message;
	private final T data;
	private final LocalDateTime timestamp;

	public ApiResponse(HttpStatus status, String message, T data) {
		this.status = status.value();
		this.message = message;
		this.data = data;
		this.timestamp = LocalDateTime.now();
	}

	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<>(HttpStatus.OK, message, data);
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(HttpStatus.OK, "OK", data);
	}

	public static ApiResponse<Void> success(String message) {
		return new ApiResponse<>(HttpStatus.OK, message, null);
	}

	public static <T> ApiResponse<T> created(String message, T data) {
		return new ApiResponse<>(HttpStatus.CREATED, message, data);
	}
}
