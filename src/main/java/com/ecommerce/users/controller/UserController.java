package com.ecommerce.users.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.users.dto.GetOneUserResponse;
import com.ecommerce.users.dto.GetPageResponse;
import com.ecommerce.users.dto.GetUserResponse;
import com.ecommerce.users.dto.PatchUserRequest;
import com.ecommerce.users.dto.PatchUserResponse;
import com.ecommerce.users.dto.PatchUserStatusRequest;
import com.ecommerce.users.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/users")
	public ResponseEntity<GetPageResponse<GetUserResponse>> getUserList(
		@RequestParam String keyword,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(required = false, defaultValue = "name") String sortBy,
		@RequestParam(required = false, defaultValue = "ASC") String sortOrder,
		@RequestParam String status
	) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.valueOf(sortOrder), sortBy);
		Page<GetUserResponse> result = userService.findUserByKeyword(keyword, pageable);
		return ResponseEntity.status(HttpStatus.OK).body(GetPageResponse.of(result));
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<GetOneUserResponse> getUserDetails(@PathVariable Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.findUserDetails(userId));
	}

	@PatchMapping("/users/{userId}")
	public ResponseEntity<PatchUserResponse> patchUserDetails(@PathVariable Long userId,
		@RequestBody PatchUserRequest patchUserRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.patchUserDetails(userId, patchUserRequest));
	}

	@PatchMapping("/users/{userId}/status")
	public ResponseEntity<PatchUserResponse> patchUserStatus(@PathVariable Long userId,
		@RequestBody PatchUserStatusRequest patchUserStatusRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.patchUserStatus(userId, patchUserStatusRequest));
	}

	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
		userService.deleteById(userId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
