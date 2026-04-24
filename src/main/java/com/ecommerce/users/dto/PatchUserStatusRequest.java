package com.ecommerce.users.dto;

import com.ecommerce.common.enums.UserStatus;

import lombok.Getter;

@Getter
public class PatchUserStatusRequest {
	private final UserStatus userStatus;

	public PatchUserStatusRequest(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
}
