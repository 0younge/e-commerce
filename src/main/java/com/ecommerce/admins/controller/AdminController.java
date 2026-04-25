package com.ecommerce.admins.controller;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.admins.dto.CreateAdminRequest;
import com.ecommerce.admins.dto.GetAdminResponse;
import com.ecommerce.admins.dto.GetOneAdminResponse;
import com.ecommerce.admins.dto.LoginAdminRequest;
import com.ecommerce.admins.dto.UpdateAdminRequest;
import com.ecommerce.admins.dto.UpdateRoleAdminRequest;
import com.ecommerce.admins.entity.AdminConst;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.admins.entity.AdminRole;
import com.ecommerce.admins.service.AdminService;
import com.ecommerce.common.enums.AdminStatus;

import jakarta.servlet.http.HttpSession;
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
	public ResponseEntity<Void> createAdmin(@RequestBody @Valid CreateAdminRequest request) {
		adminService.save(request);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 로그인
	 * @param request 로그인에 필요한 정보
	 * @param session 검증을 위한 세션
	 * @return 상태코드
	 */
	@PostMapping("/login")
	public ResponseEntity<Void> loginAdmin(@RequestBody @Valid LoginAdminRequest request, HttpSession session) {
		AdminInfo adminInfo = adminService.login(request);
		session.setMaxInactiveInterval(86400); // 세션만료: 24시간
		session.setAttribute(AdminConst.ADMIN_INFO, adminInfo);

		return ResponseEntity.ok().build();
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
	 * @param session 검증을 위한 세션
	 * @return 페이지네이션을 마친 관리자 리스트
	 */
	@GetMapping
	public ResponseEntity<Page<GetAdminResponse>> getAdminList(@RequestParam(required = false) String keyword,
		@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "createdAt") String sortBy, @RequestParam(defaultValue = "desc") String sortOrder,
		@RequestParam(required = false) AdminRole role, @RequestParam(required = false) AdminStatus status,
		HttpSession session) {
		Pageable pageable = PageRequest.of(page - 1, size,
			sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

		return ResponseEntity.ok(
			adminService.getAdminList(keyword, role, status, pageable, checkSessionOrThrow(session)));
	}

	/**
	 * 특정 관리자 조회
	 * @param adminId 조회할 관리자 아이디
	 * @param session 검증을 위한 세션
	 * @return 특정 관리자의 이름, 메일, 전화번호, 역할, 상태, 생성일, 수락일 반환
	 */
	@GetMapping("/{adminId}")
	public ResponseEntity<GetOneAdminResponse> getOneAdmin(@PathVariable Long adminId, HttpSession session) {
		return ResponseEntity.ok(adminService.getOne(adminId, checkSessionOrThrow(session)));
	}

	/**
	 * 관리자 정보 수정
	 * @param request 수정할 값
	 * @param adminId 수정할 관리자 아이디
	 * @param session 검증을 위한 세션
	 * @return 상태코드
	 */
	@PatchMapping("/{adminId}")
	public ResponseEntity<Void> updateAdmin(@RequestBody @Valid UpdateAdminRequest request, @PathVariable Long adminId,
		HttpSession session) {
		adminService.update(adminId, request, checkSessionOrThrow(session));

		return ResponseEntity.ok().build();
	}

	/**
	 * 관리자 역할 변경
	 * @param request 변경할 역할
	 * @param adminId 변경할 관리자 아이디
	 * @param session 검증을 위한 세션
	 * @return 상태코드
	 */
	@PatchMapping("/{adminId}/role")
	public ResponseEntity<Void> updateRoleAdmin(@RequestBody @Valid UpdateRoleAdminRequest request,
		@PathVariable Long adminId, HttpSession session) {
		adminService.updateRole(adminId, request, checkSessionOrThrow(session));

		return ResponseEntity.ok().build();
	}

	/**
	 * 로그인 확인 메서드
	 * 	 * @param session 검증을 위한 세션
	 * 	 * @return 검증을 마친 세션의 세션값
	 */
	public AdminInfo checkSessionOrThrow(HttpSession session) {
		AdminInfo adminInfo = (AdminInfo)session.getAttribute(AdminConst.ADMIN_INFO);
		if (adminInfo == null) {
			throw new IllegalStateException("로그인이 필요한 작업입니다.");
		}
		return adminInfo;
	}

}
