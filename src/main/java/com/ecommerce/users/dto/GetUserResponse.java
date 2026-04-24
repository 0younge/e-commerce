package com.ecommerce.users.dto;

import java.time.LocalDateTime;

import com.ecommerce.common.enums.UserStatus;

import lombok.Getter;

@Getter
public class GetUserResponse {

	private final Long id;
	private final String name;
	private final String email;
	private final String phoneNumber;
	private final UserStatus status;
	private final LocalDateTime createdAt;

	public GetUserResponse(Long id, String name, String email, String phoneNumber, UserStatus status,
		LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.createdAt = createdAt;
	}
}
