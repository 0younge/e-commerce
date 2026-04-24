package com.ecommerce.users.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.users.dto.GetUserResponse;
import com.ecommerce.users.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/users")
	public ResponseEntity<Page<GetUserResponse>> getUser(
		@RequestParam String keyword,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(required = false) String sortBy,
		@RequestParam(required = false) String sortOrder,
		@RequestParam String status
		) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder), sortBy);
		return ResponseEntity.status(HttpStatus.OK).body(userService.findUserByKeyword(keyword, status, pageable));
	}

}
