package com.ecommerce.admins.dto;

import com.ecommerce.admins.entity.AdminRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateAdminRequest {

	@NotBlank(message = "이름은 필수로 작성해야합니다.")
	private final String name;
	@NotBlank(message = "메일은 필수로 작성해야합니다.")
	@Email(message = "메일 형식을 지켜야합니다.")
	private final String email;
	@NotBlank(message = "비밀번호는 필수로 작성해야합니다.")
	@Size(min = 8, message = "비밀번호는 최소 8자 이상이여야 합니다")
	private final String password;
	@NotBlank(message = "전화번호는 필수로 작성해야합니다.")
	@Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "010-XXXX-XXXX 형식을 지켜야합니다.")
	private final String phoneNumber;
	@NotBlank(message = "역할은 필수로 작성해야합니다.")
	@Pattern(regexp = "^(SUPER_ADMIN|OPERATION_ADMIN|CS_ADMIN)$", message = "SUPER_ADMIN|OPERATION_ADMIN|CS_ADMIN중에서 선택해야 합니다.")
	private final AdminRole role;

	public CreateAdminRequest(String name, String email, String password, String phoneNumber, AdminRole role) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

}
