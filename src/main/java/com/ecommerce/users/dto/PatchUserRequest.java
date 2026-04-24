package com.ecommerce.users.dto;

import lombok.Getter;

@Getter
public class PatchUserRequest {
	private final String name;
	private final String email;
	private final String phoneNumber;

	public PatchUserRequest(String name, String email, String phoneNumber) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
}
