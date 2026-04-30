package com.ecommerce.admins.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginAdminRequest {

	@NotBlank(message = "메일은 필수로 작성해야합니다.")
	@Email(message = "메일 형식을 지켜야합니다.")
	private final String email;
	@NotBlank(message = "비밀번호는 필수로 작성해야합니다.")
	@Size(min = 8, message = "비밀번호는 최소 8자 이상이여야 합니다")
	private final String password;

	public LoginAdminRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
