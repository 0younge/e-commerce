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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.common.enums.UserStatus;
import com.ecommerce.common.exception.InvalidRequestException;
import com.ecommerce.common.response.ApiResponse;
import com.ecommerce.users.dto.GetOneUserResponse;
import com.ecommerce.users.dto.GetPageResponse;
import com.ecommerce.users.dto.GetUserResponse;
import com.ecommerce.users.dto.PatchUserRequest;
import com.ecommerce.users.dto.PatchUserResponse;
import com.ecommerce.users.dto.PatchUserStatusRequest;
import com.ecommerce.users.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	/**
	 * 사용자 목록 조회
	 * @param keyword 검색할 키워드
	 * @param page 페이지 번호
	 * @param size 페이지 사이즈
	 * @param sortBy 정렬 기준
	 * @param sortOrder 정렬 순서
	 * @param status 검색할 상태
	 * @return 페이지네이션을 마친 사용자 리스트
	 */
	@GetMapping()
	public ResponseEntity<ApiResponse<GetPageResponse<GetUserResponse>>> getUserList(
		@RequestParam(required = false) String keyword,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(required = false, defaultValue = "name") String sortBy,
		@RequestParam(required = false, defaultValue = "ASC") String sortOrder,
		@RequestParam(required = false) UserStatus status
	) {
		if (page < 1) {
			throw new InvalidRequestException("페이지는 1 이상이어야 합니다.");
		}
		Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.valueOf(sortOrder), sortBy);
		Page<GetUserResponse> result = userService.findByKeywordAndStatus(keyword, status, pageable);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(GetPageResponse.of(result)));
	}

	/**
	 * 특정 사용자 조회
	 * @param userId 조회할 사용자 아이디
	 * @return 특정 사용자의 상세 정보
	 */
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse<GetOneUserResponse>> getUserDetails(@PathVariable Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(userService.findUserDetails(userId)));
	}

	/**
	 * 사용자 정보 수정
	 * @param userId 수정할 사용자 아이디
	 * @param patchUserRequest 수정할 값
	 * @return 수정된 사용자 정보
	 */
	@PatchMapping("/{userId}")
	public ResponseEntity<ApiResponse<PatchUserResponse>> patchUserDetails(@PathVariable Long userId,
		@Valid @RequestBody PatchUserRequest patchUserRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(userService.patchUserDetails(userId, patchUserRequest)));
	}

	/**
	 * 사용자 상태 변경
	 * @param userId 상태를 변경할 사용자 아이디
	 * @param patchUserStatusRequest 변경할 상태
	 * @return 상태가 변경된 사용자 정보
	 */
	@PatchMapping("/{userId}/status")
	public ResponseEntity<ApiResponse<PatchUserResponse>> patchUserStatus(@PathVariable Long userId,
		@Valid @RequestBody PatchUserStatusRequest patchUserStatusRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(userService.patchUserStatus(userId, patchUserStatusRequest)));
	}

	/**
	 * 사용자 삭제
	 * @param userId 삭제할 사용자 아이디
	 * @return 상태코드
	 */
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
		userService.deleteById(userId);
		return ResponseEntity.ok(ApiResponse.success("사용자가 삭제되었습니다."));
	}
}