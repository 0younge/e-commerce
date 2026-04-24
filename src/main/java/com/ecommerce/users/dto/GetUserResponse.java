package com.ecommerce.users.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class GetUserResponse {

	private final Long id;
	private final String name;
	private final String email;
	private final String phoneNumber;
	private final String status;
	private final LocalDateTime createdAt;

	public GetUserResponse(Long id, String name, String email, String phoneNumber, String status,
		LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.createdAt = createdAt;
	}
}
