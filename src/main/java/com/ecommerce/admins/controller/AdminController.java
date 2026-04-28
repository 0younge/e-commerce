package com.ecommerce.admins.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.ecommerce.admins.dto.CreateAdminRequest;
import com.ecommerce.admins.dto.GetAdminResponse;
import com.ecommerce.admins.dto.GetMyAdminResponse;
import com.ecommerce.admins.dto.GetOneAdminResponse;
import com.ecommerce.admins.dto.LoginAdminRequest;
import com.ecommerce.admins.dto.LoginAdminResponse;
import com.ecommerce.admins.dto.RejectAdminRequest;
import com.ecommerce.admins.dto.RejectAdminResponse;
import com.ecommerce.admins.dto.UpdateAdminRequest;
import com.ecommerce.admins.dto.UpdateMyAdminRequest;
import com.ecommerce.admins.dto.UpdateMyPasswordRequest;
import com.ecommerce.admins.dto.UpdateRoleAdminRequest;
import com.ecommerce.admins.dto.UpdateStatusAdminRequest;
import com.ecommerce.admins.entity.AdminRole;
import com.ecommerce.admins.service.AdminService;
import com.ecommerce.common.enums.AdminStatus;
import com.ecommerce.common.response.ApiResponse;
import com.ecommerce.common.security.auth.SecurityAdminInfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

	private final AdminService adminService;

	/**
	 * 회원가입
	 * @param request 회원가입 정보
	 * @return 상태코드
	 */
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<Void>> createAdmin(@RequestBody @Valid CreateAdminRequest request) {
		adminService.save(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created("관리자 회원가입 성공", null));
	}

	/**
	 * 로그인 기능
	 * @param request 메일과 비밀번호
	 * @return 서비스 로직에서 생성된 JWT 반환
	 */
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginAdminResponse>> loginAdmin(@RequestBody @Valid LoginAdminRequest request) {
		return ResponseEntity.ok().body(ApiResponse.success("로그인 성공!", adminService.login(request)));
	}

	/**
	 * 관리자 전체 조회
	 * @param keyword 검색할 키워드
	 * @param page 페이지 넘버
	 * @param size 페이지 사이즈
	 * @param sortBy 정렬기준
	 * @param sortOrder 정렬 순서
	 * @param role 검색할 역할
	 * @param status 검색할 상태
	 * @return 페이지네이션을 마친 관리자 리스트
	 */
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<GetAdminResponse>>> getAdminList(
		@RequestParam(required = false) String keyword,
		@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "createdAt") String sortBy, @RequestParam(defaultValue = "desc") String sortOrder,
		@RequestParam(required = false) AdminRole role, @RequestParam(required = false) AdminStatus status,
		@AuthenticationPrincipal SecurityAdminInfo loginAdmin) // 수정: 세션 대신 JWT 인증 정보 사용
	{
		Pageable pageable = PageRequest.of(page - 1, size,
			sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

		return ResponseEntity.ok()
			.body(ApiResponse.success("관리자 전체 조회 성공",
				adminService.getAdminList(keyword, role, status, pageable, loginAdmin.adminId())));
	}

	/**
	 * 특정 관리자 조회
	 * @param adminId 조회할 관리자 아이디
	 * @param loginAdmin 검증을 위한 JWT 값
	 * @return 특정 관리자의 이름, 메일, 전화번호, 역할, 상태, 생성일, 수락일 반환
	 */
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	@GetMapping("/{adminId}")
	public ResponseEntity<ApiResponse<GetOneAdminResponse>> getOneAdmin(@PathVariable Long adminId,
		@AuthenticationPrincipal SecurityAdminInfo loginAdmin) {
		return ResponseEntity.ok(
			ApiResponse.success("관리자 상세 조회 성공", adminService.getOne(adminId, loginAdmin.adminId())));
	}

	/**
	 * 관리자 정보 수정
	 * @param request 수정할 값
	 * @param adminId 수정할 관리자 아이디
	 * @param loginAdmin 검증을 위한 JWT 값
	 * @return 상태코드
	 */
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	@PatchMapping("/{adminId}")
	public ResponseEntity<ApiResponse<Void>> updateAdmin(@RequestBody @Valid UpdateAdminRequest request,
		@PathVariable Long adminId, @AuthenticationPrincipal SecurityAdminInfo loginAdmin) {
		adminService.update(adminId, request, loginAdmin.adminId());

		return ResponseEntity.ok(ApiResponse.success("관리자 정보 수정 성공"));
	}

	/**
	 * 관리자 역할 변경
	 * @param request 변경할 역할
	 * @param adminId 변경할 관리자 아이디
	 * @param loginAdmin 검증을 위한 JWT 값
	 * @return 상태코드
	 */
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	@PatchMapping("/{adminId}/role")
	public ResponseEntity<ApiResponse<Void>> updateRoleAdmin(@RequestBody @Valid UpdateRoleAdminRequest request,
		@PathVariable Long adminId, @AuthenticationPrincipal SecurityAdminInfo loginAdmin) {
		adminService.updateRole(adminId, request, loginAdmin.adminId());

		return ResponseEntity.ok(ApiResponse.success("관리자 역할 변경 성공"));
	}

	/**
	 * 관리자 상태 변경
	 * @param request 변경할 상태
	 * @param adminId 변경할 관리자 아이디
	 * @param loginAdmin 검증을 위한 JWT 값
	 * @return 상태코드
	 */
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	@PatchMapping("/{adminId}/status")
	public ResponseEntity<ApiResponse<Void>> updateStatusAdmin(@RequestBody @Valid UpdateStatusAdminRequest request,
		@PathVariable Long adminId, @AuthenticationPrincipal SecurityAdminInfo loginAdmin) {
		adminService.updateStatus(adminId, request, loginAdmin.adminId());

		return ResponseEntity.ok(ApiResponse.success("관리자 상태 변경 성공"));
	}

	/**
	 * 관리자 삭제
	 * @param adminId 삭제할 관리자 아이디
	 * @param loginAdmin 검증을 위한 JWT 값
	 * @return 상태코드
	 */
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	@DeleteMapping("/{adminId}")
	public ResponseEntity<ApiResponse<Void>> deleteAdmin(@PathVariable Long adminId,
		@AuthenticationPrincipal SecurityAdminInfo loginAdmin) {
		adminService.delete(adminId, loginAdmin.adminId());

		return ResponseEntity.ok(ApiResponse.success("관리자 삭제 성공"));
	}

	/**
	 * 관리자 승인
	 * @param adminId 승인할 관리자 아이디
	 * @param loginAdmin 검증을 위한 JWT 값
	 * @return 상태코드
	 */
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	@PatchMapping("/{adminId}/approve")
	public ResponseEntity<ApiResponse<Void>> approveAdmin(@PathVariable Long adminId,
		@AuthenticationPrincipal SecurityAdminInfo loginAdmin) {
		adminService.approve(adminId, loginAdmin.adminId());

		return ResponseEntity.ok(ApiResponse.success("관리자 승인 성공"));
	}

	/**
	 * 관리자 거부
	 * @param adminId 거부할 관리자 아이디
	 * @param request 거부사유
	 * @param loginAdmin 검증을 위한 JWT 값
	 * @return 상태코드
	 */
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	@PatchMapping("/{adminId}/reject")
	public ResponseEntity<ApiResponse<RejectAdminResponse>> rejectAdmin(@PathVariable Long adminId,
		@RequestBody @Valid RejectAdminRequest request, @AuthenticationPrincipal SecurityAdminInfo loginAdmin) {

		return ResponseEntity.ok(
			ApiResponse.success("관리자 거부 성공", adminService.reject(adminId, request, loginAdmin.adminId())));
	}

	/**
	 * 내 프로필 조회
	 * @param loginAdmin 검증을 위한 JWT 값
	 * @return 내 이름, 메일, 전화번호 반환
	 */
	@GetMapping("/my")
	public ResponseEntity<ApiResponse<GetMyAdminResponse>> getMy(
		@AuthenticationPrincipal SecurityAdminInfo loginAdmin) {
		return ResponseEntity.ok(ApiResponse.success("내 프로필 조회 성공", adminService.getMy(loginAdmin.adminId())));
	}

	/**
	 * 내 프로필 수정
	 * @param request 수정할 이름, 메일, 전화번호
	 * @param loginAdmin 검증을 위한 JWT 값
	 * @return 상태코드
	 */
	@PatchMapping("/my")
	public ResponseEntity<ApiResponse<Void>> updateMy(@RequestBody @Valid UpdateMyAdminRequest request,
		@AuthenticationPrincipal SecurityAdminInfo loginAdmin) {
		adminService.updateMy(request, loginAdmin.adminId());

		return ResponseEntity.ok(ApiResponse.success("내 프로필 수정 성공"));
	}

	/**
	 * 내 비밀번호 수정
	 * @param request 변경할 비밀번호
	 * @param loginAdmin 검증을 위한 JWT 값
	 * @return 상태코드
	 */
	@PatchMapping("/my/password")
	public ResponseEntity<ApiResponse<Void>> updateMyPassword(@RequestBody @Valid UpdateMyPasswordRequest request,
		@AuthenticationPrincipal SecurityAdminInfo loginAdmin) {
		adminService.updateMyPassword(request, loginAdmin.adminId());

		return ResponseEntity.ok(ApiResponse.success("내 비밀번호 수정 성공"));
	}

}
