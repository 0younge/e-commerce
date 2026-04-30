package com.ecommerce.admins.dto;

import com.ecommerce.admins.entity.Admin;

import lombok.Getter;

@Getter
public class GetMyAdminResponse {

	private final String name;
	private final String email;
	private final String phoneNumber;

	private GetMyAdminResponse(String name, String email, String phoneNumber) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public static GetMyAdminResponse from(Admin admin) {
		return new GetMyAdminResponse(admin.getName(), admin.getEmail(), admin.getPhoneNumber());
	}
}
