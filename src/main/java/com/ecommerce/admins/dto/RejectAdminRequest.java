package com.ecommerce.admins.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RejectAdminRequest {

	@NotBlank(message = "거부사유는 필수로 작성해야합니다.")
	@Size(max = 500, message = "거부사유는 최대 500자 이내이여야 합니다")
	private final String rejectionReason;

	public RejectAdminRequest(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
}
