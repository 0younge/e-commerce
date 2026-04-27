package com.ecommerce.users.dto;

import java.time.LocalDateTime;

import com.ecommerce.common.enums.UserStatus;
import com.ecommerce.users.entity.User;

import lombok.Getter;

@Getter
public class PatchUserResponse {
	private final Long userId;
	private final String name;
	private final String email;
	private final String phoneNumber;
	private final UserStatus status;
	private final LocalDateTime createdAt;
	private final LocalDateTime modifiedAt;

	private PatchUserResponse(Long userId, String name, String email, String phoneNumber, UserStatus status,
		LocalDateTime createdAt, LocalDateTime modifiedAt) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	public static PatchUserResponse from(User user) {
		return new PatchUserResponse(
			user.getUserId(),
			user.getName(),
			user.getEmail(),
			user.getPhoneNumber(),
			user.getStatus(),
			user.getCreatedAt(),
			user.getModifiedAt()
		);
	}
}
