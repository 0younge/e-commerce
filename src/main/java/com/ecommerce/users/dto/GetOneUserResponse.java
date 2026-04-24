package com.ecommerce.users.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class GetOneUserResponse {
	private final String name;
	private final String email;
	private final String phoneNumber;
	private final String status;
	private final LocalDateTime createdAt;

	public GetOneUserResponse(String name, String email, String phoneNumber, String status,
		LocalDateTime createdAt) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.createdAt = createdAt;
	}
}
