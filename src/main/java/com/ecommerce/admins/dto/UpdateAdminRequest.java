package com.ecommerce.admins.dto;

import java.lang.reflect.Field;

import com.ecommerce.admins.entity.Admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateAdminRequest {

	@NotBlank(message = "이름은 필수로 작성해야합니다.")
	private final String name;
	@NotBlank(message = "메일은 필수로 작성해야합니다.")
	@Email(message = "메일 형식을 지켜야합니다.")
	private final String email;
	@NotBlank(message = "전화번호는 필수로 작성해야합니다.")
	@Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "010-XXXX-XXXX 형식을 지켜야합니다.")
	private final String phoneNumber;

	private UpdateAdminRequest(String name, String email, String phoneNumber) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

}
