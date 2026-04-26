package com.ecommerce.users.dto;

import com.ecommerce.common.enums.UserStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PatchUserStatusRequest {

	@NotNull(message = "상태는 필수로 작성해야합니다.")
	private final UserStatus userStatus;

	public PatchUserStatusRequest(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
}
