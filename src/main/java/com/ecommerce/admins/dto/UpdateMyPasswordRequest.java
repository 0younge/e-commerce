package com.ecommerce.admins.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateMyPasswordRequest {

	@NotBlank(message = "비밀번호는 필수로 작성해야합니다.")
	@Size(min = 8, message = "비밀번호는 최소 8자 이상이여야 합니다")
	private final String password;
	@NotBlank(message = "비밀번호는 필수로 작성해야합니다.")
	@Size(min = 8, message = "비밀번호는 최소 8자 이상이여야 합니다")
	private final String newPassword;

	public UpdateMyPasswordRequest(String password, String newPassword) {
		this.password = password;
		this.newPassword = newPassword;
	}

}
