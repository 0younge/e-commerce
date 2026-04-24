package com.ecommerce.users.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class GetPageResponse {
	private final List<GetUserResponse> content;
	private final int page;
	private final int size;
	private final Long totalElements;
	private final int totalPages;

	public GetPageResponse(List<GetUserResponse> content, int page, int size, Long totalElements, int totalPages) {
		this.content = content;
		this.page = page;
		this.size = size;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
	}
}
