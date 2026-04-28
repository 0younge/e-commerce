package com.ecommerce.admins.dto;

import java.time.LocalDateTime;

import com.ecommerce.admins.entity.Admin;

import lombok.Getter;

@Getter
public class RejectAdminResponse {

	private final LocalDateTime rejectedAt;
	private final String rejectionReason;

	private RejectAdminResponse(LocalDateTime rejectedAt, String rejectionReason) {
		this.rejectedAt = rejectedAt;
		this.rejectionReason = rejectionReason;
	}

	public static RejectAdminResponse from(Admin admin) {
		return new RejectAdminResponse(admin.getRejectedAt(), admin.getRejectionReason());
	}
}
