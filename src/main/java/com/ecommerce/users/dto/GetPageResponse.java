package com.ecommerce.users.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;

@Getter
public class GetPageResponse<T> {
	private final List<T> content;
	private final int page;
	private final int size;
	private final Long totalElements;
	private final int totalPages;

	private GetPageResponse(Page<T> page) {
		this.content = page.getContent();
		this.page = page.getNumber() + 1;
		this.size = page.getSize();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
	}

	public static <T> GetPageResponse<T> of(Page<T> page) {
		return new GetPageResponse<>(page);
	}
}
